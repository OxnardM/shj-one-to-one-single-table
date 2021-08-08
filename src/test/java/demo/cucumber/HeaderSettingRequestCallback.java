package demo.cucumber;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;

import java.io.IOException;
import java.util.Map;

public class HeaderSettingRequestCallback implements RequestCallback {
  final Map<String, String> requestHeaders;
  private String body;

  
  public static class Builder {
    // Required Parameters
    private final Map<String, String> requestHeaders;
    
    // Optional Parameters - initialized to default values if required
    private String body;
    
    public Builder (Map<String, String> requestHeaders) {
      this.requestHeaders = requestHeaders;
    }
    
    public Builder body(String body) {this.body = body; return this;}
    public HeaderSettingRequestCallback build() {return new HeaderSettingRequestCallback(this);}
  }
  
  public HeaderSettingRequestCallback(final Map<String, String> headers) {
    this.requestHeaders = headers;
  }

  private HeaderSettingRequestCallback(Builder builder) {
    this.requestHeaders = builder.requestHeaders;
    this.body = builder.body;
  }

  public void setBody(final String postBody) {
    this.body = postBody;
  }

  @Override
  public void doWithRequest(ClientHttpRequest request) throws IOException {
//    System.out.println(String.format("(doWithRequest) starting [body => %s]", body));
    final HttpHeaders clientHeaders = request.getHeaders();
    for (final Map.Entry<String, String> entry : requestHeaders.entrySet()) {
      clientHeaders.add(entry.getKey(), entry.getValue());
    }
    if (null != body) {
      request.getBody().write(body.getBytes()); // does not work with a GET request see RFC
    }
  }
}