package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<ScheduleSectionEntity> findByTrainAndRideDate(TrainEntity trainEntity, LocalDate rideDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);

        Join<ScheduleSectionEntity, TrainEntity> trainEntityJoin = root.join(ScheduleSectionEntity_.trainEntity);
        Join<TrainEntity, RideEntity> rideEntityJoin = trainEntityJoin.join(TrainEntity_.rideEntityList);

        Predicate trainEquality = criteriaBuilder.equal(root.get(ScheduleSectionEntity_.trainEntity), trainEntity);
        Predicate rideDateEquality = criteriaBuilder.equal(rideEntityJoin.get(RideEntity_.rideDate), rideDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, rideDateEquality));
        TypedQuery<ScheduleSectionEntity> selectByTrain = entityManager.createQuery(criteriaQuery);

        return selectByTrain.getResultStream()
                .sorted(Comparator.comparingInt(ScheduleSectionEntity::getIndexWithinTrainRoute))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleSectionEntity findByTrainAndSectionIndex(TrainEntity trainEntity, int sectionIndex) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);

        Predicate trainEquality = criteriaBuilder.equal(root.get(ScheduleSectionEntity_.trainEntity), trainEntity);
        Predicate indexEquality = criteriaBuilder.equal(root.get(ScheduleSectionEntity_.indexWithinTrainRoute), sectionIndex);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, indexEquality));
        TypedQuery<ScheduleSectionEntity> selectByTrainAndSectionIndex = entityManager.createQuery(criteriaQuery);

        return selectByTrainAndSectionIndex.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<ScheduleSectionEntity> findBySection(SectionEntity sectionEntity) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-ride-graph");
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
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-station-graph");
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("javax.persistence.fetchgraph", entityGraph);
        return entityManager.find(ScheduleSectionEntity.class, id, propertyMap);
    }

    @Override
    public List<ScheduleSectionEntity> findByStationAndRideDate(StationEntity stationEntity, LocalDate rideDate) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-station-graph");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleSectionEntity> criteriaQuery = criteriaBuilder.createQuery(ScheduleSectionEntity.class);
        Root<ScheduleSectionEntity> root = criteriaQuery.from(ScheduleSectionEntity.class);

        Join<ScheduleSectionEntity, SectionEntity> sectionEntityJoin = root.join(ScheduleSectionEntity_.sectionEntity);
        Join<ScheduleSectionEntity, TrainEntity> trainEntityJoin = root.join(ScheduleSectionEntity_.trainEntity);
        Join<TrainEntity, RideEntity> rideEntityJoin = trainEntityJoin.join(TrainEntity_.rideEntityList);

        Predicate departureStationEquality = criteriaBuilder
                .equal(sectionEntityJoin.get(SectionEntity_.stationEntityFrom), stationEntity);
        Predicate destinationStationEquality = criteriaBuilder
                .equal(sectionEntityJoin.get(SectionEntity_.stationEntityTo), stationEntity);
        Predicate rideDateEquality = criteriaBuilder.
                equal(rideEntityJoin.get(RideEntity_.rideDate), rideDate);
        Predicate departureAndRideDate = criteriaBuilder.and(departureStationEquality, rideDateEquality);
        Predicate destinationAndRideDate = criteriaBuilder.and(destinationStationEquality, rideDateEquality);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.or(departureAndRideDate, destinationAndRideDate));
        TypedQuery<ScheduleSectionEntity> selectByDepartureStationIdAndRideDate = entityManager.createQuery(criteriaQuery);
        selectByDepartureStationIdAndRideDate.setHint("javax.persistence.loadgraph", entityGraph);

        return selectByDepartureStationIdAndRideDate.getResultList();
    }

    @Override
    public void add(ScheduleSectionEntity scheduleSectionEntity) {
        entityManager.persist(scheduleSectionEntity);
    }

    @Override
    public void add(Collection<ScheduleSectionEntity> scheduleSectionEntityCollection) {
        scheduleSectionEntityCollection.forEach(this::add);
    }
}
