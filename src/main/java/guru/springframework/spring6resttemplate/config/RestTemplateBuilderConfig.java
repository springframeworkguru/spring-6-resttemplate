package guru.springframework.spring6resttemplate.config;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.autoconfigure.RestTemplateBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
public class RestTemplateBuilderConfig {

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){

        return configurer.configure(new RestTemplateBuilder())
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(2));
    }
}
