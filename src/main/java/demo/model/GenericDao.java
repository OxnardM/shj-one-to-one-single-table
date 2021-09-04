package demo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class GenericDao<T> {

  @PersistenceContext protected EntityManager em;
  private Class<T> persistentClass;

  public List<T> findAllMultipleClause(String[] predicates, Object[] values, Class<T>[] types, String[] orders) {
    int i = 0;
    Predicate[] preds = new Predicate[predicates.length];
    Order[] odrs = new Order[orders.length];
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistentClass);
    Root<T> root = criteriaQuery.from(persistentClass);
    criteriaQuery.select(root);
    for (String whereClauseVal : predicates) {
      ParameterExpression<T> pe = criteriaBuilder.parameter(types[i], whereClauseVal);
      Predicate pred = criteriaBuilder.equal(root.get(whereClauseVal), pe);
      preds[i] = pred;
      i++;
    }
    i = 0;
    for (String order : orders) {
      Order orderLocal = criteriaBuilder.asc(root.get(order));
      odrs[i] = orderLocal;
    }
    criteriaQuery.where(preds).orderBy(odrs);
    TypedQuery<T> typedQuery = em.createQuery(criteriaQuery.select(root));
    for (i = 0; i < predicates.length; i++) {
      typedQuery.setParameter(predicates[i], values[i]);
    }
    return typedQuery.getResultList();
  }

}
