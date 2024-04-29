package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_BEFORE_SAVE;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_RESPONSE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_BEFORE_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_REQUEST;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_RESPONSE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/test-schema.sql")
class ReservationTimeServiceIntegrationTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationTimeService reservationTimeService;

    @DisplayName("시간 목록 조회")
    @Test
    void findAllReservationTime() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE);
        reservationTimeDao.save(GAMJA_RESERVATION_TIME_BEFORE_SAVE);

        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();

        assertThat(reservationTimes).containsExactly(
            JOJO_RESERVATION_TIME_RESPONSE,
            GAMJA_RESERVATION_TIME_RESPONSE
        );
    }

    @DisplayName("시간 추가")
    @Test
    void createReservationTime() {
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.create(
            JOJO_RESERVATION_TIME_REQUEST);

        assertThat(reservationTimeResponse).isEqualTo(JOJO_RESERVATION_TIME_RESPONSE);
    }

    @DisplayName("시간 삭제")
    @Test
    void deleteReservationTime() {
        Long savedId = reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE)
            .getId();

        assertThatCode(() -> reservationTimeService.delete(savedId))
            .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 시간 삭제 시 예외 발생")
    @Test
    void throwExceptionIfReservationTimeNotFound() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
