package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.fake.FakeReservationTimeDao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
public class ReservationTimeServiceTest {
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());
    }

    @DisplayName("모든 예약 시간 조회 테스트")
    @Test
    void findAllTest() {
        // given
        ReservationTime reservationTime1 = reservationTimeService.save(new ReservationTimeRequest("11:00"));
        ReservationTime reservationTime2 = reservationTimeService.save(new ReservationTimeRequest("12:00"));

        // when & then
        assertThat(reservationTimeService.findAll())
                .containsExactly(reservationTime1, reservationTime2);
    }

    @DisplayName("예약 시간 조회 테스트")
    @Test
    void findByIdTest() {
        // given
        ReservationTime reservationTime = reservationTimeService.save(new ReservationTimeRequest("11:00"));

        // when & then
        assertThat(reservationTimeService.findById(reservationTime.getId()))
                .isEqualTo(reservationTime);
    }

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given & when
        ReservationTime reservationTime = reservationTimeService.save(new ReservationTimeRequest("11:00"));

        // then
        assertThat(reservationTimeService.findById(reservationTime.getId()))
                .isEqualTo(reservationTime);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        ReservationTime reservationTime1 = reservationTimeService.save(new ReservationTimeRequest("11:00"));
        ReservationTime reservationTime2 = reservationTimeService.save(new ReservationTimeRequest("12:00"));

        // when
        reservationTimeService.deleteById(reservationTime1.getId());

        // then
        assertThatThrownBy(() -> reservationTimeService.findById(reservationTime1.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 시간 id입니다.");
    }
}
