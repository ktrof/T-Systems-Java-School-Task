package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.StationEntity;
import org.tsystems.javaschool.model.entity.StationEntity_;
import org.tsystems.javaschool.repository.StationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
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
    public StationEntity add(StationEntity stationEntity) {
        entityManager.persist(stationEntity);
        return stationEntity;
    }

    @Override
    public StationEntity update(StationEntity stationEntity) {
        return entityManager.merge(stationEntity);
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
