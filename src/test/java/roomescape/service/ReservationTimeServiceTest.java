package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.fixture.Fixture;
import roomescape.repository.ReservationTimeRepository;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    private ReservationTimeService reservationTimeService;

    @Test
    void getAllReservationTimes() {
        // given
        when(reservationTimeRepository.findAll()).thenReturn(List.of(Fixture.RESERVATION_TIME_1));

        // when
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.getAllReservationTimes();

        // then
        assertThat(reservationTimes).containsExactly(ReservationTimeResponse.from(Fixture.RESERVATION_TIME_1));
    }

    @Test
    void getReservationTimeByIdOrElseThrow() {
        // given
        Long id = 1L;
        ReservationTime reservationTime = new ReservationTime(id, LocalTime.of(10, 30));
        when(reservationTimeRepository.findById(id)).thenReturn(
                Optional.of(reservationTime));

        // when
        ReservationTime savedReservationTime = reservationTimeService.getReservationTimeByIdOrElseThrow(id);

        // then
        assertThat(reservationTime).isEqualTo(savedReservationTime);
    }

    @Test
    void addReservationTime() {
        // given
        ReservationTime reservationTime = Fixture.reservationTime(1L, 10, 30);
        when(reservationTimeRepository.save(any())).thenReturn(reservationTime);

        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.addReservationTime(
                new ReservationTimeRequest(LocalTime.of(10, 30)));

        // then
        assertThat(reservationTimeResponse).isEqualTo(ReservationTimeResponse.from(reservationTime));
    }

    @Test
    void deleteReservationTimeById() {
        // given
        Long id = 1L;

        // when
        reservationTimeService.deleteReservationTimeById(id);

        // then
        verify(reservationTimeRepository).deleteById(id);
    }
}
