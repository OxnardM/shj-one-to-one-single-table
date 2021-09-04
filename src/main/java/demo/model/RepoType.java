package demo.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/*
 * maybe use @DiscriminatorColumn here and 
 * @DiscriminatorValue on child tables
 */

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "dtype", include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = true)
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "dtype")
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
//  @Type(value = LdRnEnt.class, name = "ldRnEnt"), // lets some random string be used
  @Type(value = LdRnEnt.class), // class name?
  @Type(value = OtherRepo.class)
})
//@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING, length=50) // use as cannot get access for toString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class RepoType {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false) private Integer id;
  
//  @Column(insertable = false, updatable = false, nullable = false) private String type; // using discriminator odd settings or error 
//  @JsonIgnore
  @Column(insertable = false, updatable = false, nullable = false) private String dtype; // using discriminator odd settings or error 

  @OneToOne
  @JsonBackReference // prevents endless loop in Jackson
  @JoinColumn(name = "tst_ste_id", nullable = false, updatable = false, unique = true)
  private TstSte tstSte;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TstSte getTstSte() {
    return tstSte;
  }

  public void setTstSte(TstSte tstSte) {
    this.tstSte = tstSte;
  }

//  public String getType() {
//    return type;
//  }

//  maybe not needed as kind of a constant value which should not change
//  public void setType(String type) {
//    this.type = type;
//  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((tstSte == null) ? 0 : tstSte.hashCode());
//    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    RepoType other = (RepoType) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (tstSte == null) {
      if (other.tstSte != null)
        return false;
    } else if (!tstSte.equals(other.tstSte))
      return false;
//    if (type == null) {
//      if (other.type != null)
//        return false;
//    } else if (!type.equals(other.type))
//      return false;
    return true;
  }

  @Override
  public String toString() {
//    return "RepositoryType [id=" + id + ", tstSte=" + tstSte + ", type=" + type + "]";
    return "RepositoryType [id=" + id + "dtype=" + dtype + ", tstSte=" + tstSte + "]";
  }

  public String getDtype() {
    return dtype;
  }

  public void setDtype(String dtype) {
    this.dtype = dtype;
  }
  
}
