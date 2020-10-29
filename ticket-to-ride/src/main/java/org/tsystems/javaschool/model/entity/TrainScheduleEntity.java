package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Trofim Kremen
 */
@Entity
@Table(name = "train_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(TrainScheduleEntity.ID.class)
public class TrainScheduleEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @Id
    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "minutes_delayed")
    private int minutesDelayed;

    @Column(name = "index_within_train_route")
    private int indexWithinTrainRoute;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {
        TrainEntity trainEntity;
        LocalDate rideDate;
    }

}
