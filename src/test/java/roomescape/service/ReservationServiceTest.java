package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.controller.dto.request.ReservationCreateRequest;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservation.sql")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @DisplayName("예약을 생성한다.")
    @Test
    void createReservation() {
        // given
        ReservationCreateRequest reservationRequest = new ReservationCreateRequest("브라운", LocalDate.of(2024, 4, 28), 1L);

        // when
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);

        // then
        assertAll(
                () -> assertThat(reservationResponse.name()).isEqualTo("브라운"),
                () -> assertThat(reservationResponse.date()).isEqualTo(LocalDate.of(2024, 4, 28)),
                () -> assertThat(reservationResponse.id()).isEqualTo(2L),
                () -> assertThat(reservationResponse.time().id()).isEqualTo(1L),
                () -> assertThat(reservationResponse.time().startAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("저장되어 있는 예약을 전부 가져온다.")
    @Test
    void getReservations() {
        // given
        List<ReservationResponse> reservationResponses = reservationService.getReservations();

        // when & then
        ReservationResponse reservationResponse = reservationResponses.get(0);
        assertAll(
                () -> assertThat(reservationResponses).hasSize(1),
                () -> assertThat(reservationResponse.id()).isEqualTo(1L),
                () -> assertThat(reservationResponse.name()).isEqualTo("브라운"),
                () -> assertThat(reservationResponse.date()).isEqualTo(LocalDate.of(2024, 4, 26)),
                () -> assertThat(reservationResponse.time().id()).isEqualTo(1L),
                () -> assertThat(reservationResponse.time().startAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        assertThatCode(() -> reservationService.delete(1L))
                .doesNotThrowAnyException();
    }
}
