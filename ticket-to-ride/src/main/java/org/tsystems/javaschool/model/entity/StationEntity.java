package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "station")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "timezone")
    private ZoneId timezone;

    @OneToMany(mappedBy = "stationEntityFrom", cascade = CascadeType.ALL)
    List<SectionEntity> sectionEntityList;

}
