package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.fake.FakeReservationDao;
import roomescape.fake.FakeReservationTimeDao;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationServiceTest {
    private ReservationService reservationService;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao());
        reservationService = new ReservationService(new FakeReservationDao(), reservationTimeService);
    }

    @DisplayName("모든 예약 조회 테스트")
    @Test
    void findAllTest() {
        // given
        ReservationTime reservationTime = reservationTimeService.save(new ReservationTimeRequest("11:00"));
        Reservation reservation1 =
                reservationService.save(new ReservationRequest("nak", "2024-06-22", reservationTime.getId()));
        Reservation reservation2 =
                reservationService.save(new ReservationRequest("hon", "2024-06-10", reservationTime.getId()));

        // when & then
        assertThat(reservationService.findAll())
                .containsExactly(reservation1, reservation2);
    }

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given
        ReservationTime reservationTime = reservationTimeService.save(new ReservationTimeRequest("11:00"));

        // when
        Reservation reservation =
                reservationService.save(new ReservationRequest("nak", "2024-06-22", reservationTime.getId()));

        // then
        assertThat(reservationService.findAll())
                .containsExactly(reservation);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        ReservationTime reservationTime = reservationTimeService.save(new ReservationTimeRequest("11:00"));
        Reservation reservation =
                reservationService.save(new ReservationRequest("nak", "2024-06-22", reservationTime.getId()));

        // when
        reservationService.deleteById(reservation.getId());

        // then
        assertThat(reservationService.findAll())
                .isEmpty();
    }
}
