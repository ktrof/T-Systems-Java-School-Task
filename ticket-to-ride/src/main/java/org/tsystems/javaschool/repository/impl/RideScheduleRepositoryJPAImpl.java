package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.RideScheduleRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Repository
public class RideScheduleRepositoryJPAImpl implements RideScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RideScheduleEntity> findByTrainAndRideDate(TrainEntity trainEntity, LocalDate rideDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RideScheduleEntity> criteriaQuery = criteriaBuilder.createQuery(RideScheduleEntity.class);
        Root<RideScheduleEntity> root = criteriaQuery.from(RideScheduleEntity.class);

        Predicate trainEquality = criteriaBuilder
                .equal(root.get(RideScheduleEntity_.trainEntity), trainEntity);
        Predicate rideDateEquality = criteriaBuilder
                .equal(root.get(RideScheduleEntity_.rideDate), rideDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, rideDateEquality));
        TypedQuery<RideScheduleEntity> selectByTrainAndDepartureDate = entityManager.createQuery(criteriaQuery);

        return selectByTrainAndDepartureDate.getResultStream()
                .sorted(Comparator.comparingInt(RideScheduleEntity::getIndexWithinTrainRoute))
                .collect(Collectors.toList());
    }

    @Override
    public RideScheduleEntity findByTrainAndSectionIndexAndArrivalDate(TrainEntity trainEntity,
                                                                       int indexWithinTrainRoute,
                                                                       LocalDate arrivalDate) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("ride-schedule-graph");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RideScheduleEntity> criteriaQuery = criteriaBuilder.createQuery(RideScheduleEntity.class);
        Root<RideScheduleEntity> root = criteriaQuery.from(RideScheduleEntity.class);

        Join<RideScheduleEntity, TrainEntity> trainEntityJoin = root
                .join(RideScheduleEntity_.trainEntity);
        Join<TrainEntity, ScheduleSectionEntity> scheduleSectionEntityJoin = trainEntityJoin
                .join(TrainEntity_.scheduleSectionEntityList);

        Predicate trainEquality = criteriaBuilder
                .equal(root.get(RideScheduleEntity_.trainEntity), trainEntity);
        Predicate sectionIndexEquality = criteriaBuilder
                .equal(scheduleSectionEntityJoin.get(ScheduleSectionEntity_.indexWithinTrainRoute), indexWithinTrainRoute);
        Predicate arrivalDateEquality = criteriaBuilder
                .equal(root.get(RideScheduleEntity_.arrivalDateFact), arrivalDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, sectionIndexEquality, arrivalDateEquality));
        TypedQuery<RideScheduleEntity> selectByTrainAndSectionIndexAndArrivalDate = entityManager.createQuery(criteriaQuery);
        selectByTrainAndSectionIndexAndArrivalDate.setHint("javax.persistence.loadgraph", entityGraph);

        return selectByTrainAndSectionIndexAndArrivalDate.getResultStream().findFirst().orElse(null);
    }

    @Override
    public void add(RideScheduleEntity rideScheduleEntity) {
        entityManager.persist(rideScheduleEntity);
    }

    @Override
    public void add(Collection<RideScheduleEntity> rideScheduleEntityCollection) {
        rideScheduleEntityCollection.forEach(this::add);
    }

    @Override
    public void delayArrival(RideScheduleEntity rideScheduleEntity, ZonedDateTime arrival, int minutes) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<RideScheduleEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(RideScheduleEntity.class);
        Root<RideScheduleEntity> root = criteriaUpdate.from(RideScheduleEntity.class);

        Predicate trainEquality = criteriaBuilder.equal(root.get(RideScheduleEntity_.trainEntity),
                rideScheduleEntity.getTrainEntity());
        Predicate arrivalEquality = criteriaBuilder.equal(root.get(RideScheduleEntity_.arrival), arrival);

        criteriaUpdate
                .set(root.get(RideScheduleEntity_.minutesDelayed), minutes)
                .set(root.get(RideScheduleEntity_.arrivalDateFact), arrival.toLocalDate())
                .set(root.get(RideScheduleEntity_.arrival), arrival)
                .where(criteriaBuilder.and(trainEquality, arrivalEquality));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void delayDeparture(RideScheduleEntity rideScheduleEntity, ZonedDateTime departure, int minutes) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<RideScheduleEntity> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(RideScheduleEntity.class);
        Root<RideScheduleEntity> root = criteriaUpdate.from(RideScheduleEntity.class);

        Predicate trainEquality = criteriaBuilder.equal(root.get(RideScheduleEntity_.trainEntity),
                rideScheduleEntity.getTrainEntity());
        Predicate departureEquality = criteriaBuilder.equal(root.get(RideScheduleEntity_.departure), departure);

        criteriaUpdate
                .set(root.get(RideScheduleEntity_.minutesDelayed), minutes)
                .set(root.get(RideScheduleEntity_.departureDateFact), departure.toLocalDate())
                .set(root.get(RideScheduleEntity_.departure), departure)
                .where(criteriaBuilder.and(trainEquality, departureEquality));

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

}
