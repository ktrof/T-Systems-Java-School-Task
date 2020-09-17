package org.tsystems.javaschool.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.ITrainRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Repository
public class TrainRepositoryJPAImpl implements ITrainRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TrainMapper mapper;

    @Override
    public List<TrainDto> findAll() {

        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainEntity> trainEntityCriteriaQuery = criteriaBuilder.createQuery(TrainEntity.class);
        Root<TrainEntity> trainEntityRoot = trainEntityCriteriaQuery.from(TrainEntity.class);
        CriteriaQuery<TrainEntity> selectAll = trainEntityCriteriaQuery.select(trainEntityRoot);
        TypedQuery<TrainEntity> selectAllQuery = entityManager.createQuery(selectAll);
        List<TrainEntity> trainEntityList = selectAllQuery.getResultList();
        entityManager.getTransaction().commit();

        return trainEntityList.stream()
                .map(trainEntity -> mapper.toDto(trainEntity))
                .collect(Collectors.toList());

    }

    /**
     * @param id train id
     * @return trainDto object
     */
    @Override
    public TrainDto findById(@NotNull int id) {

        entityManager.getTransaction().begin();
        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id);
        entityManager.getTransaction().commit();
        return mapper.toDto(trainEntity);

    }

    /**
     * @param trainDto trainDto object
     * @return created train id
     */
    @Override
    public int createTrain(@NotNull TrainDto trainDto) {

        TrainEntity trainEntity = mapper.toEntity(trainDto);

        entityManager.getTransaction().begin();
        entityManager.persist(trainEntity);
        entityManager.getTransaction().commit();
        return trainEntity.getId();

    }

    /**
     * @param trainDto trainDto object
     * @return modified train id
     */
    @Override
    public int modifyTrain(@NotNull TrainDto trainDto) throws RepositoryException{

        TrainEntity trainEntity = entityManager.find(TrainEntity.class, trainDto.getId());

        if (trainEntity != null) {
            entityManager.getTransaction().begin();
            entityManager.detach(trainEntity);
            trainEntity.setName(trainDto.getName());
            trainEntity.setTonnage(trainDto.getTonnage());
            trainEntity.setTechnicalSpeed(trainDto.getTechnicalSpeed());
            int trainId = entityManager.merge(trainEntity).getId();
            entityManager.getTransaction().commit();
            return trainId;
        } else throw new RepositoryException("No train found by given entity");

    }

    /**
     * @param id train id
     * @return removed train id
     */
    @Override
    public int removeTrainById(@NotNull int id) throws RepositoryException {

        entityManager.getTransaction().begin();
        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id);
        if (trainEntity != null) {
            entityManager.remove(trainEntity);
            entityManager.getTransaction().commit();
            return trainEntity.getId();
        } else throw new RepositoryException("No train found by given entity");

    }

}
