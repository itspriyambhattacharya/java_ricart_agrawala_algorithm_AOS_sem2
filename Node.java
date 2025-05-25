import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node extends Thread {
    private final int id;
    private boolean inCS;
    private boolean reqCS;
    private List<Node> otherNodes;
    private int reply;
    private int clock;
    private int requestTimestamp; // FIXED
    private final Queue<Message> queue;

    public Node(int id) {
        this.id = id;
        this.inCS = false;
        this.reqCS = false;
        this.reply = 0;
        this.clock = 0;
        this.requestTimestamp = -1;
        this.otherNodes = new ArrayList<>();
        this.queue = new LinkedList<>();
    }

    public void setOtherNodes(List<Node> arr) {
        for (Node node : arr) {
            if (node.id != this.id) {
                otherNodes.add(node);
            }
        }
    }

    public synchronized void exitCriticalSection() {
        this.inCS = false;
        this.reqCS = false;
        System.out.println("Node " + id + " is EXITING CS at clock = " + clock + "\n");

        while (!queue.isEmpty()) {
            Message m = queue.poll();
            for (Node node : otherNodes) {
                if (node.id == m.getId()) {
                    Node target = node;
                    new Thread(() -> target.receiveReply()).start();
                }
            }
        }
    }

    public synchronized void enterCriticalSection() {
        this.inCS = true;
        System.out.println("Node " + id + " is ENTERING CS at clock = " + clock + "\n");

        try {
            Thread.sleep(2000); // simulate critical section execution
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exitCriticalSection();
    }

    public synchronized void receiveReply() {
        this.reply++;
        if (this.reply == this.otherNodes.size()) {
            enterCriticalSection();
        }
    }

    public synchronized void receiveRequest(Message m) {
        clock = Math.max(clock, m.getTimestamp()) + 1;

        boolean sendReply = !this.reqCS ||
                (m.getTimestamp() < this.requestTimestamp) ||
                (m.getTimestamp() == this.requestTimestamp && m.getId() < this.id);

        if (sendReply) {
            for (Node node : otherNodes) {
                if (node.id == m.getId()) {
                    Node target = node;
                    new Thread(() -> target.receiveReply()).start();
                }
            }
        } else {
            this.queue.add(m);
        }
    }

    public synchronized void requestCriticalSection() {
        this.reqCS = true;
        this.requestTimestamp = ++this.clock;
        this.reply = 0;

        System.out.println("Node " + id + " is REQUESTING CS at clock = " + requestTimestamp);

        Message m = new Message(this.id, requestTimestamp);
        for (Node node : otherNodes) {
            Node target = node;
            new Thread(() -> target.receiveRequest(m)).start();
        }
    }

    @Override
    public void run() {
        try {
            int delay = (int) (Math.random() * 3000);
            Thread.sleep(delay);
            requestCriticalSection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
