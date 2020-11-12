package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.StationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * The type Station repository jpa.
 *
 * @author Trofim Kremen
 */
@Repository
public class StationRepositoryJPAImpl implements StationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StationEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StationEntity> criteriaQuery = criteriaBuilder.createQuery(StationEntity.class);
        Root<StationEntity> root = criteriaQuery.from(StationEntity.class);

        criteriaQuery
                .select(root);
        TypedQuery<StationEntity> selectAll = entityManager.createQuery(criteriaQuery);

        return selectAll.getResultList();
    }

    @Override
    public List<StationEntity> findAllByTrain(TrainEntity trainEntity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StationEntity> criteriaQuery = criteriaBuilder.createQuery(StationEntity.class);
        Root<StationEntity> root = criteriaQuery.from(StationEntity.class);

        Join<StationEntity, SectionEntity> sectionEntityJoinFrom = root
                .join(StationEntity_.sectionEntityListFrom);
        Join<StationEntity, SectionEntity> sectionEntityJoinTo = root
                .join(StationEntity_.sectionEntityListTo);
        Join<SectionEntity, ScheduleSectionEntity> scheduleSectionEntityJoinFrom = sectionEntityJoinFrom
                .join(SectionEntity_.scheduleSectionEntityList);
        Join<SectionEntity, ScheduleSectionEntity> scheduleSectionEntityJoinTo = sectionEntityJoinTo
                .join(SectionEntity_.scheduleSectionEntityList);

        Predicate trainEqualityFrom = criteriaBuilder.equal(scheduleSectionEntityJoinFrom
                .get(ScheduleSectionEntity_.trainEntity), trainEntity);
        Predicate trainEqualityTo = criteriaBuilder.equal(scheduleSectionEntityJoinTo
                .get(ScheduleSectionEntity_.trainEntity), trainEntity);

        criteriaQuery
                .select(root)
                .distinct(true)
                .where(criteriaBuilder.or(trainEqualityFrom, trainEqualityTo));
        TypedQuery<StationEntity> selectAllByTrain = entityManager.createQuery(criteriaQuery);

        return selectAllByTrain.getResultList();
    }

    @Override
    public StationEntity findById(int id) {
        return entityManager.find(StationEntity.class, id);
    }

    @Override
    public StationEntity findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StationEntity> criteriaQuery = criteriaBuilder.createQuery(StationEntity.class);
        Root<StationEntity> root = criteriaQuery.from(StationEntity.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(StationEntity_.name), name));
        TypedQuery<StationEntity> selectByName = entityManager.createQuery(criteriaQuery);

        return selectByName.getResultStream().findFirst().orElse(null);
    }

    @Override
    public void add(StationEntity stationEntity) {
        entityManager.persist(stationEntity);
    }

    @Override
    public void update(StationEntity stationEntity) {
        entityManager.merge(stationEntity);
    }

    private void updateClosedAttribute(StationEntity stationEntity, boolean isClosed) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<StationEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(StationEntity.class);
        Root<StationEntity> root = criteriaUpdate.from(StationEntity.class);

        criteriaUpdate
                .set(root.get(StationEntity_.CLOSED), isClosed)
                .where(criteriaBuilder.equal(root.get(StationEntity_.id), stationEntity.getId()));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void close(StationEntity stationEntity) {
        updateClosedAttribute(stationEntity, true);
    }

    @Override
    public void open(StationEntity stationEntity) {
        updateClosedAttribute(stationEntity, false);
    }

}
