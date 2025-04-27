package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.exception.ReservationTimeNotFoundException;
import roomescape.repository.ReservationTimeDao;

@JdbcTest
@Import({ReservationTimeDao.class, ReservationTimeService.class})
class ReservationTimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationTimeService reservationTimeService;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation_time");

        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("예약 시간 생성 요청이 들어오면 예약 시간을 생성한다.")
    @Test
    void createReservationTime() {
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        ReservationTimeResponse response = reservationTimeService.createReservationTime(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("예약 시간 생성 요청에 빈 값이 들어오면 예약 시간을 생성할 수 없다.")
    @Test
    void createReservationTimeWithBadRequest() {
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(null);

        assertThatThrownBy(() -> reservationTimeService.createReservationTime(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시간 번호로 예약 시간을 삭제한다.")
    @Test
    void deleteReservationTimeById() {
        ReservationTimeResponse reservationTime = reservationTimeService.createReservationTime(
                new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        reservationTimeService.deleteReservationTime(reservationTime.id());

        assertThat(reservationTimeService.findAllReservationTimes()).isEmpty();
    }

    @DisplayName("존재하지 않는 예약 시간은 삭제할 수 없다.")
    @Test
    void deleteReservationTimeByNonExistsId() {
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(1L))
                .isInstanceOf(ReservationTimeNotFoundException.class)
                .hasMessage("존재하지 않는 예약 시간 번호입니다.");
    }

    @DisplayName("모든 예약 시간을 조회한다.")
    @Test
    void findAllReservationTimes() {
        reservationTimeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAllReservationTimes();

        assertThat(reservationTimeResponses).hasSize(1);
    }
}
