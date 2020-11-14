package org.tsystems.javaschool.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "section")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "station_id_from")
    private StationEntity stationEntityFrom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "station_id_to")
    private StationEntity stationEntityTo;

    @Column(name = "length")
    private double length;

    @ToString.Exclude
    @OneToMany(mappedBy = "sectionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScheduleSectionEntity> scheduleSectionEntityList;

}
