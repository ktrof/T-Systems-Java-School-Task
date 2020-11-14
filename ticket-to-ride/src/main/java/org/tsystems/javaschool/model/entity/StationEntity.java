package org.tsystems.javaschool.model.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.tsystems.javaschool.mapper.ZoneIdConverter;

import javax.persistence.*;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "station")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    @Column(name = "closed")
    private boolean closed;

    @ToString.Exclude
    @OneToMany(mappedBy = "stationEntityFrom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SectionEntity> sectionEntityListFrom;

    @ToString.Exclude
    @OneToMany(mappedBy = "stationEntityTo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SectionEntity> sectionEntityListTo;

}
