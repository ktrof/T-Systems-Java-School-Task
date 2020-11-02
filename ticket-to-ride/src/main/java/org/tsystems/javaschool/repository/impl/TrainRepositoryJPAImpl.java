package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.TrainRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

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
        CriteriaQuery<TrainEntity> criteriaQuery = criteriaBuilder.createQuery(TrainEntity.class);
        Root<TrainEntity> trainEntityRoot = criteriaQuery.from(TrainEntity.class);

        criteriaQuery
                .select(trainEntityRoot);
        TypedQuery<TrainEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public TrainEntity findById(String id) {
        return entityManager.find(TrainEntity.class, id);
    }

    @Override
    public TrainEntity add(TrainEntity trainEntity) {
        entityManager.persist(trainEntity);
        return trainEntity;
    }

    @Override
    public TrainEntity update(TrainEntity trainEntity) {
        return entityManager.merge(trainEntity);
    }

}
