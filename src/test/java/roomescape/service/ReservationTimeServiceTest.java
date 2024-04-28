package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.controller.dto.request.ReservationTimeCreateRequest;
import roomescape.controller.dto.response.ReservationTimeResponse;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservationTime.sql")
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @DisplayName("예약시간을 생성한다.")
    @Test
    void createReservatonTime() {
        // given
        LocalTime savedTime = LocalTime.of(10, 0);
        ReservationTimeCreateRequest reservationTimeCreateRequest = new ReservationTimeCreateRequest(savedTime);

        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.createReservationTime(reservationTimeCreateRequest);

        // then
        assertAll(
                () -> assertThat(reservationTimeResponse.id()).isEqualTo(2L),
                () -> assertThat(reservationTimeResponse.startAt()).isEqualTo(savedTime)
        );
    }

    @DisplayName("저장되어 있는 예약시간을 전부 가져온다.")
    @Test
    void getReservationTimes() {
        // given & when
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.getReservationTimes();

        // then
        ReservationTimeResponse reservationTimeResponse = reservationTimeResponses.get(0);
        assertAll(
                () -> assertThat(reservationTimeResponses).hasSize(1),
                () -> assertThat(reservationTimeResponse.id()).isEqualTo(1L),
                () -> assertThat(reservationTimeResponse.startAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        assertThatCode(() -> reservationTimeService.delete(1L))
                .doesNotThrowAnyException();
    }
}
