package urlshortener.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import urlshortener.service.IShortenerService;
import urlshortener.service.ShortenerService;
import org.apache.http.impl.client.HttpClientBuilder;

@Configuration
public class RootConfig {

    @Bean
    public IShortenerService getShortenerLogic(){
        return new ShortenerService();
    }

    @Bean
    public RestTemplate getTestRestTemplate(){
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                     HttpClientBuilder.create().build());
        clientHttpRequestFactory.setReadTimeout(9_500);
        return new RestTemplate(clientHttpRequestFactory);
    }
}
