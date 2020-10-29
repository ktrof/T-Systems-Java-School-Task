package org.tsystems.javaschool.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private int totalPrice;

    @ToString.Exclude
    @OneToMany(mappedBy = "ticketEntity", fetch = FetchType.LAZY)
    private List<TicketScheduleSectionEntity> ticketScheduleSectionEntityList;

}
