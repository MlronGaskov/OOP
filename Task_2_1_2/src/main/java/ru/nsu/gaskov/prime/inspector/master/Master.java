package ru.nsu.gaskov.prime.inspector.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Master {
    public static void main(String[] args) {
        if (args.length < 6) {
            System.err.println("Usage: java Master <multicastAddress> <multicastPort> <host> <listenPort> <timeoutMillis> <num1> <num2> ...");
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
        for (int i = 5; i < args.length; i++) {
            try {
                numbers.add(Integer.parseInt(args[i]));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number: " + args[i]);
                System.exit(1);
            }
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

