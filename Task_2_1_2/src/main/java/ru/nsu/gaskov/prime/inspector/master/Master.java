package ru.nsu.gaskov.prime.inspector.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the master process that reads numbers and checks primality using distributed workers.
 */
public class Master {

    /**
     * Main method: parses connection parameters, reads input numbers, and prints whether any is non-prime.
     */
    public static void main(String[] args) {
        if (args.length < 5) {
            System.err.println("Usage: java Master <multicastAddress> <multicastPort> <host> <listenPort> <timeoutMillis>");
            System.exit(1);
        }

        String multicastAddress = args[0];
        int multicastPort;
        String host;
        int listenPort;
        int timeoutMillis;
        try {
            multicastPort = Integer.parseInt(args[1]);
            host = args[2];
            listenPort = Integer.parseInt(args[3]);
            timeoutMillis = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for ports or timeout: " + e.getMessage());
            return;
        }

        List<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter numbers:");
        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextInt());
        }
        scanner.close();

        if (numbers.isEmpty()) {
            System.err.println("No numbers provided via stdin.");
            System.exit(1);
        }

        try (DistributedPrimeInspector inspector = new DistributedPrimeInspector(
                multicastAddress, multicastPort, host, listenPort, timeoutMillis)) {

            boolean hasNonPrime = inspector.hasNonPrime(numbers);
            System.out.println(hasNonPrime);

        } catch (IOException e) {
            System.err.println("Failed to initialize DistributedPrimeInspector: " + e.getMessage());
            System.exit(1);
        }
    }
}
