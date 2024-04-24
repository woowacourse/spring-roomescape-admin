package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
    @DisplayName("모든 예약 시간들을 조회한다.")
    void getAllReservationTimes() {
        // given
        BDDMockito.given(reservationTimeRepository.findAll())
                .willReturn(List.of(Fixture.RESERVATION_TIME_1));

        // when
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.getAllReservationTimes();

        // then
        assertThat(reservationTimes).containsExactly(ReservationTimeResponse.from(Fixture.RESERVATION_TIME_1));
    }

    @Test
    @DisplayName("예약 시간을 조회한다.")
    void getReservationTimeByIdOrElseThrow() {
        // given
        ReservationTime reservationTime = Fixture.RESERVATION_TIME_1;
        BDDMockito.given(reservationTimeRepository.findById(reservationTime.getId()))
                .willReturn(Optional.of(reservationTime));

        // when
        ReservationTime savedReservationTime = reservationTimeService.getReservationTimeByIdOrElseThrow(
                reservationTime.getId());

        // then
        assertThat(reservationTime).isEqualTo(savedReservationTime);
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void addReservationTime() {
        // given
        ReservationTime reservationTime = Fixture.RESERVATION_TIME_1;
        BDDMockito.given(reservationTimeRepository.save(any()))
                .willReturn(reservationTime);

        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.addReservationTime(
                new ReservationTimeRequest(reservationTime.getStartAt()));

        // then
        assertThat(reservationTimeResponse).isEqualTo(ReservationTimeResponse.from(reservationTime));
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void deleteReservationTimeById() {
        // given
        Long id = 1L;

        // when
        reservationTimeService.deleteReservationTimeById(id);

        // then
        verify(reservationTimeRepository).deleteById(id);
    }
}
