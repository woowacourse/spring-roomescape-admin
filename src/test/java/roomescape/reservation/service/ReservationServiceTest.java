package roomescape.reservation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.reservation.controller.ReservationRequest;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReservationServiceTest {

    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationTimeRepository, reservationRepository);
    }

    @Test
    @DisplayName("예약 레포지토리를 통해 에약 목록을 반환한다.")
    void given_ReservationService_when_getReservation_then_findAllCalled() {
        //given
        given(reservationRepository.findAll()).willReturn(List.of());
        //when
        reservationService.getReservations();
        //then
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("예약 레포지토리를 통해 에약을 저장한 후 시간 레포지토리에서 timeId로 시간을 조회한다.")
    void given_ReservationService_when_saveReservation_then_saveCalled() {
        //given
        Long timeId = 1L;
        ReservationRequest reservationRequest = new ReservationRequest("poke", LocalDate.parse("2024-04-25"), timeId);
        //when
        reservationService.saveReservation(reservationRequest);
        //then
        verify(reservationRepository, times(1))
                .save(reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTimeId());
        verify(reservationTimeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("예약 레포지토리를 통해 에약을 삭제한다.")
    void given_ReservationService_when_deleteReservation_then_deleteByIdCalled() {
        //given
        Long reservationId = 1L;
        //when
        reservationService.deleteReservation(reservationId);
        //then
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

}