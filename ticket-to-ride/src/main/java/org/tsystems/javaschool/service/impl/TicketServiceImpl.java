package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsystems.javaschool.controller.RouteCache;
import org.tsystems.javaschool.mapper.PassengerMapper;
import org.tsystems.javaschool.mapper.PassengerMapperImpl;
import org.tsystems.javaschool.mapper.TicketMapper;
import org.tsystems.javaschool.model.dto.*;
import org.tsystems.javaschool.model.entity.PassengerEntity;
import org.tsystems.javaschool.model.entity.TicketEntity;
import org.tsystems.javaschool.model.entity.TicketScheduleSectionEntity;
import org.tsystems.javaschool.model.entity.UserEntity;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.TicketService;

import javax.inject.Provider;
import java.time.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    public static final long MIN_ALLOWED_TIME_TO_BUY_TICKET = 10;

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TicketScheduleSectionRepository ticketScheduleSectionRepository;
    private final ScheduleSectionRepository scheduleSectionRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;
    private final PassengerMapper passengerMapper;
    private final Provider<RouteCache> routeCacheProvider;

    @Override
    @Transactional
    public List<TicketDto> getByUserLogin(String login) {
        List<TicketDto> ticketDtoList = null;
        try {
            UserEntity userEntity = userRepository.findByLogin(login);
            List<TicketEntity> ticketEntityList = ticketRepository.findByUser(userEntity);
            ticketDtoList = ticketMapper.toDtoList(ticketEntityList);
        } catch (Exception e) {
            log.error("Error getting tickets by user", e);
        }
        return ticketDtoList;
    }

    @Override
    @Transactional
    public boolean hasPassenger(PassengerFormDto passengerFormDto) {
        List<TicketScheduleSectionDto> newTicketSections = mapRouteToTicketSections(getRouteDto(passengerFormDto));
        try {
            List<TicketDto> passengerTicketDtoList = getByPassengerNameAndBirthDate(
                    passengerFormDto.getFirstName(), passengerFormDto.getSecondName(), passengerFormDto.getBirthDate());
            for (TicketDto passengerTicket : passengerTicketDtoList) {
                for (TicketScheduleSectionDto existingTicketSection : passengerTicket.getTicketScheduleSectionDtoList()) {
                    for (TicketScheduleSectionDto newTicketSection : newTicketSections) {
                        if (areSectionsSame(newTicketSection, existingTicketSection)) {
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
    public boolean isTimeLeft(PassengerFormDto passengerFormDto) {
        ZonedDateTime minimumTime = ZonedDateTime.now(ZoneId.of("UTC+3")).plusMinutes(MIN_ALLOWED_TIME_TO_BUY_TICKET);
        return minimumTime.isBefore(getRouteDto(passengerFormDto).getDepartureTime());
    }

    @Override
    public boolean areTicketsAvailable(PassengerFormDto passengerFormDto) {
        return getRouteDto(passengerFormDto).getTicketsAvailable() >= 1;
    }

    @Override
    @Transactional
    public TicketDto buyTicket(PassengerFormDto passengerFormDto) {
        PassengerEntity passengerEntity = passengerRepository
                .findByNameAndBirthDate(
                        passengerFormDto.getFirstName(), passengerFormDto.getSecondName(), passengerFormDto.getBirthDate()
                );
        if (Objects.isNull(passengerEntity)) {
            passengerEntity = passengerMapper.toEntity(passengerFormDto);
            passengerEntity.setUserEntity(userRepository.findByLogin(passengerFormDto.getUserLogin()));
            PassengerEntity newPassengerEntity = passengerRepository.add(passengerEntity);
            System.out.println(newPassengerEntity.toString());
        }

        TicketDto newTicket = TicketDto.builder()
                .ticketScheduleSectionDtoList(mapRouteToTicketSections(getRouteDto(passengerFormDto)))
                .passengerDto(passengerMapper.toDto(passengerEntity))
                .build();

        createTicket(newTicket);

        return newTicket;
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
                                    .arrivalDate(ticketScheduleSectionDto.getArrivalDate())
                                    .arrivalTime(ticketScheduleSectionDto.getArrivalTime())
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

    private RouteDto getRouteDto(PassengerFormDto passengerFormDto) {
        return routeCacheProvider.get().findById(passengerFormDto.getId());
    }

    private List<TicketScheduleSectionDto> mapRouteToTicketSections(RouteDto routeDto) {
        AtomicInteger indexWithinTicket = new AtomicInteger(0);
        return routeDto.getRoutePartDtoList().stream()
                .map(routePartDto -> routePartDto.getScheduleSectionDtoList().stream()
                        .map(scheduleSectionDto ->
                                TicketScheduleSectionDto.builder()
                                        .scheduleSectionId(scheduleSectionDto.getId())
                                        .departureDate(routePartDto.getDepartureTime().toLocalDate())
                                        .departureTime(routePartDto.getDepartureTime().toLocalTime())
                                        .arrivalDate(routePartDto.getArrivalTime().toLocalDate())
                                        .arrivalTime(routePartDto.getArrivalTime().toLocalTime())
                                        .indexWithinTicket(indexWithinTicket.getAndIncrement())
                                        .build())
                        .collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
