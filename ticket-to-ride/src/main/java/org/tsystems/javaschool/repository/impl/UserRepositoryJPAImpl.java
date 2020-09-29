package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.model.entity.UserEntity_;
import org.tsystems.javaschool.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The type User repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class UserRepositoryJPAImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery
                .select(root);
        TypedQuery<UserEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public UserEntity findById(int id) throws SBBException {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        if (userEntity != null) {
            return userEntity;
        } else throw new SBBException("No user found by given id");
    }

    @Override
    public UserEntity findByLogin(String login) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(UserEntity_.login), login));
        TypedQuery<UserEntity> selectByLogin = entityManager.createQuery(criteriaQuery);

        UserEntity userEntity = selectByLogin.getSingleResult();
        if (userEntity != null) {
            return userEntity;
        } else throw new SBBException("No user found bu given login");
    }

    @Override
    public UserEntity add(UserEntity userEntity) throws SBBException {
        if (userEntity != null) {
            entityManager.persist(userEntity);
            return userEntity;
        } else throw new SBBException("User entity can not be null");
    }

    @Override
    public UserEntity updateLogin(String login, UserEntity userEntity) throws SBBException {
        if (login != null || userEntity != null) {
            userEntity.setLogin(login);
            return entityManager.merge(userEntity);
        } else throw new SBBException("User entity and login can not be null");
    }

    @Override
    public UserEntity updatePassword(String password, UserEntity userEntity) {
        if (password != null || userEntity != null) {
            userEntity.setPassword(password);
            return entityManager.merge(userEntity);
        } else throw new SBBException("User entity and password can not be null");
    }

    @Override
    public void remove(UserEntity userEntity) throws SBBException {
        if (userEntity != null) {
            entityManager.remove(userEntity);
        } else throw new SBBException("User entity can not be null");
    }

}
