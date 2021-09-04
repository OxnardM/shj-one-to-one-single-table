package demo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional // different
public class TstSteDao {
  private final static Logger LOGGER = LoggerFactory.getLogger(TstSteDao.class);
  @PersistenceContext private EntityManager em;
  
  public TstSte find(TstSte tstSte) {
    LOGGER.info(String.format("[find] [tstSte=%s]", tstSte.toString()));
    return em.find(TstSte.class, tstSte.getTsId());
  }
  
  /**
   * at most can have one test suite selected
   * should support a PK and/or Natural key 
   * @param tstSte
   * @return
   */
  public TstSte tstSteSingleton(TstSte tstSte) {
    StringBuffer sb = new StringBuffer("select t from TstSte t left join fetch t.repoType where 1 = 1 "); 
    if (tstSte.getTsId() != null) {
      sb.append("and t.tsId = :tsId ");
      if (tstSte.getName() != null) { // is possible but unlikely a PK and some other value could be supplied
        sb.append("and t.name = :name ");
        return em.createQuery(sb.toString(), TstSte.class)
            .setParameter("tsId", tstSte.getTsId())
            .setParameter("name", tstSte.getName())
            .getSingleResult();
      } else {
        return em.createQuery(sb.toString(), TstSte.class)
            .setParameter("tsId", tstSte.getTsId())
            .getSingleResult();        
      }
    }
    throw new IllegalArgumentException("Invalid Argument tstSte => " + tstSte.toString());
  }
  
  
  // pass by reference so do not need to explicitly return it
  // cannot get dtype to return
  public void persist(TstSte tstSte) {
    LOGGER.info(String.format("[persist] [tstSte=%s]", tstSte.toString()));
//    final String dtype = tstSte.getRepositoryType().getDtype();
    em.persist(tstSte);
//    em.flush();
//    tstSte.getRepositoryType().setDtype(dtype);
//    tstSte = em.find(TstSte.class, tstSte.getTsId());
    System.out.println(tstSte.toString());
  }
  
  public List<TstSte> tstStes(TstSte tstSte) {
    String sql = "select t from TstSte t left join fetch t.repoType";
    return em.createQuery(sql, TstSte.class)
        .getResultList();
  }
  

}
