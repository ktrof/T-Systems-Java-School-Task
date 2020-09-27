package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.SectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The type Section repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class SectionRepositoryJPAImpl implements SectionRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<SectionEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SectionEntity> criteriaQuery = criteriaBuilder.createQuery(SectionEntity.class);
        Root<SectionEntity> root = criteriaQuery.from(SectionEntity.class);
        criteriaQuery
                .select(root);
        TypedQuery<SectionEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public SectionEntity findById(int id) throws RepositoryException {
        SectionEntity sectionEntity = entityManager.find(SectionEntity.class, id);
        if (sectionEntity != null) {
            return sectionEntity;
        } else throw new RepositoryException("No section found by given id");
    }

    @Override
    public SectionEntity add(SectionEntity sectionEntity) throws RepositoryException {
        if (sectionEntity != null) {
            entityManager.persist(entityManager);
            return sectionEntity;
        } else throw new RepositoryException("Section entity can not be null");
    }

    @Override
    public SectionEntity updateStations(StationEntity stationFrom, StationEntity stationTo, SectionEntity sectionEntity) throws RepositoryException {
        if (stationFrom != null || stationTo != null || sectionEntity != null) {
            sectionEntity.setStationEntityFrom(stationFrom);
            sectionEntity.setStationEntityTo(stationTo);
            return entityManager.merge(sectionEntity);
        } else throw new RepositoryException("Section entity and station entities can not be null");
    }

    @Override
    public void remove(SectionEntity sectionEntity) throws RepositoryException {
        if (sectionEntity != null) {
            entityManager.remove(sectionEntity);
        } else throw new RepositoryException("Section entity can not be null");
    }

}
