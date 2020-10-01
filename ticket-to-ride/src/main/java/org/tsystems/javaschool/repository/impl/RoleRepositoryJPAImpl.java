package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.RoleEntity;
import org.tsystems.javaschool.model.entity.RoleEntity_;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.model.entity.UserEntity_;
import org.tsystems.javaschool.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type Role repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class RoleRepositoryJPAImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<RoleEntity> findRolesByUserLogin(String userLogin) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
        Join<RoleEntity, UserEntity> userEntityJoin = root.join(RoleEntity_.userEntitySet);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(userEntityJoin.get(UserEntity_.login), userLogin));
        TypedQuery<RoleEntity> selectByUserLogin = entityManager.createQuery(criteriaQuery);

        Collection<RoleEntity> roleEntityCollection = selectByUserLogin.getResultStream().collect(Collectors.toSet());
        if (roleEntityCollection.size() != 0) {
            return roleEntityCollection;
        } else throw new SBBException("No roles found by given user login");
    }

    @Override
    public RoleEntity findByName(String name) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(RoleEntity_.name), name));
        TypedQuery<RoleEntity> selectByName = entityManager.createQuery(criteriaQuery);

        RoleEntity roleEntity = selectByName.getSingleResult();
        if (roleEntity != null) {
            return roleEntity;
        } else throw new SBBException("No role found by given name");
    }

}