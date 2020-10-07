package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.mapper.ZoneIdConverter;

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

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "timezone")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId timezone;

    @OneToMany(mappedBy = "stationEntityFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<SectionEntity> sectionEntityList;

}
