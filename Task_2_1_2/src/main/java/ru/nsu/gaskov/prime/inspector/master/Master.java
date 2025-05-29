package ru.nsu.gaskov.prime.inspector.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the master process that
 * reads numbers and checks primality using distributed workers.
 */
public class Master {

    /**
     * Main method: parses connection parameters,
     * reads input numbers, and prints whether any is non-prime. */
    public static void main(String[] args) {
        if (args.length < 6) {
            System.err.println(
                    "Usage: java Master "
                            + "<multicastAddress> "
                            + "<multicastPort> "
                            + "<interfaceName> "
                            + "<host> "
                            + "<listenPort> "
                            + "<timeoutMillis>"
            );
            System.exit(1);
        }

        String multicastAddress = args[0];
        int multicastPort;
        String host;
        String interfaceName;
        int listenPort;
        int timeoutMillis;
        try {
            multicastPort = Integer.parseInt(args[1]);
            interfaceName = args[2];
            host = args[3];
            listenPort = Integer.parseInt(args[4]);
            timeoutMillis = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.err.println(
                    "Invalid number format for ports or timeout: "
                            + e.getMessage()
            );
            return;
        }
        List<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter count and numbers:");

        if (!scanner.hasNextInt()) {
            System.err.println("No count provided.");
            System.exit(1);
        }
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            if (!scanner.hasNextInt()) {
                System.err.println("Expected " + count + " numbers, but got only " + i);
                System.exit(1);
            }
            numbers.add(scanner.nextInt());
        }

        scanner.close();

        if (numbers.isEmpty()) {
            System.err.println("No numbers provided via stdin.");
            System.exit(1);
        }

        try (DistributedPrimeInspector inspector = new DistributedPrimeInspector(
                multicastAddress, multicastPort, interfaceName, host, listenPort, timeoutMillis)) {

            boolean hasNonPrime = inspector.hasNonPrime(numbers);
            System.out.println(hasNonPrime);

        } catch (IOException e) {
            System.err.println(
                    "Failed to initialize DistributedPrimeInspector: "
                            + e.getMessage()
            );
            System.exit(1);
        }
    }
}
