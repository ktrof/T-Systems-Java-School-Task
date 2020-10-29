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
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("schedule-calendar-graph");
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
        Join<TrainEntity, CalendarEntity> calendarEntityJoin = trainEntityJoin.join(TrainEntity_.calendarEntityList);

        Predicate departureStationEquality = criteriaBuilder
                .equal(sectionEntityJoin.get(SectionEntity_.stationEntityFrom), stationEntity);
        Predicate destinationStationEquality = criteriaBuilder
                .equal(sectionEntityJoin.get(SectionEntity_.stationEntityTo), stationEntity);
        Predicate rideDateEquality = criteriaBuilder.
                equal(calendarEntityJoin.get(CalendarEntity_.rideDate), rideDate);
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
    public ScheduleSectionEntity add(ScheduleSectionEntity scheduleSectionEntity) {
        entityManager.persist(scheduleSectionEntity);
        return scheduleSectionEntity;
    }

    @Override
    public Iterable<ScheduleSectionEntity> add(Collection<ScheduleSectionEntity> scheduleSectionEntityCollection) {
        scheduleSectionEntityCollection.forEach(this::add);
        return scheduleSectionEntityCollection;
    }
}
