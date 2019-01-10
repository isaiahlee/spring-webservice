package com.isaiahlee224.webservice.config;

import com.isaiahlee224.webservice.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@Configuration
@PropertySource(value = "classpath:config/config-${spring.profiles.active}.properties")
@EnableConfigurationProperties
public class ApplicationConfig {

  @Value("${spring.profiles.active}") private String activeProfile;
  @Value("${rest.template.read.timeout}") private int restTemplateReadTimeout;
  @Value("${rest.template.connect.timeout}") private int restTemplateConnectTimeout;
  @Value("${rest.template.connection.request.timeout}") private int restTemplateConnectionRequestTimeout;
  @Value("${rest.template.max.connection.total}") private int restTemplateMaxConnectionTotal;
  @Value("${rest.template.max.connection.per.route}") private int restTemplateMaxConnectionPerRoute;
  @Value("${upload.max.filesize}") private int uploadMaxFileSize;
  @Value("${upload.max.requestsize}") private int uploadMaxRequestSize;

  @Bean
  public RestTemplate restTemplate() {

    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

    int index = 0;
    for (HttpMessageConverter messageConverter : restTemplate.getMessageConverters()) {
      if (messageConverter instanceof StringHttpMessageConverter) {
        break;
      }
      index++;
    }

    List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
    httpMessageConverters.remove(index);
    httpMessageConverters.add(index, new StringHttpMessageConverter(Charset.forName(Constants.UTF8)));

    return restTemplate;
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        HttpClientBuilder.create().setMaxConnTotal(restTemplateMaxConnectionTotal)
            .setMaxConnPerRoute(restTemplateMaxConnectionPerRoute).setRetryHandler(new HttpRequestRetryHandler() {
          @Override
          public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

            if (executionCount > Constants.HTTP_REQUEST_RETRY_MAX_COUNT) {
              return false;
            }

            return true;
          }
        }).build());
    factory.setReadTimeout(restTemplateReadTimeout);
    factory.setConnectTimeout(restTemplateConnectTimeout);
    factory.setConnectionRequestTimeout(restTemplateConnectionRequestTimeout);
    return factory;
  }
}
