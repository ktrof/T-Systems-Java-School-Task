package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.CalendarEntity_;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.CalendarRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

/**
 * The type Calendar repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class CalendarRepositoryJPAImpl implements CalendarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CalendarEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CalendarEntity> criteriaQuery = criteriaBuilder.createQuery(CalendarEntity.class);
        Root<CalendarEntity> root = criteriaQuery.from(CalendarEntity.class);

        criteriaQuery
                .select(root);
        TypedQuery<CalendarEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<CalendarEntity> findAllByTrain(TrainEntity trainEntity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CalendarEntity> criteriaQuery = criteriaBuilder.createQuery(CalendarEntity.class);
        Root<CalendarEntity> root = criteriaQuery.from(CalendarEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(CalendarEntity_.trainEntity), trainEntity));
        TypedQuery<CalendarEntity> selectAllByTrainEntity = entityManager.createQuery(criteriaQuery);

        return selectAllByTrainEntity.getResultList();
    }

    @Override
    public CalendarEntity findById(int id) {
        return entityManager.find(CalendarEntity.class, id);
    }

    @Override
    public CalendarEntity add(CalendarEntity calendarEntity) {
        entityManager.persist(calendarEntity);
        return calendarEntity;
    }

    @Override
    public Iterable<CalendarEntity> add(Collection<CalendarEntity> calendarEntityCollection) {
        for (CalendarEntity calendarEntity : calendarEntityCollection) {
            entityManager.persist(calendarEntity);
        }
        return calendarEntityCollection;
    }

    private <O> void updateCalendarEntityAttribute(CalendarEntity calendarEntity, String attributeName,
                                                   O attributeValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<CalendarEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(CalendarEntity.class);
        Root<CalendarEntity> root = criteriaUpdate.from(CalendarEntity.class);

        Predicate trainEquality = criteriaBuilder
                .equal(root.get(CalendarEntity_.trainEntity), calendarEntity.getTrainEntity());
        Predicate rideDateEquality = criteriaBuilder
                .equal(root.get(CalendarEntity_.rideDate), calendarEntity.getRideDate());

        criteriaUpdate
                .set(root.get(attributeName), attributeValue)
                .where(criteriaBuilder.and(trainEquality, rideDateEquality));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void cancelRide(CalendarEntity calendarEntity) {
        updateCalendarEntityAttribute(calendarEntity, CalendarEntity_.CANCELLED, true);
    }

    @Override
    public void cancelAllRides(Collection<CalendarEntity> calendarEntityCollection) {
        calendarEntityCollection.forEach(this::cancelRide);
    }

    @Override
    public void restartRide(CalendarEntity calendarEntity) {
        updateCalendarEntityAttribute(calendarEntity, CalendarEntity_.CANCELLED, false);
    }

    @Override
    public void restartAllRides(Collection<CalendarEntity> calendarEntityCollection) {
        calendarEntityCollection.forEach(this::restartRide);
    }

    @Override
    public void delayRide(CalendarEntity calendarEntity, int minutes) {
        updateCalendarEntityAttribute(calendarEntity, CalendarEntity_.MINUTES_DELAYED, minutes);
    }

}
