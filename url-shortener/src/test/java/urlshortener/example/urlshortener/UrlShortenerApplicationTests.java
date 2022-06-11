package urlshortener.example.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import urlshortener.UrlShortenerApplication;
import urlshortener.model.UrlRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class UrlShortenerApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	//@Test
	public void testGetShortUrl() {
		String url = "https://www.hello.com";
		UrlRequestDto requestDto = new UrlRequestDto();
		requestDto.setOriginalUrl(url);
		requestDto.setShortUrl(null);
		UrlRequestDto responseDto = this.restTemplate.postForEntity("http://localhost:8090/getShortUrl", requestDto,UrlRequestDto.class,"").getBody();
		String shortUrl = responseDto.getShortUrl();
		assert(!shortUrl.isEmpty());
	}

}
