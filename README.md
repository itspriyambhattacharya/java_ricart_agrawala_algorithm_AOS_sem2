
# Ricart-Agrawala Algorithm in Java

## 🧠 Overview

This project implements the **Ricart-Agrawala algorithm**, a well-known distributed mutual exclusion algorithm that allows multiple distributed processes (nodes) to **safely access a critical section (CS)** without central coordination.

Each node is represented as an independent thread, simulating the behavior of a distributed system. The algorithm ensures **mutual exclusion**, **progress**, and **fairness** using **timestamped message-passing**.

---

## 📚 What is the Ricart-Agrawala Algorithm?

The Ricart-Agrawala algorithm is a **fully distributed algorithm** that allows multiple processes in a distributed system to enter the critical section **without using a centralized coordinator**.

### Key Concepts:
- **Request Message**: Sent by a node to all others when it wants to enter the critical section.
- **Reply Message**: Sent by a node to another when it is safe to enter the CS.
- **Timestamp**: Logical clock is used to determine the priority of requests.
- **Queue**: If a node cannot grant permission immediately, it defers the reply and queues the request.

---

## 📌 Features of the Implementation

- Fully distributed mutual exclusion using message-passing
- Logical clock updates and synchronization
- Queue-based deferred reply system
- Multi-threaded simulation of nodes
- Fair and correct entry to the critical section
- Java-based clean modular code (OOP design)

---

## 📁 Code Structure

### 1. `Message.java`
Represents a message carrying the **timestamp** and **sender's ID** used in request and reply communication.

```java
private int id;
private int timestamp;
```

### 2. `Node.java`
Represents each node (or process) in the distributed system. It extends `Thread` to simulate concurrent behavior.

Key members:
- `int id`: Unique identifier
- `int clock`: Logical clock for timestamping
- `boolean reqCS`, `inCS`: Flags to track state
- `Queue<Message> queue`: Deferred request queue
- `List<Node> otherNodes`: Reference to other nodes
- `int reply`: Count of replies received

Key methods:
- `requestCriticalSection()`: Initiates CS request
- `receiveRequest(Message m)`: Handles incoming requests
- `receiveReply()`: Handles replies from peers
- `enterCriticalSection()`: Grants access to CS
- `exitCriticalSection()`: Exits CS and replies to queued requests

### 3. `Main.java`
Sets up the simulation:
- Creates multiple nodes
- Establishes peer connections
- Starts all nodes to begin the simulation

---

## 🧮 Pseudocode

### Node Initialization

```
FOR each Node n_i in list of nodes
    SET id
    INIT clock = 0
    INIT requestTimestamp = -1
    INIT inCS = false
    INIT reqCS = false
    INIT reply = 0
    INIT queue = empty
    SET otherNodes (excluding itself)
END FOR
```

---

### Requesting Critical Section

```
BEGIN requestCriticalSection
    SET reqCS = true
    INCREMENT clock
    SET requestTimestamp = clock
    SET reply = 0
    PRINT "Requesting CS at clock = requestTimestamp"
    
    CREATE request message m(id, requestTimestamp)
    
    FOR each node in otherNodes
        SEND m to node.receiveRequest()
    END FOR
END
```

---

### Receiving a Request

```
BEGIN receiveRequest(m)
    SET clock = max(clock, m.timestamp) + 1
    
    IF not reqCS OR
       (m.timestamp < requestTimestamp) OR
       (m.timestamp == requestTimestamp AND m.id < this.id)
    THEN
        SEND immediate reply to m.id
    ELSE
        ENQUEUE m in queue
    END IF
END
```

---

### Receiving a Reply

```
BEGIN receiveReply
    INCREMENT reply
    IF reply == total other nodes
        CALL enterCriticalSection()
    END IF
END
```

---

### Entering Critical Section

```
BEGIN enterCriticalSection
    SET inCS = true
    PRINT "Node id entering CS at clock"
    SLEEP (simulate work in CS)
    CALL exitCriticalSection()
END
```

---

### Exiting Critical Section

```
BEGIN exitCriticalSection
    SET inCS = false
    SET reqCS = false
    PRINT "Node id exiting CS at clock"
    
    WHILE queue is not empty
        DEQUEUE message m
        SEND reply to m.id
    END WHILE
END
```

---

## 🛠️ How to Run

### ✅ Prerequisites:
- Java JDK 8 or above
- Any IDE or command-line terminal

### 💻 Compilation:
```bash
javac Main.java Node.java Message.java
```

### ▶️ Execution:
```bash
java Main
```

---

## 📌 Sample Output

```
Node 3 is REQUESTING CS at clock = 1
Node 1 is REQUESTING CS at clock = 1
Node 1 is ENTERING CS at clock = 2

...

Node 1 is EXITING CS at clock = 2
Node 3 is ENTERING CS at clock = 3
...
```

> Each node independently requests the critical section and waits for permission. Once it receives all replies, it enters the CS. On exiting, it processes any deferred replies.

---

## 🧠 Key Points

- All communication is **synchronous**, mimicked using Java threads and method calls.
- Clock synchronization uses the **Lamport clock** principle.
- **Fairness** is guaranteed: older requests or lower-ID ties are prioritized.
- Mutual exclusion is strictly maintained: **at most one node is in CS at a time.**

---

## 📌 Extensions (Future Work)

- Convert this simulation into a network-based implementation using sockets or RMI.
- Extend to allow re-entry into CS multiple times.
- Introduce message loss or delays for fault tolerance simulation.

---

## 👨‍💻 Author

**Priyam Bhattacharya**  
M.Sc. Computer Science  
University of Calcutta

---

## 📜 References

- Ricart, Glenn, and Ashok Agrawala. *"An optimal algorithm for mutual exclusion in computer networks."* Communications of the ACM 24.1 (1981): 9-17.
- Lamport, Leslie. *"Time, clocks, and the ordering of events in a distributed system."* Communications of the ACM 21.7 (1978): 558-565.
