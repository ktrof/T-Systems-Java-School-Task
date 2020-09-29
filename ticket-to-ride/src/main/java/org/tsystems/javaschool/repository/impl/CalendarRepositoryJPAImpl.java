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
    public List<CalendarEntity> findAllByTrain(TrainEntity trainEntity) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CalendarEntity> criteriaQuery = criteriaBuilder.createQuery(CalendarEntity.class);
        Root<CalendarEntity> root = criteriaQuery.from(CalendarEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(CalendarEntity_.trainEntity), trainEntity));
        TypedQuery<CalendarEntity> selectAllByTrainEntity = entityManager.createQuery(criteriaQuery);
        List<CalendarEntity> calendarEntityList = selectAllByTrainEntity.getResultList();
        if (calendarEntityList.size() != 0) {
            return calendarEntityList;
        } else throw new SBBException("No dates found by given train entity");
    }

    @Override
    public CalendarEntity findById(int id) throws SBBException {
        CalendarEntity calendarEntity = entityManager.find(CalendarEntity.class, id);
        if (calendarEntity != null) {
            return calendarEntity;
        } else throw new SBBException("No date found by given id");
    }

    @Override
    public CalendarEntity add(CalendarEntity calendarEntity) throws SBBException {
        if (calendarEntity != null) {
            entityManager.persist(calendarEntity);
            return calendarEntity;
        } else throw new SBBException("Calendar entity can not be null");
    }

    @Override
    public void remove(CalendarEntity calendarEntity) throws SBBException {
        if (calendarEntity != null) {
            entityManager.remove(calendarEntity);
        } else throw new SBBException("Calendar entity can not be null");
    }
}
