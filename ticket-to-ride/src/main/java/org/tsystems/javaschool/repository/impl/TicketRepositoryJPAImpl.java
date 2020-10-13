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
import java.time.LocalDate;
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
    public List<TicketEntity> findByPassenger(PassengerEntity passengerEntity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketEntity> criteriaQuery = criteriaBuilder.createQuery(TicketEntity.class);
        Root<TicketEntity> root = criteriaQuery.from(TicketEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(TicketEntity_.passengerEntity), passengerEntity));
        TypedQuery<TicketEntity> selectAllByPassenger = entityManager.createQuery(criteriaQuery);

        return selectAllByPassenger.getResultList();
    }

    @Override
    public List<TicketEntity> findByPassengerNameAndBirthDate(String firstName, String secondName, LocalDate birthDate) throws SBBException {
        TypedQuery<TicketEntity> selectAllByPassengerNameAndMobile = entityManager
                .createQuery("SELECT t FROM TicketEntity t " +
                        "WHERE t.passengerEntity.firstName = :firstName " +
                        "AND t.passengerEntity.secondName = :secondName " +
                        "AND t.passengerEntity.birthDate = :birthDate", TicketEntity.class)
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .setParameter("birthDate", birthDate);

        return selectAllByPassengerNameAndMobile.getResultList();
    }

    @Override
    public TicketEntity findById(int id) {
        return entityManager.find(TicketEntity.class, id);
    }

    @Override
    public TicketEntity add(TicketEntity ticketEntity) {
        entityManager.persist(ticketEntity);
        return ticketEntity;
    }

    @Override
    public void remove(TicketEntity ticketEntity) {
        entityManager.remove(ticketEntity);
    }
}
