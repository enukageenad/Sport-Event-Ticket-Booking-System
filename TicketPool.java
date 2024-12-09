import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<String> ticketQueue = new LinkedList<>(); // Ticket queue (thread-safe structure)
    private final int maxTicketCapacity; // Maximum ticket capacity

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Method for vendors to add tickets
    public synchronized void addTicket(String ticket) throws InterruptedException {
        while (ticketQueue.size() >= maxTicketCapacity) {
            // Wait if pool is full
            wait();
        }
        ticketQueue.add(ticket);
        System.out.println("Ticket added: " + ticket + " | Tickets in pool: " + ticketQueue.size());
        notifyAll(); // Notify customers that a ticket is available
    }

    // Method for customers to buy tickets
    public synchronized void buyTicket(String customerName) throws InterruptedException {
        while (ticketQueue.isEmpty()) {
            // Wait if no tickets are available
            System.out.println(customerName + " is waiting for tickets...");
            wait();
        }
        String ticket = ticketQueue.poll(); // Retrieve a ticket
        System.out.println(customerName + " bought ticket: " + ticket + " | Tickets left in pool: " + ticketQueue.size());
        notifyAll(); // Notify vendors that there is space in the pool
    }

    // Method to view the current number of tickets in the pool
    public synchronized int viewTickets() {
        return ticketQueue.size();
    }
}
