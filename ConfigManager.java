import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class ConfigManager {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final String CONFIG_FILE = "config.txt";

    public void configureSystem() {
        Scanner scanner = new Scanner(System.in);

        // Ask if the user wants to load previous parameters
        if (new File(CONFIG_FILE).exists()) {
            System.out.print("Do you want to continue with previous inputs? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                loadPreviousConfiguration();
                return;
            }
        }

        // Validate and set totalTickets
        while (true) {
            System.out.print("Enter total tickets available: ");
            totalTickets = scanner.nextInt();
            if (totalTickets > 0) break;
            System.out.println("Invalid input! Total tickets must be greater than 0.");
        }

        // Validate and set ticketReleaseRate
        while (true) {
            System.out.print("Enter ticket release rate (in seconds): ");
            ticketReleaseRate = scanner.nextInt();
            if (ticketReleaseRate > 0) break;
            System.out.println("Invalid input! Ticket release rate must be greater than 0.");
        }

        // Validate and set customerRetrievalRate
        while (true) {
            System.out.print("Enter customer retrieval rate (in seconds): ");
            customerRetrievalRate = scanner.nextInt();
            if (customerRetrievalRate > 0) break;
            System.out.println("Invalid input! Customer retrieval rate must be greater than 0.");
        }

        // Validate and set maxTicketCapacity
        while (true) {
            System.out.print("Enter max ticket capacity: ");
            maxTicketCapacity = scanner.nextInt();
            if (maxTicketCapacity > 0) break;
            System.out.println("Invalid input! Max ticket capacity must be greater than 0.");
        }

        // Save the configuration to a file
        saveConfiguration();

        System.out.println("System configured successfully!");
    }

    // Method to save the current configuration to a file
    private void saveConfiguration() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write("Date: " + new Date() + "\n");
            writer.write("totalTickets=" + totalTickets + "\n");
            writer.write("ticketReleaseRate=" + ticketReleaseRate + "\n");
            writer.write("customerRetrievalRate=" + customerRetrievalRate + "\n");
            writer.write("maxTicketCapacity=" + maxTicketCapacity + "\n");
            System.out.println("Configuration saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Method to load the configuration from the file
    private void loadPreviousConfiguration() {
        try (Scanner fileScanner = new Scanner(new File(CONFIG_FILE))) {
            System.out.println("Loading previous configuration:");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("totalTickets=")) {
                    totalTickets = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("ticketReleaseRate=")) {
                    ticketReleaseRate = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("customerRetrievalRate=")) {
                    customerRetrievalRate = Integer.parseInt(line.split("=")[1]);
                } else if (line.startsWith("maxTicketCapacity=")) {
                    maxTicketCapacity = Integer.parseInt(line.split("=")[1]);
                }
                System.out.println(line);
            }
            System.out.println("Previous configuration loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    // Getters for parameters
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}
