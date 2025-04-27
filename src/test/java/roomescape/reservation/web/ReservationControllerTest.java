package roomescape.reservation.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.OK;

import fake.FakeReservationTimeDao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import fake.FakeReservationDao;
import roomescape.reservation.dao.ReservationDao;
import roomescape.time.ReservationTime;
import roomescape.time.dao.ReservationTimeDao;

class ReservationControllerTest {
    private ReservationController reservationController;
    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;
    private LocalDate date;
    private LocalTime time;

    @BeforeEach
    public void init() {
        reservationDao = new FakeReservationDao();
        reservationTimeDao = new FakeReservationTimeDao();
        reservationController = new ReservationController(reservationDao, reservationTimeDao);
        date = LocalDate.now();
        time = LocalTime.now();
    }

    @DisplayName("예약을_생성할_수_있다")
    @Test
    void create() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(null, time));
        ReservationRequest request = new ReservationRequest("레오", date, savedReservationTime.getId());

        // when
        ResponseEntity<ReservationResponse> result = reservationController.create(request);
        ReservationResponse response = result.getBody();

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(OK),
                () -> assertThat(response.id()).isOne(),
                () -> assertThat(response.name()).isEqualTo("레오"),
                () -> assertThat(response.date()).isEqualTo(date),
                () -> assertThat(response.time().id()).isEqualTo(savedReservationTime.getId()),
                () -> assertThat(response.time().startAt()).isEqualTo(savedReservationTime.getStartAt())
        );
    }

    @DisplayName("모든_예약_정보를_반환할_수_있다")
    @Test
    void getAll() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(null, time));
        ReservationRequest request = new ReservationRequest("레오", LocalDate.now(), savedReservationTime.getId());
        reservationController.create(request);

        // when
        ResponseEntity<List<ReservationResponse>> result = reservationController.getAll();
        List<ReservationResponse> responses = result.getBody();

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(OK),
                () -> assertThat(responses).hasSize(1)
        );
    }

    @DisplayName("주어진_id의_예약을_삭제할_수_있다")
    @Test
    void delete() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(null, time));
        ReservationRequest request = new ReservationRequest("레오", LocalDate.now(), savedReservationTime.getId());
        ResponseEntity<ReservationResponse> responseEntity = reservationController.create(request);

        // when
        ResponseEntity<Void> result = reservationController.delete(responseEntity.getBody().id());

        // then
        List<ReservationResponse> responses = reservationController.getAll().getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(OK),
                () -> assertThat(responses).hasSize(0)
        );
    }
}
