package org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryNotFoundExceptionTest {

    @Test
    public void testCategoryNotFoundExceptionWhenCreatedThenMessageIsSetAndCanBeRetrieved() {
        // Arrange
        String expectedMessage = "Category not found";

        // Act
        CategoryNotFoundException exception = new CategoryNotFoundException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testCategoryNotFoundExceptionWhenCategoryNotFoundThenExceptionIsThrown() {
        // Arrange
        String expectedMessage = "Category not found";

        // Act & Assert
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            throw new CategoryNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
