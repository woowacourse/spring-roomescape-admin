package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import roomescape.repository.ReservationFakeRepository;
import roomescape.model.Reservation;
import roomescape.dto.CreateReservationRequest;

public class ReservationControllerTest {

    private static final long timeSlotId = ReservationFakeRepository.FIXED_TIME_SLOT.id();

    private ReservationController controller;

    @BeforeEach
    void setUp() {
        controller = new ReservationController(new ReservationFakeRepository());
    }

    @Test
    @DisplayName("예약을 추가할 수 있다.")
    void addReservation() {
        //given
        final var request = new CreateReservationRequest(
            "포포",
            LocalDate.of(2024, 4, 18),
            timeSlotId
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
    void badRequestAnyParameterNull(CreateReservationRequest request) {
        //when
        final var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 추가 시 이름이 잘못된 형식이면 400 Bad Request")
    void badRequestAnyParameterInvalid() {
        //given
        final var request = new CreateReservationRequest(
            "여섯글자이름",
            LocalDate.of(2023, 8, 5),
            timeSlotId
        );

        //when
        final var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 삭제 시 존재하지 않는 Id를 삭제하면 204 No Content")
    void noContentDeleteNotExistId() {
        //when
        final var responseEntity = controller.deleteReservation(5L);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    public static Stream<Arguments> parametersThatAnyOneIsNull() {
        return Stream.of(
            Arguments.of(new CreateReservationRequest("브라운", LocalDate.of(2023, 8, 5), null)),
            Arguments.of(new CreateReservationRequest("브라운", null, timeSlotId)),
            Arguments.of(new CreateReservationRequest(null, LocalDate.of(2023, 8, 5), timeSlotId))
        );
    }

    private Reservation addOneReservation(final ReservationController controller) {
        final var request = new CreateReservationRequest(
            "포포",
            LocalDate.of(2024, 4, 18),
            timeSlotId
        );
        return controller.addReservation(request).getBody();
    }
}
