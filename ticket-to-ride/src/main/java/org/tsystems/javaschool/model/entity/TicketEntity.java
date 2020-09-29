package org.tsystems.javaschool.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
@NamedEntityGraph(
        name = "ticket-entity-graph",
        attributeNodes = @NamedAttributeNode(
                value = "scheduleSectionEntitySet",
                subgraph = "schedule-entity-subgraph"),
        subgraphs = {
                @NamedSubgraph(
                        name = "schedule-entity-subgraph",
                        attributeNodes = @NamedAttributeNode(value = "trainEntity")
                )
        }
)
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passengerEntity;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "ticket_schedule",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_section_id", referencedColumnName = "id")
    )
    private Set<ScheduleSectionEntity> scheduleSectionEntitySet;

}
