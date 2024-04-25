package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.reservationtime.ReservationTimeCreateRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;
import roomescape.repository.ReservationTimeFakeDao;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("예약 시간 서비스")
class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    private LocalTime startAt;

    @BeforeEach
    void setUp() {
        this.reservationTimeService = new ReservationTimeService(new ReservationTimeFakeDao());
        this.startAt = LocalTime.of(10, 10);
    }

    @DisplayName("에약 시간 서비스는 시간을 생성한다.")
    @Test
    void createTime() {
        // given
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);

        // when
        ReservationTimeResponse reservationTime = reservationTimeService.createTime(request);

        // then
        assertThat(reservationTime.startAt())
                .isEqualTo(startAt);
    }

    @DisplayName("예약 시간 서비스는 id에 맞는 시간을 반환한다.")
    @Test
    void readReservationTime() {
        // given
        createInitReservationTime();
        Long id = 1L;

        // when
        ReservationTimeResponse reservationTime = reservationTimeService.readReservationTime(id);

        // then
        assertThat(reservationTime.startAt())
                .isEqualTo(startAt);
    }

    @DisplayName("예약 시간 서비스는 시간들을 반환한다.")
    @Test
    void readReservationTimes() {
        // given
        createInitReservationTime();

        // when
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.readReservationTimes();

        // then
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @DisplayName("예약 시간 서비스는 id에 맞는 시간을 삭제한다.")
    @Test
    void deleteTime() {
        // given
        createInitReservationTime();
        Long id = 1L;

        // when & then
        assertThatCode(() -> reservationTimeService.deleteTime(id))
                .doesNotThrowAnyException();
    }

    private void createInitReservationTime() {
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);
        reservationTimeService.createTime(request);
    }
}
