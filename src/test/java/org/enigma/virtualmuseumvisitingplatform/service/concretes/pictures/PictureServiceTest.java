package org.enigma.virtualmuseumvisitingplatform.service.concretes.pictures;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures.BaseReadAndWritePicture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

class PictureServiceTest {

    @Mock
    private BaseReadAndWritePicture readAndWritePicture;

    @InjectMocks
    private PictureService pictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Use reflection to set the @Value field
        setPrivateField(pictureService, "picturePath", "/images/");
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetPicture() {
        // Arrange
        String fileName = "testImage.png";
        byte[] expectedPictureData = new byte[]{1, 2, 3, 4}; // Example byte data for the image

        when(readAndWritePicture.readPicture("/images/" + fileName)).thenReturn(expectedPictureData);

        // Act
        byte[] actualPictureData = pictureService.getPicture(fileName);

        // Assert
        assertArrayEquals(expectedPictureData, actualPictureData);
        verify(readAndWritePicture, times(1)).readPicture("/images/" + fileName);
    }
}
