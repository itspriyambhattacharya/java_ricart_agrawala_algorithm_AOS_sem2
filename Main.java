import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);

        List<Node> arr = Arrays.asList(n1, n2, n3, n4, n5); // creating an array from individual elements
        n1.setOtherNodes(arr);
        n2.setOtherNodes(arr);
        n3.setOtherNodes(arr);
        n4.setOtherNodes(arr);
        n5.setOtherNodes(arr);

        n1.start();
        n2.start();
        n3.start();
        n4.start();
        n5.start();
    }
}
