package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tsystems.javaschool.mapper.PassengerMapper;
import org.tsystems.javaschool.mapper.TicketMapper;
import org.tsystems.javaschool.model.dto.*;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.TicketEntity;
import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.TrainEntity;
import org.tsystems.javaschool.repository.PassengerRepository;
import org.tsystems.javaschool.repository.ScheduleSectionRepository;
import org.tsystems.javaschool.repository.TicketRepository;
import org.tsystems.javaschool.repository.TicketScheduleSectionRepository;
import org.tsystems.javaschool.service.TicketService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    public static final Duration MIN_ALLOWED_TIME_TO_BUY_TICKET = Duration.ofMinutes(10);

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TicketScheduleSectionRepository ticketScheduleSectionRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final TicketMapper ticketMapper;
    private final PassengerMapper passengerMapper;

    @Override
    public boolean hasPassenger(RouteDto routeDto, PassengerDto passengerDto) {
        List<TicketScheduleSectionDto> newTicketSections = mapRouteToTicketSections(routeDto);
        try {
            List<TicketDto> passengerTicketDtoList = getByPassengerNameAndBirthDate(
                    passengerDto.getFirstName(), passengerDto.getSecondName(), passengerDto.getBirthDate());
            for (TicketDto passengerTicket : passengerTicketDtoList) {
                for (TicketScheduleSectionDto existingTicketSection : passengerTicket.getTicketScheduleSectionDtoList()) {
                    for (TicketScheduleSectionDto newTicketSection : newTicketSections) {
                        if (areSectionsSame(existingTicketSection, newTicketSection)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error checking if the ticket already has a passenger attached", e);
        }
        return false;
    }

    private List<TicketDto> getByPassengerNameAndBirthDate(String firstName, String secondName, LocalDate birthDate) {
        List<TicketDto> ticketDtoList = null;
        try {
            List<TicketEntity> trainEntityList = ticketRepository
                    .findByPassengerNameAndBirthDate(firstName, secondName, birthDate);
            ticketDtoList = ticketMapper.toDtoList(trainEntityList);
        } catch (Exception e) {
            log.error("Error getting tickets by passenger details", e);
        }
        return ticketDtoList;
    }

    private boolean areSectionsSame(TicketScheduleSectionDto section1, TicketScheduleSectionDto section2) {
        return Objects.equals(section1.getScheduleSectionId(), section2.getScheduleSectionId()) &&
                Objects.equals(section1.getDepartureDate(), section2.getDepartureDate()) &&
                Objects.equals(section1.getDepartureTime(), section2.getDepartureTime());
    }

    @Override
    public boolean isTimeLeft(RouteDto routeDto) {
        return Duration.between(LocalDateTime.now(), routeDto.getDepartureTime())
                .compareTo(MIN_ALLOWED_TIME_TO_BUY_TICKET) < 0;
    }

    @Override
    public boolean areTicketsAvailable(RouteDto routeDto) {
        return routeDto.getTicketsAvailable() >= 1;
    }

    @Override
    public TicketDto buyTicket(RouteDto routeDto, PassengerDto passengerDto) {
        PassengerEntity passengerEntity = passengerRepository
                .findByNameAndBirthDate(
                        passengerDto.getFirstName(), passengerDto.getSecondName(), passengerDto.getBirthDate()
                );
        if (passengerEntity == null) {
            passengerEntity = passengerRepository.add(passengerMapper.toEntity(passengerDto));
        }

        TicketDto newTicket = TicketDto.builder()
                .ticketScheduleSectionDtoList(mapRouteToTicketSections(routeDto))
                .passengerDto(passengerMapper.toDto(passengerEntity))
                .build();
        createTicket(newTicket);

        return newTicket;
    }

    private List<TicketScheduleSectionDto> mapRouteToTicketSections(RouteDto routeDto) {
        return routeDto.getRoutePartDtoList().stream()
                .map(routePartDto -> routePartDto.getScheduleSectionDtoList().stream()
                        .map(scheduleSectionDto ->
                                TicketScheduleSectionDto.builder()
                                        .ticketId(0)
                                        .scheduleSectionId(scheduleSectionDto.getId())
                                        .departureDate(routePartDto.getDepartureTime().toLocalDate())
                                        .departureTime(scheduleSectionDto.getDeparture())
                                        .build()
                        )
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void createTicket(TicketDto newTicket) {
        try {
            TicketEntity ticketEntity = ticketRepository.add(ticketMapper.toEntity(newTicket));
            newTicket.getTicketScheduleSectionDtoList()
                    .forEach(ticketScheduleSectionDto ->
                            ticketScheduleSectionRepository.add(TicketScheduleSectionEntity.builder()
                                    .ticketEntity(ticketEntity)
                                    .departureDate(ticketScheduleSectionDto.getDepartureDate())
                                    .departureTime(ticketScheduleSectionDto.getDepartureTime())
                                    .scheduleSectionEntity(
                                            scheduleSectionRepository.findById(
                                                    ticketScheduleSectionDto.getScheduleSectionId()
                                            )
                                    )
                                    .build()));
        } catch (Exception e) {
            log.error("Error creating ticket", e);
        }
    }
}
