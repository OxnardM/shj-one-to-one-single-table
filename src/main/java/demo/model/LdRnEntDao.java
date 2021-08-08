package demo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LdRnEntDao {

  
  @PersistenceContext private EntityManager em;
  
  // pass by reference so do not need to explicitly return it
  public void persist(LdRnEnt ldRnEnt) {
    em.persist(ldRnEnt);
  }
  
  public List<LdRnEnt> findByTypeDomainProject(String type, String domain, String project ) {
    String sql = "select * from RepositoryType t where t.type :type";
    return em.createQuery(sql, LdRnEnt.class)
      .setParameter("type", type)
      .getResultList();
  }
}
