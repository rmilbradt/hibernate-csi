package br.ufsm.csi.seguranca.dao;

import br.ufsm.csi.seguranca.model.Usuario;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by cpol on 31/05/2017.
 */
@Repository
public class HibernateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void criaObjeto(Object o) {
        sessionFactory.getCurrentSession().save(o);
    }

    public void removeObjeto(Object o) {
        sessionFactory.getCurrentSession().remove(o);
    }



    public Collection<Object> listaObjetos(Class classe, Map<String, String> likeMap) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(classe);
        detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Map.Entry<String, String> ent : likeMap.entrySet()) {
            detachedCriteria.add(Restrictions.ilike(ent.getKey(), "%" + ent.getValue() + "%"));
        }
        return detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }

    public Object carregaObjeto(Class classe, Serializable id) {
        return sessionFactory.getCurrentSession().get(classe, id);
    }

}
