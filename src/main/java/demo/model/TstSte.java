package demo.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tst_ste")
public class TstSte {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tst_ste_id") private Integer tsId;  
  @Column private String name;
  @Column private String app;
  @Column private String description;
  @Column private Boolean active; // for Oracle default maps to an integer
  @Column private String starter;
  
  @OneToOne(mappedBy = "tstSte", orphanRemoval = true, cascade = CascadeType.ALL)
  @JsonManagedReference // prevents endless loop in Jackson
  private RepoType repoType;
  
  public static class Builder {
    // Required Parameters
    
    // Optional Parameters
    private Integer tsId;
    private String name;
    private String app;
    private String description;
    private Boolean active;
    private String starter;
//    private String ldRnEntDomain; // TODO figure out how to create safely
//    private String ldRnEntProject; // TODO figure out how to create safely
    
    public Builder tsId(Integer tsId) {this.tsId = tsId; return this;}
    public Builder name(String name) {this.name = name; return this;}
    public Builder app(String app) {this.app = app; return this;}
    public Builder description(String description) {this.description = description; return this;}
    public Builder active(Boolean active) {this.active = active; return this;}
    public Builder starter(String starter) {this.starter = starter; return this;}
//    public Builder ldRnEntDomain(String ldRnEntDomain) {this.ldRnEntDomain = ldRnEntDomain; return this;}
//    public Builder ldRnEntProject(String ldRnEntProject) {this.ldRnEntProject = ldRnEntProject; return this;}
    public TstSte build() {return new TstSte(this);}
  }
  
  public TstSte() {}
  
  private TstSte(Builder builder) {
    this.tsId = builder.tsId;
    this.name = builder.name;
    this.app = builder.app;
    this.description = builder.description;
    this.active = builder.active;
    this.starter = builder.starter;
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

  public RepoType getRepositoryType() {
    return repoType;
  }

  public void setRepositoryType(RepoType repoType) {
    this.repoType = repoType;
  }

  public String getApp() {
    return app;
  }
  
  public void setApp(String app) {
    this.app = app;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
  
  public String getStarter() {
    return starter;
  }
  
  public void setStarter(String starter) {
    this.starter = starter;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((active == null) ? 0 : active.hashCode());
    result = prime * result + ((app == null) ? 0 : app.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((repoType == null) ? 0 : repoType.hashCode());
    result = prime * result + ((starter == null) ? 0 : starter.hashCode());
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
    if (active == null) {
      if (other.active != null)
        return false;
    } else if (!active.equals(other.active))
      return false;
    if (app == null) {
      if (other.app != null)
        return false;
    } else if (!app.equals(other.app))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (repoType == null) {
      if (other.repoType != null)
        return false;
    } else if (!repoType.equals(other.repoType))
      return false;
    if (starter == null) {
      if (other.starter != null)
        return false;
    } else if (!starter.equals(other.starter))
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
    return "TstSte [tsId=" + tsId + ", name=" + name + ", app=" + app + ", description=" + description + ", active=" + active + ", starter=" + starter + 
        ", repositoryType.id=" + repoType.getId() + ", repositoryType.dtype=" + repoType.getDtype() +
        "]";
  }

}
