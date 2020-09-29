package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "section")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private int length;

    @OneToMany(mappedBy = "sectionEntity", cascade = CascadeType.ALL)
    List<ScheduleSectionEntity> scheduleSectionEntityList;

}
