package org.tsystems.javaschool.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tsystems.javaschool.mapper.PassengerMapper;
import org.tsystems.javaschool.mapper.TicketMapper;
import org.tsystems.javaschool.model.dto.passenger.PassengerDto;
import org.tsystems.javaschool.model.dto.passenger.PassengerFormDto;
import org.tsystems.javaschool.model.dto.route.RouteDto;
import org.tsystems.javaschool.model.dto.route.RoutePartDto;
import org.tsystems.javaschool.model.dto.schedulesection.ScheduleSectionDto;
import org.tsystems.javaschool.model.dto.section.SectionDto;
import org.tsystems.javaschool.model.dto.ticket.TicketDto;
import org.tsystems.javaschool.model.dto.ticketschedule.TicketScheduleSectionDto;
import org.tsystems.javaschool.model.dto.train.TrainDto;
import org.tsystems.javaschool.model.dto.train.TrainType;
import org.tsystems.javaschool.model.entity.*;
import org.tsystems.javaschool.repository.*;
import org.tsystems.javaschool.service.TicketService;

import javax.inject.Provider;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Trofim Kremen
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TicketServiceImplTest {

    TicketService ticketService;

    @Mock TicketRepository ticketRepository;
    @Mock PassengerRepository passengerRepository;
    @Mock TicketScheduleSectionRepository ticketScheduleSectionRepository;
    @Mock ScheduleSectionRepository scheduleSectionRepository;
    @Mock UserRepository userRepository;
    @Mock TicketMapper ticketMapper;
    @Mock PassengerMapper passengerMapper;
    @Mock Provider<RouteCache> routeCacheProvider;
    @Mock RouteCache routeCache;

    @BeforeEach
    public void init() {
        ticketService = new TicketServiceImpl(
                ticketRepository,
                passengerRepository,
                ticketScheduleSectionRepository,
                scheduleSectionRepository,
                userRepository,
                ticketMapper,
                passengerMapper,
                routeCacheProvider
        );
        lenient().when(routeCacheProvider.get()).thenReturn(routeCache);
        lenient().when(routeCache.findById(any(UUID.class))).thenReturn(getTestRouteDto());
    }

    private UserEntity getTestUserEntity() {
        return UserEntity.builder().build();
    }

    private TicketEntity getTestTicketEntity() {
        return TicketEntity.builder()
                .id(1)
                .passengerEntity(getTestPassengerEntity())
                .build();
    }

    private PassengerEntity getTestPassengerEntity() {
        return PassengerEntity.builder()
                .firstName("test")
                .secondName("test")
                .birthDate(LocalDate.now().minusYears(20))
                .build();
    }

    private ScheduleSectionEntity getTestScheduleSectionEntity() {
        return ScheduleSectionEntity.builder().build();
    }

    private TicketDto getTestTicketDto() {
        return TicketDto.builder()
                .id(1)
                .passengerDto(getTestPassengerDto())
                .ticketScheduleSectionDtoList(Collections.singletonList(TicketScheduleSectionDto.builder()
                        .scheduleSectionId(1)
                        .departureTime(LocalTime.now())
                        .departureDate(LocalDate.now())
                        .build()))
                .build();
    }

    private PassengerDto getTestPassengerDto() {
        return PassengerDto.builder()
                .firstName("test")
                .secondName("test")
                .birthDate(LocalDate.now().minusYears(20))
                .build();
    }

    private RouteDto getTestRouteDto() {
        return RouteDto.builder()
                .ticketsAvailable(10)
                .departureTime(ZonedDateTime.now())
                .routePartDtoList(Collections.singletonList(RoutePartDto.builder()
                        .departureTime(ZonedDateTime.now())
                        .arrivalTime(ZonedDateTime.now().plusDays(1))
                        .scheduleSectionDtoList(Collections.singletonList(ScheduleSectionDto.builder()
                                .id(1)
                                .sectionDto(SectionDto.builder()
                                        .length(20)
                                        .build())
                                .trainDto(TrainDto.builder()
                                        .type(TrainType.COMMON)
                                        .build())
                                .build()))
                        .build()))
                .build();
    }

    private PassengerFormDto getTestPassengerForm() {
        return PassengerFormDto.builder()
                .id(UUID.randomUUID())
                .userLogin("user")
                .firstName("test")
                .secondName("test")
                .birthDate(LocalDate.now().minusYears(20))
                .build();
    }

    @Test
    public void testGetByUserLogin() {
        when(userRepository.findByLogin(anyString())).thenReturn(getTestUserEntity());
        when(ticketRepository.findByUser(any(UserEntity.class))).thenReturn(Collections.singletonList(getTestTicketEntity()));
        when(ticketMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestTicketDto()));
        List<TicketDto> result = ticketService.getByUserLogin(anyString());
        assertEquals(1, result.size());
        TicketDto dto = result.get(0);
        assertEquals(getTestTicketEntity().getId(), dto.getId());
    }

//    TODO: Different result in github CI
//    
//    @Test
//    public void testHasPassenger() {
//        when(ticketRepository.findByPassengerNameAndBirthDate(anyString(), anyString(), any(LocalDate.class)))
//                .thenReturn(Collections.singletonList(getTestTicketEntity()));
//        when(ticketMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(getTestTicketDto()));
//        boolean result = ticketService.hasPassenger(getTestPassengerForm());
//        assertFalse(result); //passenger wasn't created
//    }

    @Test
    public void testIfTimeLeft() {
        boolean result = ticketService.isTimeLeft(getTestPassengerForm());
        assertFalse(result); //train departs now
    }

    @Test
    public void testIfTicketsAvailable() {
        boolean result = ticketService.areTicketsAvailable(getTestPassengerForm());
        assertTrue(result); //10 tickets are available
    }

    @Test
    public void testBuyTicket() {
        when(passengerRepository.findByNameAndBirthDate(anyString(), anyString(), any(LocalDate.class))).thenReturn(null);
        when(passengerMapper.toEntity(any(PassengerFormDto.class))).thenReturn(getTestPassengerEntity());
        when(userRepository.findByLogin(anyString())).thenReturn(getTestUserEntity());
        when(passengerMapper.toDto(any(PassengerEntity.class))).thenReturn(getTestPassengerDto());
        when(ticketMapper.toEntity(any(TicketDto.class))).thenReturn(getTestTicketEntity());
        when(scheduleSectionRepository.findById(anyInt())).thenReturn(getTestScheduleSectionEntity());
        ticketService.buyTicket(getTestPassengerForm());
        verify(passengerRepository, atMostOnce()).add(any(PassengerEntity.class));
        verify(ticketRepository, only()).add(any(TicketEntity.class));
        verify(ticketScheduleSectionRepository, atLeastOnce()).add(any(TicketScheduleSectionEntity.class));
    }

}
