package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.TicketRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

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
    public List<TicketEntity> findByUser(UserEntity userEntity) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("ticket-graph");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TicketEntity> criteriaQuery = criteriaBuilder.createQuery(TicketEntity.class);
        Root<TicketEntity> root = criteriaQuery.from(TicketEntity.class);

        Join<TicketEntity, PassengerEntity> passengerEntityJoin = root.join(TicketEntity_.passengerEntity);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(passengerEntityJoin.get(PassengerEntity_.userEntity), userEntity));
        TypedQuery<TicketEntity> selectAllByUser = entityManager.createQuery(criteriaQuery);
        selectAllByUser.setHint("javax.persistence.loadgraph", entityGraph);

        return selectAllByUser.getResultList();
    }

    @Override
    public List<TicketEntity> findByPassengerNameAndBirthDate(String firstName, String secondName, LocalDate birthDate) {
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
    public void add(TicketEntity ticketEntity) {
        entityManager.persist(ticketEntity);
    }

}
