import java.util.ArrayList;
import java.util.List;

public class Node extends Thread {
    // Attributes
    private int id;
    private boolean inCS;
    private boolean reqCS;
    private List<Node> otherNodes;
    private int reply;

    // Constructor
    public Node(int id) {
        this.id = id;
        this.inCS = false;
        this.reqCS = false;
        this.otherNodes = new ArrayList<>();
        this.reply = 0;
    }

    // Methods
    public void setOtherNodes(List<Node> arr) {
        for (Node node : arr) {
            if (node.id != this.id) {
                otherNodes.add(node);
            }
        }
    }

    public synchronized void receiveReply() throws Exception {
        this.reply++;
        if (this.reply == this.otherNodes.size()) {
            enterCriticalSection();
        }
    }

    public void enterCriticalSection() throws Exception {
        this.inCS = true;
        System.out.println("Node id: " + this.id + " is entering Critical Section.\n");
        Thread.sleep(2000);
    }

    public void exitCriticalSection() {
        System.out.println("Node id: " + this.id + " is exiting Critical Section.\n");

    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}