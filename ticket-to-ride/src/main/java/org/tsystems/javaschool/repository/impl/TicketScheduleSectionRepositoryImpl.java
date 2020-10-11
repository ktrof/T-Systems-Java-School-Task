package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.TicketScheduleSectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Ticket schedule section repository.
 *
 * @author Trofim Kremen
 */
@Repository
public class TicketScheduleSectionRepositoryImpl implements TicketScheduleSectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TicketScheduleSectionEntity> findByScheduleSectionIdAndDepartureDate(int id, LocalDate departureDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketScheduleSectionEntity> criteriaQuery = criteriaBuilder
                .createQuery(TicketScheduleSectionEntity.class);
        Root<TicketScheduleSectionEntity> root = criteriaQuery.from(TicketScheduleSectionEntity.class);
        Join<TicketScheduleSectionEntity, ScheduleSectionEntity> scheduleSectionEntityJoin = root
                .join(TicketScheduleSectionEntity_.scheduleSectionEntity);
        Join<ScheduleSectionEntity, TrainEntity> trainEntityJoin = scheduleSectionEntityJoin
                .join(ScheduleSectionEntity_.trainEntity);
        Join<TrainEntity, CalendarEntity> calendarEntityJoin = trainEntityJoin
                .join(TrainEntity_.calendarEntityList);
        Predicate scheduleSectionIdEquality = criteriaBuilder
                .equal(scheduleSectionEntityJoin.get(ScheduleSectionEntity_.id), id);
        Predicate departureDateEquality = criteriaBuilder
                .equal(calendarEntityJoin.get(CalendarEntity_.rideDate), departureDate);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(scheduleSectionIdEquality, departureDateEquality));

        TypedQuery<TicketScheduleSectionEntity> selectByScheduleSectionIdAndDepartureDate = entityManager
                .createQuery(criteriaQuery);
        return selectByScheduleSectionIdAndDepartureDate.getResultList();
    }

    @Override
    public TicketScheduleSectionEntity add(TicketScheduleSectionEntity ticketScheduleSectionEntity) {
        entityManager.persist(ticketScheduleSectionEntity);
        return ticketScheduleSectionEntity;
    }

    @Override
    public List<TicketScheduleSectionEntity> addAll(List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList) {
        for (TicketScheduleSectionEntity ticketScheduleSectionEntity : ticketScheduleSectionEntityList) {
            entityManager.persist(ticketScheduleSectionEntity);
        }
        return ticketScheduleSectionEntityList;
    }
}
