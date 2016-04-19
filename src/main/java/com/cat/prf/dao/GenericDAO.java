package com.cat.prf.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

abstract public class GenericDAO<EntityType, IdentifierType> implements Serializable{

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private Class<EntityType> entityClass;

    public GenericDAO() {
    }

    public GenericDAO(Class<EntityType> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public void create(EntityType entity) {
        em.persist(entity);
    }

    public EntityType read(IdentifierType id) {
        return em.find(getEntityClass(), id);
    }

    @Transactional
    public void update(EntityType entity) {
        em.merge(entity);
    }

    @Transactional
    public void delete(IdentifierType id) {
        EntityType entity = em.find(getEntityClass(), id);
        em.remove(entity);
    }

    public boolean contains(EntityType entity) {
        return em.contains(entity);
    }

    public Class<EntityType> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
