import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();
    private static boolean isRunning = false;

    public static void main(String[] args) {
        // System configuration
        ConfigManager configManager = new ConfigManager();
        configManager.configureSystem();

        // Create a ticket pool with max ticket capacity
        TicketPool ticketPool = new TicketPool(configManager.getMaxTicketCapacity());

        // Create vendors (producers) and customers (consumers)
        Vendor vendor1 = new Vendor(ticketPool, configManager.getTicketReleaseRate(), "Vendor1");
        Vendor vendor2 = new Vendor(ticketPool, configManager.getTicketReleaseRate(), "Vendor2");

        Customer customer1 = new Customer(ticketPool, configManager.getCustomerRetrievalRate(), "Customer1");
        Customer customer2 = new Customer(ticketPool, configManager.getCustomerRetrievalRate(), "Customer2");
        Customer customer3 = new Customer(ticketPool, configManager.getCustomerRetrievalRate(), "Customer3");
        Customer customer4 = new Customer(ticketPool, configManager.getCustomerRetrievalRate(), "Customer4");
        Customer customer5 = new Customer(ticketPool, configManager.getCustomerRetrievalRate(), "Customer5");


        // Command loop for start, stop, view, and exit
        Scanner scanner = new Scanner(System.in);
        String command = "";

        System.out.println("Enter a command (start, stop, view, exit): ");
        while (!command.equals("exit")) {
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    if (!isRunning) {
                        // Start vendor and customer threads
                        isRunning = true;
                        startVendorsAndCustomers(vendor1, vendor2, customer5, customer2);
                        System.out.println("System started.");
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;

                case "stop":
                    if (isRunning) {
                        stopVendorsAndCustomers();
                        System.out.println("System stopped.");
                    } else {
                        System.out.println("System is not running.");
                    }
                    break;

                case "view":
                    System.out.println("Tickets available in the pool: " + ticketPool.viewTickets());
                    break;

                case "exit":
                    if (isRunning) {
                        stopVendorsAndCustomers(); // Ensure threads are stopped before exit
                    }
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Unknown command. Available commands: start, stop, view, exit.");
            }

            if (!command.equals("exit")) {
                System.out.print("Enter a command: ");
            }
        }

        scanner.close();
    }

    // Method to start vendor and customer threads
    private static void startVendorsAndCustomers(Vendor vendor1, Vendor vendor2, Customer customer1, Customer customer2) {
        vendorThreads.add(new Thread(vendor1));
        vendorThreads.add(new Thread(vendor2));
        customerThreads.add(new Thread(customer1));
        customerThreads.add(new Thread(customer2));

        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
        }
        for (Thread customerThread : customerThreads) {
            customerThread.start();
        }
    }

    // Method to stop vendor and customer threads
    private static void stopVendorsAndCustomers() {
        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        isRunning = false;
    }
}
