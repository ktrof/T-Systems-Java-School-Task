package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.RideRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * The type Calendar repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class RideRepositoryJPAImpl implements RideRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RideEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RideEntity> criteriaQuery = criteriaBuilder.createQuery(RideEntity.class);
        Root<RideEntity> root = criteriaQuery.from(RideEntity.class);

        criteriaQuery
                .select(root);
        TypedQuery<RideEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<RideEntity> findAllByTrain(TrainEntity trainEntity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RideEntity> criteriaQuery = criteriaBuilder.createQuery(RideEntity.class);
        Root<RideEntity> root = criteriaQuery.from(RideEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(RideEntity_.trainEntity), trainEntity));
        TypedQuery<RideEntity> selectAllByTrainEntity = entityManager.createQuery(criteriaQuery);

        return selectAllByTrainEntity.getResultList();
    }

    @Override
    public RideEntity findByTrainAndDate(TrainEntity trainEntity, LocalDate rideDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RideEntity> criteriaQuery = criteriaBuilder.createQuery(RideEntity.class);
        Root<RideEntity> root = criteriaQuery.from(RideEntity.class);

        Predicate trainEquality = criteriaBuilder.equal(root.get(RideEntity_.trainEntity), trainEntity);
        Predicate rideDateEquality = criteriaBuilder.equal(root.get(RideEntity_.rideDate), rideDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, rideDateEquality));
        TypedQuery<RideEntity> selectByTrainAndDate = entityManager.createQuery(criteriaQuery);

        return selectByTrainAndDate.getResultStream().findFirst().orElse(null);
    }

    @Override
    public RideEntity add(RideEntity rideEntity) {
        entityManager.persist(rideEntity);
        return rideEntity;
    }

    @Override
    public Iterable<RideEntity> add(Collection<RideEntity> rideEntityCollection) {
        rideEntityCollection.forEach(this::add);
        return rideEntityCollection;
    }

    private void updateTrainCancellation(RideEntity rideEntity, boolean attributeValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<RideEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(RideEntity.class);
        Root<RideEntity> root = criteriaUpdate.from(RideEntity.class);

        Predicate trainEquality = criteriaBuilder
                .equal(root.get(RideEntity_.trainEntity), rideEntity.getTrainEntity());
        Predicate rideDateEquality = criteriaBuilder
                .equal(root.get(RideEntity_.rideDate), rideEntity.getRideDate());

        criteriaUpdate
                .set(root.get(RideEntity_.cancelled), attributeValue)
                .where(criteriaBuilder.and(trainEquality, rideDateEquality));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void cancelRide(RideEntity rideEntity) {
        updateTrainCancellation(rideEntity, true);
    }

    @Override
    public void cancelAllRides(Collection<RideEntity> rideEntityCollection) {
        rideEntityCollection.forEach(this::cancelRide);
    }

    @Override
    public void restartRide(RideEntity rideEntity) {
        updateTrainCancellation(rideEntity, false);
    }

    @Override
    public void restartAllRides(Collection<RideEntity> rideEntityCollection) {
        rideEntityCollection.forEach(this::restartRide);
    }

}
