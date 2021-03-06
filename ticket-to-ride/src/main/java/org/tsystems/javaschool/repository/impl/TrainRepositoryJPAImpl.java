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
    public void add(TrainEntity trainEntity) {
        entityManager.persist(trainEntity);
    }

    @Override
    public void update(TrainEntity trainEntity) {
        entityManager.merge(trainEntity);
    }

    private void updateCancelledAttribute(TrainEntity trainEntity, boolean isCancelled) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<TrainEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(TrainEntity.class);
        Root<TrainEntity> root = criteriaUpdate.from(TrainEntity.class);

        criteriaUpdate
                .set(root.get(TrainEntity_.cancelled), isCancelled)
                .where(criteriaBuilder.equal(root.get(TrainEntity_.id), trainEntity.getId()));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void cancel(TrainEntity trainEntity) {
        updateCancelledAttribute(trainEntity, true);
    }

    @Override
    public void restart(TrainEntity trainEntity) {
        updateCancelledAttribute(trainEntity, false);
    }

}
