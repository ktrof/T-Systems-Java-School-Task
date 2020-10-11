package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "calendar")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(CalendarEntity.ID.class)
public class CalendarEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @Id
    @Column(name = "ride_date")
    private LocalDate rideDate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {
        TrainEntity trainEntity;
        LocalDate localDate;
    }
}
