package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.fixture.ReservationFixture.addReservationInRepository;
import static roomescape.test.fixture.ReservationTimeFixture.addReservationTimeInRepository;
import static roomescape.test.utility.HttpResponseTestUtility.checkLocationHeader;
import static roomescape.test.utility.HttpResponseTestUtility.checkStatusCode;
import static roomescape.test.utility.ReservationTestUtility.checkDeleteReservation;
import static roomescape.test.utility.ReservationTestUtility.checkReservationFieldWithoutId;
import static roomescape.test.utility.ReservationTestUtility.checkReservationId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;
import roomescape.test.fake.FakeReservationRepository;
import roomescape.test.fake.FakeReservationTimeRepository;

class ReservationControllerTest {

    private static final LocalDate NEXT_DATE = LocalDate.now().plusDays(1);
    private static final LocalTime PAST_TIME = LocalTime.now().minusSeconds(1);

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository timeRepository = new FakeReservationTimeRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository, timeRepository);
    private final ReservationController controller = new ReservationController(reservationService);

    @DisplayName("저장된 예약들을 조회할 수 있다")
    @Test
    void getReservations() {
        ReservationTime reservationTime = addReservationTimeInRepository(timeRepository, LocalTime.now());
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);

        List<Reservation> responseBody = controller.getReservations();

        assertThat(responseBody).hasSize(3);
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void createReservation() {
        ReservationTime reservationTime = addReservationTimeInRepository(timeRepository, LocalTime.now());
        ReservationCreationRequest request = new ReservationCreationRequest(
                "reservation1", NEXT_DATE, reservationTime.getId());

        ResponseEntity<Reservation> response = controller.createReservation(request);

        Reservation newReservation = reservationRepository.findAll().getFirst();
        assertAll(
                () -> checkReservationId(newReservation.getId(), 1L),
                () -> checkReservationFieldWithoutId(newReservation, request),
                () -> checkStatusCode(response, HttpStatus.CREATED),
                () -> checkLocationHeader(response, "reservations/" + newReservation.getId()),
                () -> checkReservationId(response.getBody().getId(), 1L),
                () -> checkReservationFieldWithoutId(response.getBody(), request)
        );
    }


    @DisplayName("과거 날짜와 시간으로는 예약을 추가할 수 없다.")
    @Test
    void canNotCreateReservationWithPastDateTime() {
        ReservationTime pastReservationTime = addReservationTimeInRepository(timeRepository, PAST_TIME);
        ReservationCreationRequest request = new ReservationCreationRequest(
                "reservation", LocalDate.now(), pastReservationTime.getId());

        ResponseEntity<Reservation> response = controller.createReservation(request);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("이미 예약한 날짜와 시간으로는 예약이 불가능하다")
    @Test
    void canNotCreateReservationWithSameDateTime() {
        LocalDate sameDate = NEXT_DATE;
        ReservationTime sameTime = addReservationTimeInRepository(timeRepository, LocalTime.of(10, 0));
        Reservation reservationInTime = addReservationInRepository(reservationRepository, sameDate, sameTime);
        ReservationCreationRequest request =
                new ReservationCreationRequest("reservation1", sameDate, sameTime.getId());

        ResponseEntity<Reservation> response = controller.createReservation(request);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).hasSize(1),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("특정 ID의 예약을 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        ReservationTime reservationTime = addReservationTimeInRepository(timeRepository, LocalTime.now());
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);
        addReservationInRepository(reservationRepository, NEXT_DATE, reservationTime);
        long deleteReservationId = reservationRepository.findAll().getFirst().getId();

        ResponseEntity<Void> response = controller.deleteReservation(deleteReservationId);

        List<Reservation> reservations = reservationRepository.findAll();
        assertAll(
                () -> checkDeleteReservation(reservations, deleteReservationId),
                () -> checkStatusCode(response, HttpStatus.NO_CONTENT)
        );
    }

    @DisplayName("존재하지 않는 예약을 삭제하려고 할 경우 예외 응답을 보낸다.")
    @Test
    void deleteNoneExistentReservation() {
        long noneExistentReservationId = 1L;

        ResponseEntity<Void> response = controller.deleteReservation(noneExistentReservationId);

        checkStatusCode(response, HttpStatus.NOT_FOUND);
    }
}