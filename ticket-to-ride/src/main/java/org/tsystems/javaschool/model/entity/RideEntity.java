package org.tsystems.javaschool.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ride")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@IdClass(RideEntity.ID.class)
public class RideEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @Id
    @Column(name = "ride_date")
    private LocalDate rideDate;

    @Column(name = "tickets_available")
    private int ticketsAvailable;

    @Column(name = "cancelled")
    private boolean cancelled;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {
        TrainEntity trainEntity;
        LocalDate rideDate;
    }

}
