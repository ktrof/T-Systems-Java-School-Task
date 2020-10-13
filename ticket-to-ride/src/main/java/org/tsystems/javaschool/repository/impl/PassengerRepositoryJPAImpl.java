package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.PassengerEntity_;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.PassengerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Passenger repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class PassengerRepositoryJPAImpl implements PassengerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PassengerEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PassengerEntity> criteriaQuery = criteriaBuilder.createQuery(PassengerEntity.class);
        Root<PassengerEntity> root = criteriaQuery.from(PassengerEntity.class);

        criteriaQuery
                .select(root);
        TypedQuery<PassengerEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<PassengerEntity> findAllByUser(UserEntity userEntity) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PassengerEntity> criteriaQuery = criteriaBuilder.createQuery(PassengerEntity.class);
        Root<PassengerEntity> root = criteriaQuery.from(PassengerEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(PassengerEntity_.userEntity), userEntity));
        TypedQuery<PassengerEntity> selectAllByUser = entityManager.createQuery(criteriaQuery);

        return selectAllByUser.getResultList();
    }

    @Override
    public List<PassengerEntity> findByName(String firstName, String secondName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PassengerEntity> criteriaQuery = criteriaBuilder.createQuery(PassengerEntity.class);
        Root<PassengerEntity> root = criteriaQuery.from(PassengerEntity.class);

        Predicate firstNamePredicate = criteriaBuilder.equal(root.get(PassengerEntity_.firstName), firstName);
        Predicate secondNamePredicate = criteriaBuilder.equal(root.get(PassengerEntity_.secondName), secondName);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(firstNamePredicate, secondNamePredicate));
        TypedQuery<PassengerEntity> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getResultList();
    }

    @Override
    public PassengerEntity findByNameAndBirthDate(String firstName, String secondName, LocalDate birthDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PassengerEntity> criteriaQuery = criteriaBuilder.createQuery(PassengerEntity.class);
        Root<PassengerEntity> root = criteriaQuery.from(PassengerEntity.class);

        Predicate firstNameEquality = criteriaBuilder.equal(root.get(PassengerEntity_.firstName), firstName);
        Predicate secondNameEquality = criteriaBuilder.equal(root.get(PassengerEntity_.secondName), secondName);
        Predicate birthDateEquality = criteriaBuilder.equal(root.get(PassengerEntity_.birthDate), birthDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(firstNameEquality, secondNameEquality, birthDateEquality));
        TypedQuery<PassengerEntity> selectByPassengerDetails = entityManager.createQuery(criteriaQuery);

        return selectByPassengerDetails.getSingleResult();
    }

    @Override
    public PassengerEntity findById(int id) {
        return entityManager.find(PassengerEntity.class, id);
    }

    @Override
    public PassengerEntity add(PassengerEntity passengerEntity) {
        entityManager.persist(passengerEntity);
        return passengerEntity;
    }

    @Override
    public PassengerEntity updateName(String firstName, String secondName, PassengerEntity passengerEntity) {
        passengerEntity.setFirstName(firstName);
        passengerEntity.setSecondName(secondName);
        return entityManager.merge(passengerEntity);
    }

    @Override
    public PassengerEntity updateMobileNumber(String mobile, PassengerEntity passengerEntity) {
        passengerEntity.setMobileNumber(mobile);
        return entityManager.merge(passengerEntity);
    }

    @Override
    public PassengerEntity updateEmail(String email, PassengerEntity passengerEntity) {
        passengerEntity.setEmail(email);
        return entityManager.merge(passengerEntity);
    }

    @Override
    public void remove(PassengerEntity passengerEntity) {
        entityManager.remove(passengerEntity);
    }
}
