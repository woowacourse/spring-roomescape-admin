package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.util.Fixture.GAMJA_RESERVATION_BEFORE_SAVE;
import static roomescape.util.Fixture.GAMJA_RESERVATION_RESPONSE;
import static roomescape.util.Fixture.GAMJA_RESERVATION_TIME_BEFORE_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_BEFORE_SAVE;
import static roomescape.util.Fixture.JOJO_RESERVATION_REQUEST;
import static roomescape.util.Fixture.JOJO_RESERVATION_RESPONSE;
import static roomescape.util.Fixture.JOJO_RESERVATION_TIME_BEFORE_SAVE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("예약 목록 조회")
    @Test
    void findAllReservation() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE);
        reservationTimeDao.save(GAMJA_RESERVATION_TIME_BEFORE_SAVE);
        reservationDao.save(JOJO_RESERVATION_BEFORE_SAVE);
        reservationDao.save(GAMJA_RESERVATION_BEFORE_SAVE);

        List<ReservationResponse> reservations = reservationService.findAll();

        assertThat(reservations).containsExactly(
            JOJO_RESERVATION_RESPONSE,
            GAMJA_RESERVATION_RESPONSE
        );
    }

    @DisplayName("예약 추가")
    @Test
    void createReservation() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE);

        ReservationResponse reservationResponse = reservationService.create(
            JOJO_RESERVATION_REQUEST);

        assertThat(reservationResponse).isEqualTo(JOJO_RESERVATION_RESPONSE);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        reservationTimeDao.save(JOJO_RESERVATION_TIME_BEFORE_SAVE);
        Long savedId = reservationDao.save(JOJO_RESERVATION_BEFORE_SAVE)
            .getId();

        assertThatCode(() -> reservationService.delete(savedId))
            .doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 예약 삭제 시 예외 발생")
    @Test
    void throwExceptionIfReservationNotFound() {
        assertThatThrownBy(() -> reservationService.delete(1L))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
