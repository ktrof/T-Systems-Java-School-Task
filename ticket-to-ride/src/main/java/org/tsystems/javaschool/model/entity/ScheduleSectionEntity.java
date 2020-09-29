package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "schedule_section")
@NoArgsConstructor
@AllArgsConstructor
@Data
@NamedEntityGraph(
        name = "schedule-entity-graph",
        attributeNodes = @NamedAttributeNode(value = "ticketEntitySet")
)
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

    @Column(name = "index_within_train_route")
    private int indexWithinTrainRoute;

    @Column(name = "tickets_available")
    private int ticketsAvailable;

    @Column(name = "arrival")
    private Instant arrival;

    @Column(name = "departure")
    private Instant departure;

    @ManyToMany(mappedBy = "scheduleSectionEntitySet", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<TicketEntity> ticketEntitySet;

}