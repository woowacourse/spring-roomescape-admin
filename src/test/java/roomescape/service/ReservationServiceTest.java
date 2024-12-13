package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;
import roomescape.service.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationServiceTest {

    private static final ReservationRequest RESERVATION_REQUEST_A = new ReservationRequest("userA",
            LocalDate.of(2100, 1, 1), 1L);
    private static final ReservationRequest RESERVATION_REQUEST_B = new ReservationRequest("userB",
            LocalDate.of(2100, 1, 2), 1L);
    private static final ReservationTimeResponse RESERVATION_TIME_RESPONSE = new ReservationTimeResponse(1L, "10:00");
    private static final ReservationResponse RESERVATION_RESPONSE_A = new ReservationResponse(4L, "userA",
            LocalDate.of(2100, 1, 1), RESERVATION_TIME_RESPONSE);
    private static final ReservationResponse RESERVATION_RESPONSE_B = new ReservationResponse(4L, "userB",
            LocalDate.of(2100, 1, 2), RESERVATION_TIME_RESPONSE);

    @Autowired
    private ReservationService reservationService;

    @Test
    void create() {
        // when
        ReservationResponse result = reservationService.create(RESERVATION_REQUEST_A);

        // then
        assertThat(result).isEqualTo(RESERVATION_RESPONSE_A);
    }

    @Test
    void findOne() {
        // given
        ReservationResponse expected = reservationService.create(RESERVATION_REQUEST_A);
        long id = expected.id();

        // when
        ReservationResponse result = reservationService.findOne(id);

        // then
        assertThat(result).isEqualTo(RESERVATION_RESPONSE_A);
    }

    @Test
    void findAll() {
        // when
        List<ReservationResponse> result = reservationService.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove() {
        // given
        ReservationResponse savedResponse = reservationService.create(RESERVATION_REQUEST_A);

        // when
        reservationService.remove(savedResponse.id());

        // then
        assertThatThrownBy(() -> reservationService.findOne(savedResponse.id()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
