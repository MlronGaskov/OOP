package ru.nsu.gaskov.prime.inspector.worker;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.List;

/**
 * Worker process that listens for master connections
 * and checks numbers for primality.
 */
public class Worker {

    /**
     * Starts the worker, discovers masters, receives numbers,
     * checks for composites, and sends results.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(
                    "Usage: java Worker "
                            + "<multicastAddress> "
                            + "<multicastPort> "
                            + "<networkInterfaceName>"
            );
            System.exit(1);
        }
        String multicastAddress = args[0];
        int multicastPort = Integer.parseInt(args[1]);
        String netIfName = args[2];

        NetworkInterface netIf;
        try {
            netIf = NetworkInterface.getByName(netIfName);
            if (netIf == null) {
                System.err.println("Network interface not found: " + netIfName);
                System.exit(1);
                return;
            }
        } catch (IOException e) {
            System.err.println("Error looking up network interface: " + e.getMessage());
            System.exit(1);
            return;
        }

        MasterConnector connector;
        try {
            connector = new MasterConnector(multicastAddress, multicastPort, netIf);
        } catch (IOException e) {
            System.err.println("Failed to set up MasterConnector: " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.println("Worker started. Waiting for masters on "
                + multicastAddress + ":" + multicastPort + " via interface " + netIfName);

        while (true) {
            try {
                System.out.println("Searching for master...");
                connector.findMaster();
                System.out.println("Master found.");

                List<Integer> numbers = connector.receiveIntList();
                System.out.println("Received " + numbers.size() + " numbers.");

                boolean hasComposite = numbers.stream().anyMatch(PrimeChecker::isNotPrime);

                System.out.println("Has composite? " + hasComposite);

                connector.sendBoolean(hasComposite);
                connector.closeMasterSocket();
                System.out.println("Response sent, socket closed.");

            } catch (IOException e) {
                System.err.println("I/O error: " + e.getMessage());
                break;
            }
        }
    }
}

