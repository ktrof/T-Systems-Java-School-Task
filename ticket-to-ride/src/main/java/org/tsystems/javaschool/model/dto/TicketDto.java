package org.tsystems.javaschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;
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

    public TicketScheduleSectionDto getFirstSection() {
        return ticketScheduleSectionDtoList.stream()
                .min(Comparator.comparing(TicketScheduleSectionDto::getIndexWithinTicket))
                .orElse(null);
    }

    public TicketScheduleSectionDto getLastSection() {
        return ticketScheduleSectionDtoList.stream()
                .max(Comparator.comparing(TicketScheduleSectionDto::getIndexWithinTicket))
                .orElse(null);
    }

}
