package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
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
    public UserEntity findById(int id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public UserEntity findByLogin(String login) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(UserEntity_.login), login));
        TypedQuery<UserEntity> selectByLogin = entityManager.createQuery(criteriaQuery);

        return selectByLogin.getResultStream().findFirst().orElse(null);
    }

    @Override
    public UserEntity findByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(UserEntity_.email), email));
        TypedQuery<UserEntity> selectByEmail = entityManager.createQuery(criteriaQuery);

        return selectByEmail.getResultStream().findFirst().orElse(null);
    }


    @Override
    public void add(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    @Override
    public void update(UserEntity userEntity) {
        entityManager.merge(userEntity);
    }

}
