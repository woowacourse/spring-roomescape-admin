package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.test.fixture.ReservationFixture.addReservationInRepository;
import static roomescape.test.fixture.ReservationTimeFixture.addReservationTimeInRepository;
import static roomescape.test.utility.HttpResponseTestUtility.checkLocationHeader;
import static roomescape.test.utility.HttpResponseTestUtility.checkStatusCode;
import static roomescape.test.utility.ReservationTimeTestUtility.checkDeleteReservationTime;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeFieldWithoutId;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeService;
import roomescape.test.fake.FakeReservationRepository;
import roomescape.test.fake.FakeReservationTimeRepository;

class ReservationTimeControllerTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationTimeRepository timeRepository = new FakeReservationTimeRepository();
    private final ReservationTimeService timeService =
            new ReservationTimeService(reservationRepository, timeRepository);
    private final ReservationTimeController controller = new ReservationTimeController(timeService);

    @DisplayName("등록된 모든 예약 가능 시간을 조회활 수 있다")
    @Test
    void canGetReservationTimes() {
        addReservationTimeInRepository(timeRepository, LocalTime.of(10, 0));
        addReservationTimeInRepository(timeRepository, LocalTime.of(11, 0));
        addReservationTimeInRepository(timeRepository, LocalTime.of(12, 0));

        List<ReservationTime> responseBody = controller.getReservationTimes();

        assertThat(responseBody).hasSize(3);
    }

    @DisplayName("예약 가능 시간을 추가할 수 있다")
    @Test
    void canCreateReservationTime() {
        ReservationTimeCreationRequest request = new ReservationTimeCreationRequest(LocalTime.of(10, 0));

        ResponseEntity<ReservationTime> response = controller.createReservationTime(request);

        ReservationTime savedReservationTime = timeRepository.findAll().getFirst();
        assertAll(
                () -> checkReservationTimeId(savedReservationTime.getId(), 1L),
                () -> checkReservationTimeFieldWithoutId(savedReservationTime, request),
                () -> checkStatusCode(response, HttpStatus.CREATED),
                () -> checkLocationHeader(response, "times/1"),
                () -> checkReservationTimeId(response.getBody().getId(), 1L),
                () -> checkReservationTimeFieldWithoutId(response.getBody(), request)
        );
    }

    @DisplayName("이미 추가한 시간의 경우 추가할 수 없다")
    @Test
    void canCreateSameReservationTime() {
        LocalTime sameStartAt = LocalTime.of(10, 0);
        addReservationTimeInRepository(timeRepository, sameStartAt);
        ReservationTimeCreationRequest request = new ReservationTimeCreationRequest(sameStartAt);

        ResponseEntity<ReservationTime> response = controller.createReservationTime(request);

        List<ReservationTime> reservationTimes = timeRepository.findAll();
        assertAll(
                () -> assertThat(reservationTimes).hasSize(1),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }

    @DisplayName("ID를 통해 예약 가능 시간을 삭제할 수 있다")
    @Test
    void canDeleteReservationTime() {
        addReservationTimeInRepository(timeRepository, LocalTime.of(10, 0));
        addReservationTimeInRepository(timeRepository, LocalTime.of(11, 0));
        addReservationTimeInRepository(timeRepository, LocalTime.of(12, 0));

        ResponseEntity<Void> response = controller.deleteReservationTime(1L);

        List<ReservationTime> reservationTimes = timeRepository.findAll();
        assertAll(
                () -> checkDeleteReservationTime(reservationTimes, 1L),
                () -> checkStatusCode(response, HttpStatus.OK)
        );
    }

    @DisplayName("존재하지 않는 예약 가능 시간을 삭제하려고 할 경우 예외 응답을 보낸다")
    @Test
    void canNotDeleteWithInvalidId() {
        long noneExistentReservationId = 1L;
        ResponseEntity<Void> response = controller.deleteReservationTime(noneExistentReservationId);

        checkStatusCode(response, HttpStatus.NOT_FOUND);
    }

    @DisplayName("이미 해당 시간에 예약이 존재하는 경우 예약을 제거할 수 없습니다.")
    @Test
    void canNotDeleteBecauseReservations() {
        ReservationTime savedTime = addReservationTimeInRepository(timeRepository, LocalTime.of(10, 0));
        addReservationInRepository(reservationRepository, LocalDate.now().plusDays(1), savedTime);

        ResponseEntity<Void> response = controller.deleteReservationTime(savedTime.getId());

        List<ReservationTime> reservationTimes = timeRepository.findAll();
        assertAll(
                () -> assertThat(reservationTimes).hasSize(1),
                () -> checkStatusCode(response, HttpStatus.BAD_REQUEST)
        );
    }
}