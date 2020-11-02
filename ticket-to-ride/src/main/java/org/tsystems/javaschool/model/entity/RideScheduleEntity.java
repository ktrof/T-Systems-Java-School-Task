package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * @author Trofim Kremen
 */
@Entity
@Table(name = "ride_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(RideScheduleEntity.ID.class)
@NamedEntityGraph(
        name = "ride-schedule-graph",
        attributeNodes = @NamedAttributeNode(value = "trainEntity")
)
public class RideScheduleEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @Id
    @Column(name = "departure")
    private ZonedDateTime departure;

    @Column(name = "ride_date")
    private LocalDate rideDate;

    @Column(name = "departure_date_fact")
    private LocalDate departureDateFact;

    @Column(name = "departure_date_plan")
    private LocalDate departureDatePlan;

    @Column(name = "arrival")
    private ZonedDateTime arrival;

    @Column(name = "arrival_date_fact")
    private LocalDate arrivalDateFact;

    @Column(name = "arrival_date_plan")
    private LocalDate arrivalDatePlan;

    @Column(name = "minutes_delayed")
    private int minutesDelayed;

    @Column(name = "index_within_train_route")
    private int indexWithinTrainRoute;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {
        TrainEntity trainEntity;
        ZonedDateTime departure;
    }

}
