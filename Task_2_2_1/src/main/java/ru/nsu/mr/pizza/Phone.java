package ru.nsu.mr.pizza;

import java.util.function.Consumer;

/**
 * A simple phone that notifies a consumer when a call is made.
 */
public class Phone {
    private final Consumer<String> onCall;

    /**
     * Constructs a Phone with the specified call handler.
     *
     * @param onCall a consumer that handles call messages
     */
    public Phone(Consumer<String> onCall) {
        this.onCall = onCall;
    }

    /**
     * Calls the phone by passing the message to the call handler.
     *
     * @param message the message to be sent
     */
    public void call(String message) {
        this.onCall.accept(message);
    }
}
