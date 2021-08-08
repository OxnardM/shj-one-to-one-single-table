package demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tst_ste")
public class TstSte {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tst_ste_id") private Integer tsId;
  
  @Column(name = "name") private String name;
  
  @OneToOne(mappedBy = "tstSte", orphanRemoval = true, cascade = CascadeType.ALL)
  @JsonManagedReference // prevents endless loop in Jackson
  private RepositoryType repositoryType;
  
  public static class Builder {
    // Required Parameters
    
    // Optional Parameters
    private Integer tsId;
    private String name;
//    private String ldRnEntDomain; // TODO figure out how to create safely
//    private String ldRnEntProject; // TODO figure out how to create safely
    
    public Builder name(String name) {this.name = name; return this;}
    public Builder tsId(Integer tsId) {this.tsId = tsId; return this;}
//    public Builder ldRnEntDomain(String ldRnEntDomain) {this.ldRnEntDomain = ldRnEntDomain; return this;}
//    public Builder ldRnEntProject(String ldRnEntProject) {this.ldRnEntProject = ldRnEntProject; return this;}
    public TstSte build() {return new TstSte(this);}
  }
  
  public TstSte() {}
  
  private TstSte(Builder builder) {
    this.tsId = builder.tsId;
    this.name = builder.name;
  }

  public Integer getTsId() {
    return tsId;
  }

  public void setTsId(Integer tsId) {
    this.tsId = tsId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RepositoryType getRepositoryType() {
    return repositoryType;
  }

  public void setRepositoryType(RepositoryType repositoryType) {
    this.repositoryType = repositoryType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((repositoryType == null) ? 0 : repositoryType.hashCode());
    result = prime * result + ((tsId == null) ? 0 : tsId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TstSte other = (TstSte) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (repositoryType == null) {
      if (other.repositoryType != null)
        return false;
    } else if (!repositoryType.equals(other.repositoryType))
      return false;
    if (tsId == null) {
      if (other.tsId != null)
        return false;
    } else if (!tsId.equals(other.tsId))
      return false;
    return true;
  }

  @Override
  public String toString() {
//    return "TstSte [tsId=" + tsId + ", name=" + name + ", repositoryType.id=" + repositoryType.getId() + ", repositoryType.type=" + repositoryType.getType() + "]";
    return "TstSte [tsId=" + tsId + ", name=" + name + ", repositoryType.id=" + repositoryType.getId() + ", repositoryType.dtype=" + repositoryType.getDtype() + "]";
//    return "TstSte [tsId=" + tsId + ", name=" + name + ", repositoryType.id=" + repositoryType.getId() + "]";
  }

}
