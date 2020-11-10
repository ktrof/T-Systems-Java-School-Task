package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "train")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "avg_speed")
    private int avgSpeed;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "trainEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CalendarEntity> calendarEntityList;

    @OneToMany(mappedBy = "trainEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ScheduleSectionEntity> scheduleSectionEntityList;

}
