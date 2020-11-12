package org.tsystems.javaschool.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "schedule_section")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "schedule-calendar-graph",
                attributeNodes = @NamedAttributeNode(value = "trainEntity", subgraph = "train-subgraph"),
                subgraphs = @NamedSubgraph(
                        name = "train-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "rideEntityList"),
                                @NamedAttributeNode(value = "rideScheduleEntityList")
                        }
                )
        ),
        @NamedEntityGraph(
                name = "schedule-station-graph",
                attributeNodes = @NamedAttributeNode(value = "sectionEntity", subgraph = "section-subgraph"),
                subgraphs = @NamedSubgraph(
                        name = "section-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "stationEntityFrom"),
                                @NamedAttributeNode(value = "stationEntityTo")
                        }
                )
        )
})
public class ScheduleSectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity trainEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "section_id")
    private SectionEntity sectionEntity;

    @Column(name = "stop_duration")
    private long stopDuration;

    @Column(name = "index_within_train_route")
    private int indexWithinTrainRoute;

    @Column(name = "departure")
    private LocalTime departure;

    @Column(name = "arrival")
    private LocalTime arrival;

    @ToString.Exclude
    @OneToMany(mappedBy = "scheduleSectionEntity", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList;

}
