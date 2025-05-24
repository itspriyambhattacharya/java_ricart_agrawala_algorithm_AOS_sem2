public class Message {
    // Attributes
    private int id;
    private int timestamp;

    public int getTimestamp() {
        return this.timestamp;
    }

    public int getId() {
        return this.id;
    }

    public Message(int id, int ts) {
        this.id = id;
        this.timestamp = ts;
    }
}
