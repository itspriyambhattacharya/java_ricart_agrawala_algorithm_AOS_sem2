import java.util.ArrayList;
import java.util.List;

public class Node extends Thread {
    // Attributes
    private int id;
    private boolean inCS;
    private boolean reqCS;
    private List<Node> otherNodes;

    // Constructor
    public Node(int id) {
        this.id = id;
        this.inCS = false;
        this.reqCS = false;
        this.otherNodes = new ArrayList<>();
    }

    // Methods
    public void setOtherNodes(List<Node> arr) {
        for (Node node : arr) {
            if (node.id != this.id) {
                otherNodes.add(node);
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}