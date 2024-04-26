package roomescape.reservationtime.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.reservationtime.controller.ReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    @DisplayName("시간 레포지토리를 통해 시간목록을 불러온다.")
    void given_ReservationTimeService_when_getReservationTimes_then_findAllCalled() {
        //given
        given(reservationTimeRepository.findAll()).willReturn(List.of());
        //when
        reservationTimeService.getReservationTimes();
        //then
        verify(reservationTimeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("시간 레포지토리를 통해 시간을 저장한다.")
    void given_ReservationTimeService_when_saveReservationTime_then_saveCalled() {
        //given
        LocalTime startAt = LocalTime.parse("10:00");
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(startAt);
        //when
        reservationTimeService.saveReservationTime(reservationTimeRequest);
        //then
        verify(reservationTimeRepository, times(1)).save(startAt);
    }

    @Test
    @DisplayName("시간 레포지토리를 통해 시간을 삭제한다.")
    void given_ReservationTimeService_when_deleteReservationTime_then_deleteByIdCalled() {
        //given
        Long timeId = 1L;
        //when
        reservationTimeService.deleteReservationTime(timeId);
        //then
        verify(reservationTimeRepository, times(1)).deleteById(timeId);
    }
}