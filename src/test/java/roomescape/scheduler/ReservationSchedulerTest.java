package roomescape.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationInMemoryDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

class ReservationSchedulerTest {

    private final ReservationDao reservationDao = new ReservationInMemoryDao();
    private final ReservationScheduler reservationScheduler = new ReservationScheduler(reservationDao);

    @BeforeEach
    void setUp() {
        reservationDao.deleteAll();
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void scheduleReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", "15:40");
        // when
        reservationScheduler.scheduleReservation(request);
        List<ReservationEntity> actual = reservationDao.findAll();
        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservationsTest() {
        // given
        List<Reservation> reservations = List.of(
                new Reservation("웨지", LocalDate.parse("2024-04-17"), LocalTime.parse("15:00")),
                new Reservation("아루", LocalDate.parse("2023-04-18"), LocalTime.parse("13:00")),
                new Reservation("브리", LocalDate.parse("2023-04-19"), LocalTime.parse("16:00"))
        );
        reservations.forEach(reservationDao::addReservation);
        // when
        List<ReservationResponse> actual = reservationScheduler.getAllReservations();
        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("취소할 때, id에 해당하는 예약이 존재한다면 성공적으로 취소한다.")
    void cancelReservationTest() {
        // given
        ReservationEntity entity = reservationDao.addReservation(
                new Reservation("웨지", LocalDate.parse("2024-04-17"), LocalTime.parse("15:00")));
        // when
        reservationScheduler.cancelReservation(entity.id());
        List<ReservationEntity> actual = reservationDao.findAll();
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("취소할 때, id에 해당하는 예약이 존재하지 않는다면 예외를 발생한다.")
    void cancelReservationNotFoundTest() {
        assertThatThrownBy(() -> reservationScheduler.cancelReservation(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id에 해당하는 예약을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("모든 예약을 취소한다.")
    void cancelAllReservationsTest() {
        // given
        List<Reservation> reservations = List.of(
                new Reservation("웨지", LocalDate.parse("2024-04-17"), LocalTime.parse("15:00")),
                new Reservation("아루", LocalDate.parse("2023-04-18"), LocalTime.parse("13:00"))
        );
        reservations.forEach(reservationDao::addReservation);
        // when
        reservationScheduler.cancelAllReservations();
        List<ReservationEntity> actual = reservationDao.findAll();
        // then
        assertThat(actual).isEmpty();
    }
}
