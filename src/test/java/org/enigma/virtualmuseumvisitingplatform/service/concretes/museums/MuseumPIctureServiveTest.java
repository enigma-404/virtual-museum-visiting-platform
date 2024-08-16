package org.enigma.virtualmuseumvisitingplatform.service.concretes.museums;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.pictures.BaseReadAndWritePicture;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.entity.museums.MuseumPicture;
import org.enigma.virtualmuseumvisitingplatform.repository.museum.MuseumPictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MuseumPictureServiceTest {

    @Mock
    private MuseumPictureRepository museumPictureRepository;

    @Mock
    private BaseReadAndWritePicture baseReadAndWritePicture;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private MuseumPictureService museumPictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Use reflection to set the @Value fields
        setPrivateField(museumPictureService, "pictureUrl", "http://example.com/images/");
        setPrivateField(museumPictureService, "picturePath", "/images/");
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
    void testSavePicture() {
        // Arrange
        Long museumId = 1L;

        // Act
        Result result = museumPictureService.savePicture(museumId, file);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Saved picture", result.getMessage());

        // Verify that the picture was saved in the repository
        verify(museumPictureRepository, times(1)).save(any(MuseumPicture.class));
        // Verify that the picture was written to the file system
        verify(baseReadAndWritePicture, times(1)).writePicture(eq(file), anyString());
    }

    @Test
    void testFindPictureByMuseumId() {
        // Arrange
        Long museumId = 1L;
        List<String> urls = Arrays.asList("http://example.com/images/1_2024-08-16T10:00:00", "http://example.com/images/1_2024-08-16T10:00:01");
        when(museumPictureRepository.findUrlsByMuseumId(museumId)).thenReturn(urls);

        // Act
        DataResult<List<String>> result = museumPictureService.findPictureByMuseumId(museumId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(urls, result.getData());
        assertEquals("Successfully found picture urls", result.getMessage());

        // Verify that the repository method was called
        verify(museumPictureRepository, times(1)).findUrlsByMuseumId(museumId);
    }
}
