package org.enigma.virtualmuseumvisitingplatform.core.utilities.result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ErrorDataResultTest {

    @Test
    public void testErrorDataResultWhenDataThenSuccessAndDataReturned() {
        // Arrange
        String data = "Error data";
        
        // Act
        ErrorDataResult<String> errorDataResult = new ErrorDataResult<>(data);
        
        // Assert
        assertFalse(errorDataResult.isSuccess(), "Expected isSuccess to be false for ErrorDataResult");
        assertEquals(data, errorDataResult.getData(), "Expected data to be returned correctly");
    }

    @Test
    public void testErrorDataResultWhenDataAndMessageThenSuccessAndMessageReturned() {
        // Arrange
        String data = "Error data";
        String message = "Error message";
        
        // Act
        ErrorDataResult<String> errorDataResult = new ErrorDataResult<>(data, message);
        
        // Assert
        assertFalse(errorDataResult.isSuccess(), "Expected isSuccess to be false for ErrorDataResult");
        assertEquals(data, errorDataResult.getData(), "Expected data to be returned correctly");
        assertEquals(message, errorDataResult.getMessage(), "Expected message to be returned correctly");
    }
}
