import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node extends Thread {
    // Attributes
    private int id;
    private boolean inCS;
    private boolean reqCS;
    private List<Node> otherNodes;
    private int reply;
    private int clock;
    private Queue<Message> queue;

    // Constructor
    public Node(int id) {
        this.id = id;
        this.inCS = false;
        this.reqCS = false;
        this.otherNodes = new ArrayList<>();
        this.reply = 0;
        this.clock = 0;
        this.queue = new LinkedList<>();
    }

    // Methods
    public void setOtherNodes(List<Node> arr) {
        for (Node node : arr) {
            if (node.id != this.id) {
                otherNodes.add(node);
            }
        }
    }

    public void exitCriticalSection() {
        this.inCS = false;
        this.reqCS = false;
        System.out.println("Node id: " + this.id + " is exiting Critical Section.\n");
    }

    public void enterCriticalSection() {
        this.inCS = true;
        System.out.println("Node id: " + this.id + " is entering Critical Section.\n");
        try {
            Thread.sleep(2000);
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

        boolean sendReply = !this.reqCS || m.getTimestamp() < clock
                || (m.getTimestamp() == clock && m.getId() < this.id);

        if (sendReply) {
            for (Node node : otherNodes) {
                if (node.id == m.getId()) {
                    node.receiveReply();
                }
            }
        } else {
            this.queue.add(m);
        }

    }

    public synchronized void requestCriticalSection() {
        this.reqCS = true;
        this.clock++;
        this.reply = 0;

        Message m = new Message(this.id, this.clock);
        for (Node node : otherNodes) {
            node.receiveRequest(m);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            requestCriticalSection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}