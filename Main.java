import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nEnter Number of Processes:\t");
        int n = Integer.parseInt(br.readLine());

        List<Node> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Node newNode = new Node(i + 1);
            processes.add(newNode); // adding nodes to the ArrayList
        }

        for (Node node : processes) {
            node.setOtherNodes(processes);
        }

        for (Node node : processes) {
            node.start();
        }

        br.close();

    }
}
