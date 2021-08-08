package demo.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "ld_rn_ent")
//@DiscriminatorValue(value = "ldRnEnt") // no access to dtype
public class LdRnEnt extends RepositoryType {

  @Column private String domain;  // cannot set nullable = false as repository type table is set that way
  @Column private String project;
  
  public String getDomain() {
    return domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }
  public String getProject() {
    return project;
  }
  public void setProject(String project) {
    this.project = project;
  }
  
  public static class Builder {
    // Required Parameters
    
    // Optional Parameters
    private String domain;
    private String project;
//    private String type; // not needed as set by @DiscriminatorValue or dtype column
//    private String tstSteName;

    public Builder domain(String domain) {this.domain = domain; return this;}
    public Builder project(String project) {this.project = project; return this;}
//    public Builder type(String type) {this.type = type; return this;}
//    public Builder tstSteName(String tstSteName) {this.tstSteName = tstSteName; return this;}
    public LdRnEnt build() {return new LdRnEnt(this);}
  }
  
  public LdRnEnt() {}
  
  private LdRnEnt(Builder builder) {
    this.domain = builder.domain;
    this.project = builder.project;
//    this.type = builder.type; // if in repository type the access is private will not work
//    this.setType(builder.type); // works regardless of access modifier not needed as set by @DiscriminatorValue
//    this.setTstSte(); // TODO figure out how to create safely
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((project == null) ? 0 : project.hashCode());
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
    LdRnEnt other = (LdRnEnt) obj;
    if (domain == null) {
      if (other.domain != null)
        return false;
    } else if (!domain.equals(other.domain))
      return false;
    if (project == null) {
      if (other.project != null)
        return false;
    } else if (!project.equals(other.project))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "LdRnEnt [domain=" + domain + ", project=" + project + ", toString()=" + super.toString() + "]";
  }
  
}
