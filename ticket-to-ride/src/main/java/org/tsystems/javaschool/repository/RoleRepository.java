package org.tsystems.javaschool.repository;

import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.RoleEntity;

import java.util.Collection;

/**
 * The interface Role repository.
 *
 * @author Trofim Kremen
 */
public interface RoleRepository {

    /**
     * Find roles by user login list.
     *
     * @param userLogin the user login
     * @return the list
     */
    Collection<RoleEntity> findRolesByUserLogin(String userLogin);

    /**
     * Find by id role entity.
     *
     * @param name the name
     * @return the role entity
     */
    RoleEntity findByName(String name);

}
