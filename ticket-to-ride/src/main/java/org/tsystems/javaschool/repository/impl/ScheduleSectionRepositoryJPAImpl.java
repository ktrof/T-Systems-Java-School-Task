package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.ScheduleSectionEntity_;
import org.tsystems.javaschool.model.entity.SectionEntity;
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
import java.util.Collection;
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
    public List<ScheduleSectionEntity> findByTrain(TrainEntity trainEntity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);
        Predicate trainEqualsTrainEntity = criteriaBuilder.equal(root.get(ScheduleSectionEntity_.trainEntity), trainEntity);
        criteriaQuery
                .select(root)
                .where(trainEqualsTrainEntity);
        TypedQuery<ScheduleSectionEntity> selectByTrain = entityManager.createQuery(criteriaQuery);

        return selectByTrain.getResultList();
    }

    @Override
    public List<ScheduleSectionEntity> findBySection(SectionEntity sectionEntity) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-section-graph");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(ScheduleSectionEntity_.sectionEntity), sectionEntity));
        TypedQuery<ScheduleSectionEntity> selectBySection = entityManager.createQuery(criteriaQuery);
        selectBySection.setHint("javax.persistence.loadgraph", entityGraph);

        return selectBySection.getResultList();
    }

    @Override
    public ScheduleSectionEntity findById(int id) {
        Map<String, Object> propertyMap = new HashMap<>();
        return entityManager.find(ScheduleSectionEntity.class, id, propertyMap);
    }

    @Override
    public ScheduleSectionEntity add(ScheduleSectionEntity scheduleSectionEntity) {
        entityManager.persist(scheduleSectionEntity);
        return scheduleSectionEntity;
    }

    @Override
    public Iterable<ScheduleSectionEntity> add(Collection<ScheduleSectionEntity> scheduleSectionEntityCollection) {
        for (ScheduleSectionEntity scheduleSectionEntity : scheduleSectionEntityCollection) {
            entityManager.persist(scheduleSectionEntity);
        }
        return scheduleSectionEntityCollection;
    }
}
