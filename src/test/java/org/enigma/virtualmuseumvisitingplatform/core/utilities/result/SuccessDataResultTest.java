package org.enigma.virtualmuseumvisitingplatform.core.utilities.result;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SuccessDataResultTest {

    @Test
    public void testSuccessDataResultWhenDataPassedThenSuccessAndData() {
        // Arrange
        String testData = "Test Data";

        // Act
        SuccessDataResult<String> result = new SuccessDataResult<>(testData);

        // Assert
        assertTrue(result.isSuccess(), "The result should be successful.");
        assertEquals(testData, result.getData(), "The data should match the input data.");
    }
}
