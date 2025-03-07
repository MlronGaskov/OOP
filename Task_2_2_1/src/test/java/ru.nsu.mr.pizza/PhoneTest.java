package ru.nsu.mr.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

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

        assertEquals(1, messages.size());
        assertEquals(testMessage, messages.getFirst());
    }
}
