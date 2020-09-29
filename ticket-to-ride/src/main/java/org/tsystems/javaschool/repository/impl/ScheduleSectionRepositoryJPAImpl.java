package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.SBBException;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity_;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Schedule section repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class ScheduleSectionRepositoryJPAImpl implements ScheduleSectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ScheduleSectionEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);
        criteriaQuery
                .select(root);
        TypedQuery<ScheduleSectionEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<ScheduleSectionEntity> findByTrain(TrainEntity trainEntity) throws SBBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);
        Predicate trainEqualsTrainEntity = criteriaBuilder.equal(root.get(ScheduleSectionEntity_.trainEntity), trainEntity);
        criteriaQuery
                .select(root)
                .where(trainEqualsTrainEntity);
        TypedQuery<ScheduleSectionEntity> selectByTrain = entityManager.createQuery(criteriaQuery);

        List<ScheduleSectionEntity> scheduleSectionEntityList = selectByTrain.getResultList();
        if (scheduleSectionEntityList.size() != 0) {
            return scheduleSectionEntityList;
        } else throw new SBBException("No schedule sections found by given train entity");
    }

    @Override
    public ScheduleSectionEntity findById(int id) throws SBBException {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-entity-graph");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("javax.persistence.fetchgraph", entityGraph);
        ScheduleSectionEntity scheduleSectionEntity = entityManager.find(ScheduleSectionEntity.class, id, propertyMap);

        if (scheduleSectionEntity != null) {
            return scheduleSectionEntity;
        } else throw new SBBException("No schedule section found by given id");
    }

    @Override
    public ScheduleSectionEntity add(ScheduleSectionEntity scheduleSectionEntity) throws SBBException {
        if (scheduleSectionEntity != null) {
            entityManager.persist(scheduleSectionEntity);
            return scheduleSectionEntity;
        } else throw new SBBException("Schedule section entity can not be null");
    }
}
