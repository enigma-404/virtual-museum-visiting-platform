package org.enigma.virtualmuseumvisitingplatform.core.utilities.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SuccessResultTest {

    private SuccessResult successResult;

    @BeforeEach
    public void setUp() {
        // This method can be used to set up common test data if needed
    }

    @Test
    public void testSuccessResultWhenNoArgConstructorThenIsSuccessTrue() {
        // Arrange
        successResult = new SuccessResult();

        // Act & Assert
        assertTrue(successResult.isSuccess(), "The isSuccess property should be true for no-arg constructor");
    }

    @Test
    public void testSuccessResultWhenMessageConstructorThenIsSuccessTrueAndMessageSet() {
        // Arrange
        String message = "Operation successful";
        successResult = new SuccessResult(message);

        // Act & Assert
        assertTrue(successResult.isSuccess(), "The isSuccess property should be true for message constructor");
        assertEquals(message, successResult.getMessage(), "The message property should be set to the provided message");
    }
}
