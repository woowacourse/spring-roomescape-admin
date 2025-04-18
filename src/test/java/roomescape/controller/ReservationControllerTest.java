package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import roomescape.Reservation;
import roomescape.ReservationRequest;

public class ReservationControllerTest {

    @Test
    @DisplayName("예약을 추가할 수 있다.")
    void addReservation() {
        //given
        final var controller = new ReservationController();
        final var request = new ReservationRequest(
            "포포",
            LocalDate.of(2024, 4, 18),
            LocalTime.of(12, 0)
        );

        //when
        final var addResponse = controller.addReservation(request);

        //then
        final var reservations = controller.getReservations();
        assertAll(
            () -> assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(reservations.getBody()).hasSize(1)
        );
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다.")
    void deleteReservation() {
        //given
        final var controller = new ReservationController();
        final var addedReservation = addOneReservation(controller);

        //when
        final var deleteResponse = controller.deleteReservation(addedReservation.id());

        //then
        final var reservations = controller.getReservations();
        assertAll(
            () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(reservations.getBody()).isEmpty()
        );
    }

    @ParameterizedTest
    @MethodSource("parametersThatAnyOneIsNull")
    @DisplayName("예약 추가 시 이름, 날짜, 시간 중 하나라도 없으면 400 Bad Request")
    void badRequestAnyParameterNull(ReservationRequest request) {
        //given
        final var controller = new ReservationController();

        //when
        final var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 추가 시 이름이 잘못된 형식이면 400 Bad Request")
    void badRequestAnyParameterInvalid() {
        //given
        final var controller = new ReservationController();
        final var request = new ReservationRequest(
            "여섯글자이름",
            LocalDate.of(2023, 8, 5),
            LocalTime.of(15, 40)
        );

        //when
        final var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 삭제 시 존재하지 않는 Id를 삭제하면 204 No Content")
    void noContentDeleteNotExistId() {
        //given
        final var controller = new ReservationController();

        //when
        final var responseEntity = controller.deleteReservation(5L);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    public static Stream<Arguments> parametersThatAnyOneIsNull() {
        return Stream.of(
            Arguments.of(new ReservationRequest("브라운", LocalDate.of(2023, 8, 5), null)),
            Arguments.of(new ReservationRequest("브라운", null, LocalTime.of(15, 40))),
            Arguments.of(new ReservationRequest(null, LocalDate.of(2023, 8, 5), LocalTime.of(15, 40)))
        );
    }

    private Reservation addOneReservation(final ReservationController controller) {
        final var request = new ReservationRequest(
            "포포",
            LocalDate.of(2024, 4, 18),
            LocalTime.of(12, 0)
        );
        return controller.addReservation(request).getBody();
    }
}
