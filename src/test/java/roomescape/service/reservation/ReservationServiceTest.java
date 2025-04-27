package roomescape.service.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.repository.reservation.FakeReservationDao;
import roomescape.repository.time.FakeReservationTimeDao;
import roomescape.service.time.ReservationTimeService;

class ReservationServiceTest {

    private FakeReservationTimeDao reservationTimeDao;
    private FakeReservationDao reservationDao;
    private ReservationService reservationService;

    private static List<Reservation> createReservations(int count) {
        return LongStream.rangeClosed(1, count)
            .mapToObj(id -> new Reservation(id, "r" + id, LocalDate.now(), new ReservationTime(1L, LocalTime.now())))
            .toList();
    }

    @BeforeEach
    void setUp() {
        reservationTimeDao = new FakeReservationTimeDao(new ArrayList<>());
        reservationDao = new FakeReservationDao(new ArrayList<>());
        reservationService = new ReservationService(
            reservationDao,
            new ReservationTimeService(reservationTimeDao)
        );
    }

    @Test
    void 모든_예약_정보를_반환한다() {
        List<Reservation> reservations = createReservations(2);

        reservationDao.addAll(reservations);

        List<ReservationResponse> expected = reservations.stream()
            .map(ReservationResponse::from)
            .toList();
        List<ReservationResponse> response = reservationService.findAll();

        assertThat(response).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 예약을_하나_생성한다() {
        String name = "name";
        LocalDate date = LocalDate.now();
        ReservationTime time = new ReservationTime(1L, LocalTime.now());
        reservationTimeDao.save(time);

        ReservationCreateRequest request = new ReservationCreateRequest(name, date, 1L);

        reservationService.create(request);
        Reservation reservation = new Reservation(1L, name, date, time);

        assertThat(reservationDao.findAll()).containsExactly(reservation);
    }

    @Test
    void 예약을_삭제한다() {
        ReservationTime time = new ReservationTime(1L, LocalTime.now());
        reservationTimeDao.save(time);
        Reservation reservation = new Reservation(1L, "name", LocalDate.now(), time);
        reservationDao.save(reservation);

        reservationService.deleteById(reservation.getId());

        assertThat(reservationDao.findAll()).isEmpty();
    }
}
