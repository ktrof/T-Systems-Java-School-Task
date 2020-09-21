package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name = "train_station")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainStationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity stationEntity;

    @Column(name = "arrival")
    private Instant arrival;

    @Column(name = "waiting_time")
    private Duration waitingTime;

    @Column(name = "departure")
    private Instant departure;

}
