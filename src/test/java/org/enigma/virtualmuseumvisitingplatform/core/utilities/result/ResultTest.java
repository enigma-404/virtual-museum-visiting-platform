package org.enigma.virtualmuseumvisitingplatform.core.utilities.result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ResultTest {

    @Test
    public void testGetIsSuccessWhenIsSuccessIsTrueThenReturnTrue() {
        // Arrange
        Result result = new Result(true);

        // Act
        boolean isSuccess = result.isSuccess();

        // Assert
        assertTrue(isSuccess);
    }

    @Test
    public void testGetIsSuccessWhenIsSuccessIsFalseThenReturnFalse() {
        // Arrange
        Result result = new Result(false);

        // Act
        boolean isSuccess = result.isSuccess();

        // Assert
        assertFalse(isSuccess);
    }

    @Test
    public void testGetMessageWhenMessageIsNullThenReturnNull() {
        // Arrange
        Result result = new Result(true);

        // Act
        String message = result.getMessage();

        // Assert
        assertNull(message);
    }

    @Test
    public void testGetMessageWhenMessageIsNotNullThenReturnMessage() {
        // Arrange
        String expectedMessage = "Success";
        Result result = new Result(true, expectedMessage);

        // Act
        String message = result.getMessage();

        // Assert
        assertEquals(expectedMessage, message);
    }

    @Test
    public void testResultConstructorWhenNoArgumentsThenSetIsSuccessToFalseAndMessageToNull() {
        // Arrange & Act
        Result result = new Result();

        // Assert
        assertFalse(result.isSuccess());
        assertNull(result.getMessage());
    }

    @Test
    public void testResultConstructorWhenIsSuccessArgumentThenSetIsSuccessAndMessageToGivenValues() {
        // Arrange
        boolean expectedIsSuccess = true;

        // Act
        Result result = new Result(expectedIsSuccess);

        // Assert
        assertEquals(expectedIsSuccess, result.isSuccess());
        assertNull(result.getMessage());
    }

    @Test
    public void testResultConstructorWhenIsSuccessAndMessageArgumentsThenSetIsSuccessAndMessageToGivenValues() {
        // Arrange
        boolean expectedIsSuccess = true;
        String expectedMessage = "Success";

        // Act
        Result result = new Result(expectedIsSuccess, expectedMessage);

        // Assert
        assertEquals(expectedIsSuccess, result.isSuccess());
        assertEquals(expectedMessage, result.getMessage());
    }
}
