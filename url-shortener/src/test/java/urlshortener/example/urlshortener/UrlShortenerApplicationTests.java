package urlshortener.example.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import urlshortener.UrlShortenerApplication;
import urlshortener.controller.ShortenerController;
import urlshortener.model.Url;
import urlshortener.model.UrlRequestDto;
import urlshortener.model.UrlResponseDto;
import urlshortener.service.IShortenerService;
import urlshortener.urlRepository.IUrlRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class UrlShortenerApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@InjectMocks
	private ShortenerController shortenerController;

	@Mock
	private IShortenerService shortenerService;

	@Mock
	private IUrlRepository urlRepository;

	@Test
	public void testGetShortUrl() throws InterruptedException {
		String url = "https://www.hello.com";
		UrlRequestDto requestDto = new UrlRequestDto();
		requestDto.setOriginalUrl(url);
		requestDto.setShortUrl(null);
		UrlRequestDto responseDto = this.restTemplate.postForEntity("http://localhost:8090/getShortUrl", requestDto,UrlRequestDto.class,"").getBody();
		String shortUrl = responseDto.getShortUrl();
		assert(!shortUrl.isEmpty());
	}

}
