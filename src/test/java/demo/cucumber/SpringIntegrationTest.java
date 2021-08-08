package demo.cucumber;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import demo.App;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = App.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {
  static ResponseResults latestResponse = null;

  @Autowired
  protected RestTemplate restTemplate; // creating a bean in the App class

  void executeGet(URI url, Optional<String> body) throws IOException {
    System.out.println(String.format("(executeGet) starting [url => %s, body => %s]", url, body));
    final Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "application/json");    
    System.out.println(String.format("(executeGet) [body => %s]", body.orElse(null)));
    final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback.Builder(headers).body(body.orElse(null)).build();
    final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
    restTemplate.setErrorHandler(errorHandler);
    latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
      if (errorHandler.hadError) {
        return (errorHandler.getResults());
      } else {
        return (new ResponseResults(response));
      }
    });
  }

   protected void executePost(URI url, String body) throws IOException {
    System.out.println(String.format("(executePost) starting [url => %s, body => %s]", url, body));
    final Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "application/json");
    headers.put(HttpHeaders.CONTENT_TYPE,  MediaType.APPLICATION_JSON_VALUE); // must tell spring what it is getting
    final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback.Builder(headers).body(body).build();
    final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
    if (restTemplate == null) restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(errorHandler);
    latestResponse = restTemplate.execute(url, HttpMethod.POST, requestCallback,
        response -> {
          if (errorHandler.hadError) {
            return (errorHandler.getResults());
          } else {
            return (new ResponseResults(response));
          }
        });
  }

  private class ResponseResultErrorHandler implements ResponseErrorHandler {
    private ResponseResults results = null;
    private Boolean hadError = false;

    private ResponseResults getResults() {
      return results;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
      hadError = response.getRawStatusCode() >= 400;
      return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
      results = new ResponseResults(response);
    }
  }
}