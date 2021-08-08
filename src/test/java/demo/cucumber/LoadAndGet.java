package demo.cucumber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.log.SystemLogHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.model.LdRnEnt;
import demo.model.OtherRepo;
import demo.model.TstSte;
import demo.service.LdRnEntService;
import demo.service.TstSteService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * execute a tag mvn test -Dcucumber.filter.tags="@tagx"
 * 
 */
public class LoadAndGet extends SpringIntegrationTest {
  private static final String SCHEME = "http:"; 
  private Integer tsId;
  
  @Autowired LdRnEntService ldRnEntService;
  @Autowired TstSteService tstSteService;
  
  @Given("^load of LdRnEnt \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes get loaded
  public void loadOfLdRnEnt(String name, String domain, String project) throws Throwable {
    System.out.println(String.format("(loadOfLdRnEnt) starting [name => %s, domain => %s project => %s]", name, domain, project));
    
    TstSte tstSte = new TstSte.Builder()
        .name(name)
        .build();
    
    LdRnEnt ldRnEnt = new LdRnEnt.Builder()
        .domain(domain)
        .project(project)
        .build();
    
    tstSte.setRepositoryType(ldRnEnt);
    ldRnEnt.setTstSte(tstSte);
    
    tstSteService.persist(tstSte);
  }
  
  @Then("^LdRnEnt in db no arguments \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes will be there
  public void ldRnEntInDbNoArguments(String name, String domain, String project) throws Throwable {
    System.out.println(String.format("(ldRnEntInDbNoArguments) starting [name => %s, domain => %s project => %s]", name, domain, project));
    
    assertTrue(
        tstSteService.tstStes(new TstSte()).stream().anyMatch(x -> x.getName().equals(name) &&
            x.getRepositoryType().getDtype().equals("LdRnEnt") &&
            ((LdRnEnt) x.getRepositoryType()).getDomain().equals(domain) &&
            ((LdRnEnt) x.getRepositoryType()).getProject().equals(project))
    );
  }
  
  @Given("^load of OtherRepo \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes will surround the value
  public void loadOfOtherRepo(String name, String domain, String url) throws Throwable {
    
    TstSte tstSte = new TstSte.Builder()
        .name(name)
        .build();
    
    OtherRepo otherRepo = new OtherRepo.Builder()
        .domain(domain)
        .url(url)
        .build();
    
    tstSte.setRepositoryType(otherRepo);
    otherRepo.setTstSte(tstSte);
    
    tstSteService.persist(tstSte);
  }
  
  @Then("^OtherRepo in db no arguments \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes will surround the value
  public void otherRepoInDbNoArguments(String name, String domain, String url) throws Throwable {
    
    assertTrue(
        tstSteService.tstStes(new TstSte()).stream().anyMatch(x -> x.getName().equals(name) &&
            x.getRepositoryType().getDtype().equals("OtherRepo") &&
            ((OtherRepo) x.getRepositoryType()).getDomain().equals(domain) &&
            ((OtherRepo) x.getRepositoryType()).getUrl().equals(url))
        );
  }
  
  @Given("^load of LdRnEnt using JSON \"(.*)\" \"(.*)\" \"(.*)\"")
  public void loadOfLdRnEntUsingJson(String name, String domain, String project) throws Throwable {
    System.out.println(String.format("(loadOfLdRnEntUsingJson) starting [name => %s, domain => %s project => %s]", name, domain, project));
    // if need to divide more see: https://docs.oracle.com/javase/8/docs/api/java/net/URI.html
    URI url = new URI(SCHEME + "//localhost:8080/tstSte");
    String body = "{ \"name\": \"" + name + "\",\"repositoryType\": {\"dtype\": \"LdRnEnt\",\"domain\": \"" + domain + "\",\"project\": \"" + project + "\"}}";
    executePost(url, body);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(latestResponse.getBody());
    TstSte tstSte = mapper.treeToValue(jsonNode, TstSte.class);
    assertTrue("The returned name or domain, or project or object type did not match",
        tstSte.getName().equals(name) &&
        tstSte.getRepositoryType() instanceof LdRnEnt &&
        ((LdRnEnt) tstSte.getRepositoryType()).getDomain().equals(domain) &&        
        ((LdRnEnt) tstSte.getRepositoryType()).getProject().equals(project)
    );
  }

  @Then("^LdRnEnt in db no arguments using JSON \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes will be there
  public void ldRnEntInDbNoArgumentsUsingJson(String name, String domain, String project) throws Throwable {
    System.out.println(String.format("(ldRnEntInDbNoArgumentsUsingJson) starting [name => %s, domain => %s project => %s]", name, domain, project));
    URI url = new URI(SCHEME + "//localhost:8080/tstSte");
    executeGet(url, Optional.empty());
    List<TstSte> tstStes = new ObjectMapper().readValue(latestResponse.getBody(), new TypeReference<List<TstSte>>() {}); 
//    System.out.println(tstStes.stream().map(Objects::toString).collect(Collectors.joining(System.lineSeparator())));
    
    assertTrue("The values not found in the stream",
        tstStes.stream().anyMatch(x -> x.getName().equals(name) &&
            x.getRepositoryType().getDtype().equals("LdRnEnt") &&
            ((LdRnEnt) x.getRepositoryType()).getDomain().equals(domain) &&
            ((LdRnEnt) x.getRepositoryType()).getProject().equals(project))
    );
  }
  
  @Given("^load of OtherRepo using JSON \"(.*)\" \"(.*)\" \"(.*)\"")
  public void loadOfOtherRepoUsingJson(String name, String domain, String url) throws Throwable {
    System.out.println(String.format("(loadOfOtherRepoUsingJson) starting [name => %s, domain => %s url => %s]", name, domain, url));
    // if need to divide more see: https://docs.oracle.com/javase/8/docs/api/java/net/URI.html
    URI url01 = new URI(SCHEME + "//localhost:8080/tstSte");
    String body = "{ \"name\": \"" + name + "\",\"repositoryType\": {\"dtype\": \"OtherRepo\",\"domain\": \"" + domain + "\",\"url\": \"" + url + "\"}}";
    executePost(url01, body);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(latestResponse.getBody());
    TstSte tstSte = mapper.treeToValue(jsonNode, TstSte.class);
    tsId = tstSte.getTsId();
    assertTrue("The returned name or domain, or project or object type did not match",
        tstSte.getName().equals(name) &&
        tstSte.getRepositoryType() instanceof OtherRepo &&
        ((OtherRepo) tstSte.getRepositoryType()).getDomain().equals(domain) &&        
        ((OtherRepo) tstSte.getRepositoryType()).getUrl().equals(url)
        );
  }
  
  @Then("^OtherRepo in db no arguments using JSON \"(.*)\" \"(.*)\" \"(.*)\"") // do like this or double quotes will be there
  public void otherRepoInDbNoArgumentsUsingJson(String name, String domain, String url) throws Throwable {
    System.out.println(String.format("(ldRnEntInDbNoArgumentsUsingJson) starting [name => %s, domain => %s url => %s]", name, domain, url));
    URI url01 = new URI(SCHEME + "//localhost:8080/tstSte");
    executeGet(url01, Optional.empty());
    List<TstSte> tstStes = new ObjectMapper().readValue(latestResponse.getBody(), new TypeReference<List<TstSte>>() {}); 
//    System.out.println(tstStes.stream().map(Objects::toString).collect(Collectors.joining(System.lineSeparator())));
    assertTrue("The values not found in the stream",
        tstStes.stream().anyMatch(x -> x.getName().equals(name) &&
            x.getRepositoryType().getDtype().equals("OtherRepo") &&
            ((OtherRepo) x.getRepositoryType()).getDomain().equals(domain) &&
            ((OtherRepo) x.getRepositoryType()).getUrl().equals(url))
        );
    
  }
  
  @And("^OtherRepo by Test Suite key JSON \"(.*)\" \"(.*)\" \"(.*)\"")
  public void otherRepoByTestSuiteKeyJson(String name, String domain, String url) throws Throwable {
    System.out.println(String.format("(otherRepoByTestSuiteKeyJson) starting [tsId = %s]", tsId));
    URI url01 = new URI(SCHEME + "//localhost:8080/tstSte/tstSte?tsId=" + tsId);
    executeGet(url01, Optional.empty());
//    System.out.println(String.format("(otherRepoByTestSuiteKeyJson) [latestResponse.getBody() => %s]", latestResponse.getBody()));
    TstSte tstSte = new ObjectMapper().readValue(latestResponse.getBody(), TstSte.class);
    assertTrue("RepositoryType should be of type OtherRepo", tstSte.getRepositoryType() instanceof OtherRepo);
    assertNotNull("Could not find required record with tsId = " + tsId + " object is null", tstSte);
    assertTrue("One or more values not matching " +
        "tstSte.id=" + tstSte.getTsId() + ", tsId=" + tsId +
        ", tstSte.name=" + tstSte.getName() + ", name=" + name +
        ", tstSte.RepositoryType.dtype=" + tstSte.getRepositoryType().getDtype() + ", dtype=" + "OtherRepo" +
        ", ((OtherRepo) tstSte.RepositoryType).domain=" + ((OtherRepo) tstSte.getRepositoryType()).getDomain() + ", domain=" + domain +
        ", ((OtherRepo) tstSte.RepositoryType).url=" + ((OtherRepo) tstSte.getRepositoryType()).getUrl() + ", url=" + url
        , 
        tstSte.getTsId().equals(tsId)
        && tstSte.getName().equals(name)
        && tstSte.getRepositoryType().getDtype().equals("OtherRepo")
        && ((OtherRepo) tstSte.getRepositoryType()).getDomain().equals(domain)
        && ((OtherRepo) tstSte.getRepositoryType()).getUrl().equals(url)
    );
  }
}
