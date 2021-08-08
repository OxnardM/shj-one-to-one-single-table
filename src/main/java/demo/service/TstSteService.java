package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.model.TstSte;
import demo.model.TstSteDao;

@Service
public class TstSteService {
  @Autowired private TstSteDao tstSteDao;
  
  // pass by reference
  public void persist(TstSte tstSte) {
    if (tstSte.getRepositoryType() != null && tstSte.getRepositoryType().getTstSte() == null ) tstSte.getRepositoryType().setTstSte(tstSte); // have to have the parent in the child object or persist fails. Oddly the annotations JsonManagedReference and JsonBackReference seem to populate the value 
    tstSteDao.persist(tstSte);
  }
  
  
  public List<TstSte> tstStes(TstSte tstSte) {
    List<TstSte> tstStes = tstSteDao.tstStes(tstSte);
    return tstStes;
  }
  
  public TstSte tstSteSingleton(TstSte tstSte) {
    tstSte = tstSteDao.tstSteSingleton(tstSte);
    return tstSte;
  }
  
  public TstSte find(TstSte tstSte) {
    tstSte = tstSteDao.find(tstSte);
    return tstSte;
  }
  

}
