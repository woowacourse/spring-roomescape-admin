package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationServiceResponse;
import roomescape.service.dto.ReservationTimeServiceResponse;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("예약 시간이 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createNoneTime() {
        var reservation = new ReservationServiceRequest("pobi", LocalDate.parse("2020-10-10"), 1L);

        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시간 ID에 해당하는 시간으로 예약을 생성한다.")
    @Test
    void create() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ( ? )", "10:10");
        var reservation = new ReservationServiceRequest("pobi", LocalDate.parse("2020-10-10"), 1L);

        // when
        var reservationServiceResponse = reservationService.create(reservation);

        // then
        assertThat(reservationServiceResponse).isEqualTo(new ReservationServiceResponse(
                1L,
                "pobi",
                LocalDate.parse("2020-10-10"),
                new ReservationTimeServiceResponse(1L, LocalTime.parse("10:10")))
        );
    }
}
