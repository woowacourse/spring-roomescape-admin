package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.request.ReservationAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.reservation.ReservationH2Repository;
import roomescape.repository.reservationtime.ReservationTimeH2Repository;

@JdbcTest
@Import({ReservationH2Repository.class, ReservationTimeH2Repository.class, ReservationService.class})
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약을 추가하고 id값을 붙여서 응답 DTO를 생성한다.")
    void addReservation() {
        ReservationAddRequest reservationAddRequest = new ReservationAddRequest("네오", LocalDate.of(2024, 1, 12), 10L);

        ReservationResponse reservationResponse = reservationService.addReservation(reservationAddRequest);

        assertThat(reservationResponse.id()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 time_id로 예약을 추가하면 예외를 발생시킨다.")
    void addReservationInvalidTimeId() {
        ReservationAddRequest reservationAddRequest = new ReservationAddRequest("네오", LocalDate.of(2024, 1, 12), -1L);

        assertThatThrownBy(() -> reservationService.addReservation(reservationAddRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("id에 맞는 예약을 삭제한다.")
    void deleteReservation() {
        reservationService.deleteReservation(10L);

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation", Integer.class);
        assertThat(count).isOne();
    }

    @Test
    @DisplayName("모든 예약들을 조회한다.")
    void getReservations() {
        List<ReservationResponse> reservations = reservationService.getReservations();

        assertThat(reservations).hasSize(2);
    }
}
