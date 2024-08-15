package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
  private final String url;

  private final RestTemplate http = new RestTemplate();

  @Autowired
  public RestClient(@Value("${mymakler.svc.api.url}") String url) {
    if (url == null || url.isEmpty()) {
      throw new IllegalStateException("Set 'mymakler.svc.api.url' in application.properties.");
    }
    this.url = url;
  }

  public <T> ResponseEntity<T> get(String endpoint, Class<T> clazz) throws RestClientException {
    return http.getForEntity(url + endpoint, clazz);
  }

  public <T> ResponseEntity<T> post(String endpoint, Object request, Class<T> clazz) throws RestClientException {
    return http.postForEntity(url + endpoint, request, clazz);
  }

  public <T> ResponseEntity<T> put(String endpoint, Object request, Class<?> clazz) throws RestClientException {
    return http.execute(
        url + endpoint,
        HttpMethod.PUT,
        http.httpEntityCallback(request, clazz),
        http.responseEntityExtractor(clazz)
    );
  }
}
