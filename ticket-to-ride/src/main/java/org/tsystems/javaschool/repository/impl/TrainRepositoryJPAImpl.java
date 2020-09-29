package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.model.entity.TrainEntity_;
import org.tsystems.javaschool.repository.TrainRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Train repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class TrainRepositoryJPAImpl implements TrainRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrainEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainEntity> selectAllTrains = criteriaBuilder.createQuery(TrainEntity.class);
        Root<TrainEntity> trainEntityRoot = selectAllTrains.from(TrainEntity.class);
        selectAllTrains.select(trainEntityRoot);
        TypedQuery<TrainEntity> selectAllQuery = entityManager.createQuery(selectAllTrains);

        return selectAllQuery.getResultList();
    }

    @Override
    public TrainEntity findById(String id) throws SBBException {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("train-entity-graph");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("javax.persistence.fetchgraph", entityGraph);
        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id, propertyMap);

        if (trainEntity != null) {
            return trainEntity;
        } else throw new SBBException("No train found by given id");
    }

    @Override
    public TrainEntity add(TrainEntity trainEntity) throws SBBException {
        if (trainEntity != null) {
            entityManager.persist(trainEntity);
            return trainEntity;
        } else throw new SBBException("Train entity can not be null");
    }

    @Override
    public TrainEntity update(TrainEntity trainEntity) throws SBBException {
        if (trainEntity != null) {
            return entityManager.merge(trainEntity);
        } else throw new SBBException("Train entity can not be null");
    }

    @Override
    public void remove(TrainEntity trainEntity) throws SBBException {
        if (trainEntity != null) {
            entityManager.remove(trainEntity);
        } else throw new SBBException("Train entity and number of seats can not be null");
    }

}
