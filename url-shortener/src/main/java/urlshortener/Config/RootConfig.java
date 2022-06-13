package urlshortener.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.HttpClientBuilder;

@Configuration
public class RootConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                     HttpClientBuilder.create().build());
        return new RestTemplate(clientHttpRequestFactory);
    }
}
