public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate; // Rate at which the vendor adds tickets (in seconds)
    private final String vendorName;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        try {
            int ticketNumber = 1;
            while (true) {
                // Vendor releases tickets
                String ticket = vendorName + "-Ticket-" + ticketNumber;
                ticketPool.addTicket(ticket);

                ticketNumber++; // Increment ticket number
                Thread.sleep(ticketReleaseRate * 1000); // Sleep for specified seconds
            }
        } catch (InterruptedException e) {
            System.out.println(vendorName + " was interrupted.");
        }
    }
}
