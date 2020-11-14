package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.TicketScheduleSectionRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;
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
    public List<TicketScheduleSectionEntity> findByScheduleSectionIdAndRideDate(int id, LocalDate rideDate) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("ticket-schedule-section-graph");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketScheduleSectionEntity> criteriaQuery = criteriaBuilder
                .createQuery(TicketScheduleSectionEntity.class);
        Root<TicketScheduleSectionEntity> root = criteriaQuery.from(TicketScheduleSectionEntity.class);

        Join<TicketScheduleSectionEntity, ScheduleSectionEntity> scheduleSectionEntityJoin = root
                .join(TicketScheduleSectionEntity_.scheduleSectionEntity);
        Join<TicketScheduleSectionEntity, TicketEntity> ticketEntityJoin = root
                .join(TicketScheduleSectionEntity_.ticketEntity);

        Predicate scheduleSectionIdEquality = criteriaBuilder
                .equal(scheduleSectionEntityJoin.get(ScheduleSectionEntity_.id), id);
        Predicate departureDateEquality = criteriaBuilder
                .equal(ticketEntityJoin.get(TicketEntity_.rideDate), rideDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(scheduleSectionIdEquality, departureDateEquality));
        TypedQuery<TicketScheduleSectionEntity> selectByScheduleSectionIdAndDepartureDate = entityManager
                .createQuery(criteriaQuery);
        selectByScheduleSectionIdAndDepartureDate.setHint("javax.persistence.loadgraph", entityGraph);

        return selectByScheduleSectionIdAndDepartureDate.getResultList();
    }

    @Override
    public void add(TicketScheduleSectionEntity ticketScheduleSectionEntity) {
        entityManager.persist(ticketScheduleSectionEntity);
    }

    @Override
    public void add(Collection<TicketScheduleSectionEntity> ticketScheduleSectionEntityCollection) {
        ticketScheduleSectionEntityCollection.forEach(this::add);
    }

}
