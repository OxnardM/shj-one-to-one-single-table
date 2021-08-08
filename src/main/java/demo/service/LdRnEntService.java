package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.model.LdRnEnt;
import demo.model.LdRnEntDao;
import demo.model.TstSte;

@Service
public class LdRnEntService {
  @Autowired private LdRnEntDao ldRnEntDao;

  // pass by reference 
  public void persist(LdRnEnt ldRnEnt) {
    ldRnEntDao.persist(ldRnEnt);
  }
  
  public List<LdRnEnt> findByTypeDomainProject(String type, String domain, String project ) {
    return ldRnEntDao.findByTypeDomainProject(type, domain, project);
  }

  
}
