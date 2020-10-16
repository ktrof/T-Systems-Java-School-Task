package org.tsystems.javaschool.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Trofim Kremen
 */
@Entity
@Table(name = "ticket_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@IdClass(TicketScheduleSectionEntity.ID.class)
@NamedEntityGraph(
        name = "ticket-schedule-section-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "ticketEntity"),
                @NamedAttributeNode(value = "scheduleSectionEntity")
        }
)
public class TicketScheduleSectionEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private TicketEntity ticketEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "schedule_section_id", referencedColumnName = "id")
    private ScheduleSectionEntity scheduleSectionEntity;

    @Id
    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "index_within_ticket")
    private int indexWithinTicket;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {
        private TicketEntity ticketEntity;
        private ScheduleSectionEntity scheduleSectionEntity;
        private LocalDate departureDate;
    }

}
