package ru.nsu.mr.pizza;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests.
 */
public class PhoneTest {

    @Test
    public void testCall() {
        List<String> messages = new ArrayList<>();
        Phone phone = new Phone(messages::add);

        String testMessage = "Test call message";
        phone.call(testMessage);

        assertEquals(1, messages.size(), "One message should be recorded");
        assertEquals(testMessage, messages.getFirst(), "Recorded message should match the sent message");
    }
}
