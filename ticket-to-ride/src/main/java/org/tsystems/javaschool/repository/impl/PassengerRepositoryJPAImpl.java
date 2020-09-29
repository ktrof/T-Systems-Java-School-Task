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
        List<PassengerEntity> passengerEntityList = selectAllByUser.getResultList();

        if (passengerEntityList.size() != 0) {
            return passengerEntityList;
        } else throw new SBBException("No passengers found by given user entity");
    }

    @Override
    public List<PassengerEntity> findByName(String firstName, String secondName) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PassengerEntity> criteriaQuery = criteriaBuilder.createQuery(PassengerEntity.class);
        Root<PassengerEntity> root = criteriaQuery.from(PassengerEntity.class);
        Predicate firstNamePredicate = criteriaBuilder.equal(root.get(PassengerEntity_.firstName), firstName);
        Predicate secondNamePredicate = criteriaBuilder.equal(root.get(PassengerEntity_.secondName), secondName);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(firstNamePredicate, secondNamePredicate));
        TypedQuery<PassengerEntity> selectByName = entityManager.createQuery(criteriaQuery);
        List<PassengerEntity> passengerEntityList = selectByName.getResultList();

        if (passengerEntityList.size() != 0) {
            return passengerEntityList;
        } else throw new SBBException("No passenger found by given name");
    }

    @Override
    public PassengerEntity findById(int id) throws SBBException {
        PassengerEntity passengerEntity = entityManager.find(PassengerEntity.class, id);
        if (passengerEntity != null) {
            return passengerEntity;
        } else throw new SBBException("No passenger found by given id");
    }

    @Override
    public PassengerEntity add(PassengerEntity passengerEntity) {
        if (passengerEntity != null) {
            entityManager.persist(passengerEntity);
            return passengerEntity;
        } else throw new SBBException("Passenger entity can not be null");
    }

    @Override
    public PassengerEntity updateName(String firstName, String secondName, PassengerEntity passengerEntity) throws SBBException {
        if (firstName != null || secondName != null || passengerEntity != null) {
            passengerEntity.setFirstName(firstName);
            passengerEntity.setSecondName(secondName);
            return entityManager.merge(passengerEntity);
        } else throw new SBBException("Passenger entity and name can not be null");
    }

    @Override
    public PassengerEntity updateMobileNumber(String mobile, PassengerEntity passengerEntity) throws SBBException {
        if (mobile != null || passengerEntity != null) {
            passengerEntity.setMobileNumber(mobile);
            return entityManager.merge(passengerEntity);
        } else throw new SBBException("Passenger entity and mobile number can not be null");
    }

    @Override
    public PassengerEntity updateEmail(String email, PassengerEntity passengerEntity) throws SBBException {
        if (email != null || passengerEntity != null) {
            passengerEntity.setEmail(email);
            return entityManager.merge(passengerEntity);
        } else throw new SBBException("Passenger entity and email can not be null");
    }

    @Override
    public void remove(PassengerEntity passengerEntity) throws SBBException {
        if (passengerEntity != null) {
            entityManager.remove(passengerEntity);
        } else throw new SBBException("Passenger entity can not be null");
    }
}
