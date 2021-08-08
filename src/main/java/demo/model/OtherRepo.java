package demo.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "other_repo")
//@DiscriminatorValue(value = "otherRepo") // no access to dtype
public class OtherRepo extends RepositoryType {

  @Column private String domain; // cannot set nullable = false as repository type table is set that way 
  @Column private String url;
  
  public String getDomain() {
    return domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  
  public static class Builder {
    // Required Parameters
    
    // Optional Parameters
    private String domain;
    private String url;
//    private String type; // not needed as set by @DiscriminatorValue or dtype column
//    private String tstSteName;

    public Builder domain(String domain) {this.domain = domain; return this;}
    public Builder url(String url) {this.url = url; return this;}
//    public Builder type(String type) {this.type = type; return this;}
//    public Builder tstSteName(String tstSteName) {this.tstSteName = tstSteName; return this;}
    public OtherRepo build() {return new OtherRepo(this);}
  }
  
  public OtherRepo() {}
  
  private OtherRepo(Builder builder) {
    this.domain = builder.domain;
    this.url = builder.url;
//    this.type = builder.type; // if in repository type the access is private will not work
//    this.setType(builder.type); // works regardless of access modifier not needed as set by @DiscriminatorValue
//    this.setTstSte(); // TODO figure out how to create safely
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    OtherRepo other = (OtherRepo) obj;
    if (domain == null) {
      if (other.domain != null)
        return false;
    } else if (!domain.equals(other.domain))
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "LdRnEnt [domain=" + domain + ", url=" + url + ", toString()=" + super.toString() + "]";
  }
  
}
