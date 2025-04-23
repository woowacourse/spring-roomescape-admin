package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import roomescape.dto.CreateReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationFakeRepository;

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
        var request = createReservationRequest();

        //when
        var responseEntity = controller.addReservation(request);

        //then
        var reservationList = controller.getReservations().getBody();
        assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(OK),
            () -> assertThat(reservationList).hasSize(1)
        );
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다.")
    void deleteReservation() {
        //given
        var addedReservation = addOneReservation(controller);

        //when
        var responseEntity = controller.deleteReservation(addedReservation.id());

        //then
        var reservationList = controller.getReservations().getBody();
        assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(NO_CONTENT),
            () -> assertThat(reservationList).isEmpty()
        );
    }

    @ParameterizedTest
    @MethodSource("parametersThatAnyOneIsNull")
    @DisplayName("예약 추가 시 이름, 날짜, 시간 중 하나라도 없으면 400 Bad Request")
    void badRequestAnyParameterNull(CreateReservationRequest request) {
        //when
        var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 추가 시 이름이 잘못된 형식이면 400 Bad Request")
    void badRequestAnyParameterInvalid() {
        //given
        var request = new CreateReservationRequest(
            "여섯글자이름",
            LocalDate.of(2023, 8, 5),
            timeSlotId
        );

        //when
        var responseEntity = controller.addReservation(request);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("예약 삭제 시 존재하지 않는 Id를 삭제하면 404 NOT FOUND")
    void noContentDeleteNotExistId() {
        //when
        var responseEntity = controller.deleteReservation(5L);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    public static Stream<Arguments> parametersThatAnyOneIsNull() {
        return Stream.of(
            Arguments.of(new CreateReservationRequest("브라운", LocalDate.of(2023, 8, 5), null)),
            Arguments.of(new CreateReservationRequest("브라운", null, timeSlotId)),
            Arguments.of(new CreateReservationRequest(null, LocalDate.of(2023, 8, 5), timeSlotId))
        );
    }

    private CreateReservationRequest createReservationRequest() {
        return new CreateReservationRequest(
            "포포",
            LocalDate.of(2024, 4, 18),
            timeSlotId
        );
    }

    private Reservation addOneReservation(final ReservationController controller) {
        var request = createReservationRequest();
        return controller.addReservation(request).getBody();
    }
}
