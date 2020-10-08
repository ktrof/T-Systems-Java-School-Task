package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.CalendarEntity;
import org.tsystems.javaschool.model.entity.CalendarEntity_;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.CalendarRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    @Override
    public void remove(CalendarEntity calendarEntity) {
        entityManager.remove(calendarEntity);
    }
}
