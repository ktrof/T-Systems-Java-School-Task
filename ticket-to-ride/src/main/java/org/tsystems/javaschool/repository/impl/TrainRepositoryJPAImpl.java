package org.tsystems.javaschool.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.exception.RepositoryException;
import org.tsystems.javaschool.mapper.TrainMapper;
import org.tsystems.javaschool.mapper.TrainStationMapper;
import org.tsystems.javaschool.model.dto.TrainDto;
import org.tsystems.javaschool.model.dto.TrainStationDto;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.model.entity.TrainStationEntity;
import org.tsystems.javaschool.repository.ITrainRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
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
    private TrainMapper trainMapper;

    @Autowired
    private TrainStationMapper trainStationMapper;

    @Transactional
    @Override
    public List<TrainDto> findAll() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainEntity> trainEntityCriteriaQuery = criteriaBuilder.createQuery(TrainEntity.class);
        Root<TrainEntity> trainEntityRoot = trainEntityCriteriaQuery.from(TrainEntity.class);
        CriteriaQuery<TrainEntity> selectAll = trainEntityCriteriaQuery.select(trainEntityRoot);
        TypedQuery<TrainEntity> selectAllQuery = entityManager.createQuery(selectAll);
        List<TrainEntity> trainEntityList = selectAllQuery.getResultList();
        return trainEntityList.stream()
                .map(trainEntity -> trainMapper.toDto(trainEntity))
                .collect(Collectors.toList());

    }

    /**
     * @param id train id
     * @return trainDto object
     */
    @Transactional
    @Override
    public TrainDto findById(@NotNull int id) {

        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id);
        return trainMapper.toDto(trainEntity);

    }

    /**
     * @param trainDto trainDto object
     * @return created train id
     */
    @Override
    public int createTrain(TrainDto trainDto) {

        TrainEntity trainEntity = trainMapper.toEntity(trainDto);

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
    public int modifyTrain(TrainDto trainDto) throws RepositoryException{

        TrainEntity trainEntity = entityManager.find(TrainEntity.class, trainDto.getId());

        if (trainEntity != null) {
            entityManager.getTransaction().begin();
            entityManager.detach(trainEntity);
            trainEntity.setName(trainDto.getName());
            trainEntity.setAvgSpeed(trainDto.getAvgSpeed());
            trainEntity.setNumberOfSeats(trainDto.getNumberOfSeats());
            trainEntity.setSymbolCode(trainDto.getSymbolCode());
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
    public int removeTrainById(int id) throws RepositoryException {

        entityManager.getTransaction().begin();
        TrainEntity trainEntity = entityManager.find(TrainEntity.class, id);
        if (trainEntity != null) {
            entityManager.remove(trainEntity);
            entityManager.getTransaction().commit();
            return trainEntity.getId();
        } else throw new RepositoryException("No train found by given entity");

    }

    @Transactional
    @Override
    public List<TrainStationDto> findAllStationsByTrainId(int id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainStationEntity> selectAllStationsQuery = criteriaBuilder.createQuery(TrainStationEntity.class);
        Root<TrainStationEntity> root = selectAllStationsQuery.from(TrainStationEntity.class);
        selectAllStationsQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("trainEntity.id"), id));
        TypedQuery<TrainStationEntity> selectAllStations = entityManager.createQuery(selectAllStationsQuery);
        List<TrainStationEntity> trainStationEntityList = selectAllStations.getResultList();

        return trainStationEntityList.stream()
                .map(trainStationEntity -> trainStationMapper.toDto(trainStationEntity))
                .collect(Collectors.toList());
    }

}
