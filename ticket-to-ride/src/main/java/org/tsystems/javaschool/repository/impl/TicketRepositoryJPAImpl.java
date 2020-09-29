package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.TicketEntity;
import org.tsystems.javaschool.model.entity.TicketEntity_;
import org.tsystems.javaschool.repository.TicketRepository;

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
 * The type Ticket repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class TicketRepositoryJPAImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TicketEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketEntity> criteriaQuery = criteriaBuilder.createQuery(TicketEntity.class);
        Root<TicketEntity> root = criteriaQuery.from(TicketEntity.class);
        criteriaQuery
                .select(root);
        TypedQuery<TicketEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<TicketEntity> findByPassenger(PassengerEntity passengerEntity) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketEntity> criteriaQuery = criteriaBuilder.createQuery(TicketEntity.class);
        Root<TicketEntity> root = criteriaQuery.from(TicketEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(TicketEntity_.passengerEntity), passengerEntity));
        TypedQuery<TicketEntity> selectAllByPassenger = entityManager.createQuery(criteriaQuery);
        List<TicketEntity> ticketEntityList = selectAllByPassenger.getResultList();

        if(ticketEntityList.size() != 0) {
            return ticketEntityList;
        } else throw new SBBException("No ticket found by given passenger");
    }

    @Override
    public List<TicketEntity> findByPassengerNameAndMobile(String firstName, String secondName, String mobileNumber) throws SBBException {
        TypedQuery<TicketEntity> selectAllByPassengerNameAndMobile = entityManager
                .createQuery("SELECT t FROM TicketEntity t " +
                        "WHERE t.passengerEntity.firstName = :firstName " +
                        "AND t.passengerEntity.secondName = :secondName " +
                        "AND t.passengerEntity.mobileNumber = :mobileNumber", TicketEntity.class)
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .setParameter("mobileNumber", mobileNumber);
        List<TicketEntity> ticketEntityList = selectAllByPassengerNameAndMobile.getResultList();

        if (ticketEntityList.size() != 0) {
            return ticketEntityList;
        } else throw new SBBException("No ticket found by given name and mobile number");
    }

    @Override
    public TicketEntity findById(int id) throws SBBException {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("ticket-entity-graph");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("javax.persistence.fetchgraph", entityGraph);
        TicketEntity ticketEntity = entityManager.find(TicketEntity.class, id, propertyMap);

        if (ticketEntity != null) {
            return ticketEntity;
        } else throw new SBBException("No ticket found by given id");
    }

    @Override
    public TicketEntity add(TicketEntity ticketEntity) throws SBBException {
        if (ticketEntity != null) {
            entityManager.persist(ticketEntity);
            return ticketEntity;
        } else throw new SBBException("Ticket entity can not be null");
    }

    @Override
    public void remove(TicketEntity ticketEntity) throws SBBException {
        if (ticketEntity != null) {
            entityManager.remove(ticketEntity);
        } else throw new SBBException("Ticket entity can not be null");
    }
}
