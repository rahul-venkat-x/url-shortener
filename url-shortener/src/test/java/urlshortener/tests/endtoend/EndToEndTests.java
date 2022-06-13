package urlshortener.tests.endtoend;

import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import urlshortener.tests.testdata.TestData;
import urlshortener.UrlShortenerApplication;
import urlshortener.model.UrlRequestDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class EndToEndTests {

	@Autowired
	private RestTemplate restTemplate;

	private final String LOCAL_HOST = "http://localhost:8090/";
	private final String GET_SHORT_URL_ENDPOINT = "getShortUrl";
	private String generatedShortURL;

	public void testGeneratedShortUrl() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\91997\\Desktop\\chromedriver.exe");
		log.info("Opening web browser");
		WebDriver driver = new ChromeDriver();
		String shortURL = LOCAL_HOST + generatedShortURL;
		log.info("Entering Url as {}", shortURL);
		driver.get(shortURL);
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
		String currentUrl = driver.getCurrentUrl();
		log.info("The webpage redirected to {}",currentUrl);
		driver.quit();
		Assert.assertEquals(currentUrl, TestData.VALID_URL);
}

	@Test
	public void urlShortenerFunctionalTest() {
		UrlRequestDto requestDto = TestData.getValidUrlRequest();
		ResponseEntity<UrlRequestDto> postResponse = this.restTemplate.postForEntity(LOCAL_HOST + GET_SHORT_URL_ENDPOINT,
				requestDto, UrlRequestDto.class);
		Assert.assertEquals(postResponse.getStatusCode(), HttpStatus.OK);
		generatedShortURL = postResponse.getBody().getShortUrl();
		assert (!generatedShortURL.isEmpty());
		CompletableFuture.runAsync(this::testGeneratedShortUrl);
		Uninterruptibles.sleepUninterruptibly(6, TimeUnit.MINUTES);
		try {
			ResponseEntity<HttpStatus> getResponse = this.restTemplate.getForEntity(LOCAL_HOST + generatedShortURL,
					HttpStatus.class);
			Assert.fail("Expected to throw Exception");
		} catch (HttpClientErrorException ex) {
			Assert.assertEquals(ex.getStatusCode(), HttpStatus.BAD_REQUEST);
			Assert.assertEquals(ex.getResponseBodyAsString(), "Given short url not available in database or maybe expired");
			log.info("The URL data is deleted from the database");
		}
	}

}
