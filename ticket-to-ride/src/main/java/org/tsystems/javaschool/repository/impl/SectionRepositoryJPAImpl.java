package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.SectionEntity;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.repository.SectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
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
    public SectionEntity findById(int id) {
        return entityManager.find(SectionEntity.class, id);
    }

    @Override
    public SectionEntity add(SectionEntity sectionEntity) {
        entityManager.persist(entityManager);
        return sectionEntity;
    }

    @Override
    public Iterable<SectionEntity> add(Collection<SectionEntity> sectionEntityCollection) {
        for (SectionEntity sectionEntity : sectionEntityCollection) {
            entityManager.persist(sectionEntity);
        }
        return sectionEntityCollection;
    }

    @Override
    public SectionEntity updateStations(StationEntity stationFrom, StationEntity stationTo, SectionEntity sectionEntity) {
        sectionEntity.setStationEntityFrom(stationFrom);
        sectionEntity.setStationEntityTo(stationTo);
        return entityManager.merge(sectionEntity);
    }

    @Override
    public void remove(SectionEntity sectionEntity) {
        entityManager.remove(sectionEntity);
    }

}
