package org.tsystems.javaschool.repository.impl;

import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.TrainScheduleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Trofim Kremen
 */
@Repository
public class TrainScheduleRepositoryImpl implements TrainScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TrainScheduleEntity findByTrainAndSectionIndexAndArrivalDate(TrainEntity trainEntity,
                                                                        int indexWithinTrainRoute,
                                                                        LocalDate arrivalDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainScheduleEntity> criteriaQuery = criteriaBuilder.createQuery(TrainScheduleEntity.class);
        Root<TrainScheduleEntity> root = criteriaQuery.from(TrainScheduleEntity.class);

        Join<TrainScheduleEntity, TrainEntity> trainEntityJoin = root
                .join(TrainScheduleEntity_.trainEntity);
        Join<TrainEntity, ScheduleSectionEntity> scheduleSectionEntityJoin = trainEntityJoin
                .join(TrainEntity_.scheduleSectionEntityList);

        Predicate trainEquality = criteriaBuilder
                .equal(root.get(TrainScheduleEntity_.trainEntity), trainEntity);
        Predicate sectionIndexEquality = criteriaBuilder
                .equal(scheduleSectionEntityJoin.get(ScheduleSectionEntity_.indexWithinTrainRoute), indexWithinTrainRoute);
        Predicate arrivalDateEquality = criteriaBuilder
                .equal(root.get(TrainScheduleEntity_.arrivalDate), arrivalDate);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(trainEquality, sectionIndexEquality, arrivalDateEquality));
        TypedQuery<TrainScheduleEntity> selectByTrainAndSectionIndexAndArrivalDate = entityManager.createQuery(criteriaQuery);

        return selectByTrainAndSectionIndexAndArrivalDate.getResultStream().findFirst().orElse(null);
    }

    @Override
    public TrainScheduleEntity add(TrainScheduleEntity trainScheduleEntity) {
        entityManager.persist(trainScheduleEntity);
        return trainScheduleEntity;
    }

    @Override
    public Iterable<TrainScheduleEntity> add(Collection<TrainScheduleEntity> trainScheduleEntityCollection) {
        trainScheduleEntityCollection.forEach(this::add);
        return trainScheduleEntityCollection;
    }

    @Override
    public void delay(TrainScheduleEntity trainScheduleEntity, int minutes) {

    }
}
