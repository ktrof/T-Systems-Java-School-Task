package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.RepositoryException;
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
    public TrainEntity findById(int id) throws RepositoryException {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("train-entity-graph");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("javax.persistence.fetchgraph", entityGraph);
        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id, propertyMap);

        if (trainEntity != null) {
            return trainEntity;
        } else throw new RepositoryException("No train found by given id");
    }

    @Override
    public TrainEntity findBySymbolCode(String symbolCode) throws RepositoryException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainEntity> criteriaQuery = criteriaBuilder.createQuery(TrainEntity.class);
        Root<TrainEntity> root = criteriaQuery.from(TrainEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(TrainEntity_.symbolCode), symbolCode));
        TypedQuery<TrainEntity> selectBySymbolCode = entityManager.createQuery(criteriaQuery);

        TrainEntity trainEntity = selectBySymbolCode.getSingleResult();
        if (trainEntity != null) {
            return trainEntity;
        } else throw new RepositoryException("No train found by given symbol code");
    }

    @Override
    public TrainEntity add(TrainEntity trainEntity) throws RepositoryException {
        if (trainEntity != null) {
            entityManager.persist(trainEntity);
            return trainEntity;
        } else throw new RepositoryException("Train entity can not be null");
    }

    @Override
    public TrainEntity updateAvgSpeed(int speed, TrainEntity trainEntity) throws RepositoryException{
        if (speed != 0 || trainEntity != null) {
            trainEntity.setAvgSpeed(speed);
            return entityManager.merge(trainEntity);
        } else throw new RepositoryException("Train entity and speed can not be null");
    }

    @Override
    public TrainEntity updateNumberOfSeats(int numberOfSeats, TrainEntity trainEntity) throws RepositoryException{
        if (numberOfSeats != 0 || trainEntity != null) {
            trainEntity.setNumberOfSeats(numberOfSeats);
            return entityManager.merge(trainEntity);
        } else throw new RepositoryException("Train entity can not be null");
    }

    @Override
    public void remove(TrainEntity trainEntity) throws RepositoryException {
        if (trainEntity != null) {
            entityManager.remove(trainEntity);
        } else throw new RepositoryException("Train entity and number of seats can not be null");
    }

}
