package com.cat.prf.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

abstract public class GenericDAO<EntityType, IdentifierType> {
    @PersistenceContext
    private EntityManager em;
    private Class<EntityType> entityClass;

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

    public Class<EntityType> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
