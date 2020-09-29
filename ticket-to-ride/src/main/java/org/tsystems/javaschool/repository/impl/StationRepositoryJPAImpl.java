package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.StationEntity_;
import org.tsystems.javaschool.repository.StationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The type Station repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class StationRepositoryJPAImpl implements StationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StationEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StationEntity> criteriaQuery = criteriaBuilder.createQuery(StationEntity.class);
        Root<StationEntity> root = criteriaQuery.from(StationEntity.class);
        criteriaQuery
                .select(root);
        TypedQuery<StationEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public StationEntity findById(int id) throws SBBException {
        StationEntity stationEntity = entityManager.find(StationEntity.class, id);
        if (stationEntity != null) {
            return stationEntity;
        } else throw new SBBException("No station found by given id");
    }

    @Override
    public StationEntity findByName(String name) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StationEntity> criteriaQuery = criteriaBuilder.createQuery(StationEntity.class);
        Root<StationEntity> root = criteriaQuery.from(StationEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(StationEntity_.name), name));
        TypedQuery<StationEntity> selectByName = entityManager.createQuery(criteriaQuery);

        StationEntity stationEntity = selectByName.getSingleResult();
        if (stationEntity != null) {
            return stationEntity;
        } else throw new SBBException("No station found by given name");
    }

    @Override
    public StationEntity add(StationEntity stationEntity) throws SBBException {
        if (stationEntity != null) {
            entityManager.persist(stationEntity);
            return stationEntity;
        } else throw new SBBException("Station entity can not be null");
    }

    @Override
    public StationEntity update(StationEntity stationEntity) throws SBBException {
        if (stationEntity != null) {
            return entityManager.merge(stationEntity);
        } else throw new SBBException("Station entity can not be null");
    }

    @Override
    public void remove(StationEntity stationEntity) throws SBBException {
        if (stationEntity != null) {
            entityManager.remove(stationEntity);
        } else throw new SBBException("Station entity can not be null");
    }
}
