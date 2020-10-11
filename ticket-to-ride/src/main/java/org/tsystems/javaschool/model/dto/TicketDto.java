package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto implements Serializable {

    private int id;

    private PassengerDto passengerDto;

    private int totalPrice;

    private List<TicketScheduleSectionDto> ticketScheduleSectionDtoList;
}
