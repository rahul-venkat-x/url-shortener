package urlshortener.tests.unittest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import urlshortener.tests.testdata.TestData;
import urlshortener.controller.ShortenerController;
import urlshortener.exceptions.ShortenerExceptions;
import urlshortener.model.UrlResponseDto;
import urlshortener.service.IShortenerService;
import urlshortener.service.SequenceGeneratorService;
import urlshortener.service.ShortenerService;
import urlshortener.urlRepository.IUrlRepository;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Mock
    private IUrlRepository urlRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @Mock
    private MongoTemplate mongoTemplate;

    private IShortenerService shortenerService;

    private ShortenerController shortenerController;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.shortenerService = new ShortenerService(urlRepository,sequenceGenerator,mongoTemplate);
        this.shortenerController = new ShortenerController(shortenerService);
    }

    @Test(expected = ShortenerExceptions.class)
    public void shortInvalidUrl(){
        shortenerController.getShortUrl(TestData.getInvalidUrlRequest());
    }

    @Test
    public void shortValidUrl(){
        ResponseEntity<UrlResponseDto> response= shortenerController.getShortUrl(TestData.getValidUrlRequest());
        Assert.assertNotNull(response.getBody().getShortUrl());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shortSameUrlTwoTimes() {
        UrlResponseDto response1 = shortenerController.getShortUrl(TestData.getValidUrlRequest()).getBody();
        Assert.assertNotNull(response1);
        UrlResponseDto response2 = shortenerController.getShortUrl(TestData.getValidUrlRequest()).getBody();
        Assert.assertNotNull(response2);
        Assert.assertNotEquals(response1.getShortUrl(),response2.getShortUrl());
    }

    @Test
    public void getOriginalUrl() throws IOException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        doReturn(TestData.sampleUrl()).when(urlRepository).findByShortUrl(anyString());
        doReturn(true).when(urlRepository).existsByShortUrl(anyString());
        HttpStatus statusCode = shortenerController.getOriginalUrl(anyString(),mockHttpServletResponse).getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.OK);
    }

    @Test
    public void updateOriginalUrl(){
        when(urlRepository.existsByShortUrl(anyString())).thenReturn(true);
        HttpStatus statusCode = shortenerController.updateOriginalUrl(TestData.getValidUrlRequestWithShortUrl()).getStatusCode();
        Assert.assertEquals(statusCode,HttpStatus.OK);
    }

    @Test(expected = ShortenerExceptions.class)
    public void updateOriginalUrlForNotAvailableShortUrl(){
        when(urlRepository.existsByShortUrl(anyString())).thenReturn(false);
        shortenerController.updateOriginalUrl(TestData.getValidUrlRequestWithShortUrl()).getStatusCode();
    }

}
