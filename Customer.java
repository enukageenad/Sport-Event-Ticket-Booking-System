public class Customer implements Runnable {
    private final TicketPool ticketPool; // Shared ticket pool
    private final int customerRetrievalRate; // Rate at which the customer buys tickets (in seconds)
    private final String customerName;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Customer tries to buy a ticket
                ticketPool.buyTicket(customerName);

                // Wait based on customer retrieval rate
                Thread.sleep(customerRetrievalRate * 1000); // Sleep for specified seconds
            }
        } catch (InterruptedException e) {
            System.out.println(customerName + " was interrupted.");
        }
    }
}
