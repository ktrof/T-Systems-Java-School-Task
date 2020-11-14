package org.tsystems.javaschool.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@NamedEntityGraph(
        name = "ticket-graph",
        attributeNodes = @NamedAttributeNode(value = "ticketScheduleSectionEntityList")
)
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passengerEntity;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "ride_date")
    private LocalDate rideDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "ticketEntity", fetch = FetchType.LAZY)
    private List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList;

}
