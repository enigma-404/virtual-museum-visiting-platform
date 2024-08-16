package org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadAndWritePictureTest {

    private ReadAndWritePicture readAndWritePicture;

    @BeforeEach
    void setUp() {
        readAndWritePicture = new ReadAndWritePicture();
    }

    @Test
    void testReadPicture_Success() throws IOException {
        // Arrange
        String picturePath = "testPicture.png";
        byte[] expectedBytes = {1, 2, 3, 4};

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Paths.get(picturePath)))
                    .thenReturn(expectedBytes);

            // Act
            byte[] actualBytes = readAndWritePicture.readPicture(picturePath);

            // Assert
            assertNotNull(actualBytes);
            assertArrayEquals(expectedBytes, actualBytes);
        }
    }

    @Test
    void testReadPicture_Failure() throws IOException {
        // Arrange
        String picturePath = "nonExistentPicture.png";

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.readAllBytes(Paths.get(picturePath)))
                    .thenThrow(new IOException("File not found"));

            // Act
            byte[] actualBytes = readAndWritePicture.readPicture(picturePath);

            // Assert
            assertNull(actualBytes);  // Expecting null if the file can't be read
        }
    }

    @Test
    void testWritePicture_Success() throws IOException {
        // Arrange
        String picturePath = "testPicture.png";
        byte[] fileBytes = {1, 2, 3, 4};
        MockMultipartFile mockFile = new MockMultipartFile("file", "testPicture.png", "image/png", fileBytes);

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(Paths.get(picturePath), fileBytes))
                    .thenReturn(Paths.get(picturePath));

            // Act
            readAndWritePicture.writePicture(mockFile, picturePath);

            // Assert
            mockedFiles.verify(() -> Files.write(Paths.get(picturePath), fileBytes), times(1));
        }
    }

    @Test
    void testWritePicture_Failure() throws IOException {
        // Arrange
        String picturePath = "testPicture.png";
        byte[] fileBytes = {1, 2, 3, 4};
        MockMultipartFile mockFile = new MockMultipartFile("file", "testPicture.png", "image/png", fileBytes);

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(Paths.get(picturePath), fileBytes))
                    .thenThrow(new IOException("Write failed"));

            // Act & Assert
            assertDoesNotThrow(() -> readAndWritePicture.writePicture(mockFile, picturePath));
            mockedFiles.verify(() -> Files.write(Paths.get(picturePath), fileBytes), times(1));
        }
    }
}
