package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeFakeDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("예약 시간 서비스")
class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        this.reservationTimeService = new ReservationTimeService(new ReservationTimeFakeDao());
    }

    @DisplayName("에약 시간 서비스는 시간을 생성한다.")
    @Test
    void createTime() {
        // given
        String startAt = "10:00";
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);

        // when
        ReservationTime reservationTime = reservationTimeService.createTime(request);

        // then
        assertThat(reservationTime.getStartAt())
                .isEqualTo("10:00");
    }

    @DisplayName("예약 시간 서비스는 id에 맞는 시간을 반환한다.")
    @Test
    void readReservationTime() {
        // given
        String startAt = createInitReservationTime();
        Long id = 1L;

        // when
        ReservationTime reservationTime = reservationTimeService.readReservationTime(id);

        // then
        assertThat(reservationTime.getStartAt())
                .isEqualTo(startAt);
    }

    @DisplayName("예약 시간 서비스는 시간들을 반환한다.")
    @Test
    void readReservationTimes() {
        // given
        createInitReservationTime();

        // when
        List<ReservationTime> reservationTimes = reservationTimeService.readReservationTimes();

        // then
        System.out.println(reservationTimes);
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

    private String createInitReservationTime() {
        String startAt = "10:00";
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(startAt);
        reservationTimeService.createTime(request);
        return startAt;
    }
}
