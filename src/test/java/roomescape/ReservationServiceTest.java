package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import roomescape.dao.ReservationDaoInterface;
import roomescape.dao.ReservationTimeDaoInterface;
import roomescape.entity.ReservationTime;
import roomescape.entity.ReservationWithTimeId;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

class ReservationServiceTest {

    @Test
    void 예약_추가_성공() {
        // given
        FakeReservationDaoDao reservationDao = new FakeReservationDaoDao();
        FakeReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();

        ReservationService reservationService = new ReservationService(reservationDao, reservationTimeDao);

        ReservationWithTimeId request = new ReservationWithTimeId(null, "홍길동", LocalDate.of(2025, 5, 1), 1L);

        // when
        Reservation reservation = reservationService.addReservation(request);

        // then
        assertEquals("홍길동", reservation.getName());
        assertEquals(LocalDate.of(2025, 5, 1), reservation.getDate());
        assertThat(reservation.getTime().getTime().toString()).isEqualTo("10:00");
    }

    @Test
    void 예약_추가_실패_시간이_없으면_예외발생() {
        // given
        FakeReservationDaoDao reservationDao = new FakeReservationDaoDao();
        FakeReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();

        ReservationService reservationService = new ReservationService(reservationDao, reservationTimeDao);

        ReservationWithTimeId request = new ReservationWithTimeId(null, "홍길동", LocalDate.of(2025, 5, 1), 999L);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(request);
        });

        assertTrue(exception.getMessage().contains("예약 시간 찾기 실패"));
    }

    @Test
    void 예약_삭제_성공() {
        // given
        FakeReservationDaoDao reservationDao = new FakeReservationDaoDao();
        FakeReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();

        ReservationService reservationService = new ReservationService(reservationDao, reservationTimeDao);

        // when
        int affectedRow = reservationService.deleteReservationById(1L);

        // then
        assertThat(affectedRow).isEqualTo(1);
    }

    static class FakeReservationDaoDao implements ReservationDaoInterface {
        private long nextId = 1L;
        private List<Reservation> reservations = new ArrayList<>();

        @Override
        public List<Reservation> selectAllReservation() {
            return reservations;
        }

        @Override
        public Long addReservation(ReservationWithTimeId reservationWithTimeId) {
            return nextId++;
        }

        @Override
        public int deleteReservationById(Long id) {
            return 1;
        }
    }

    static class FakeReservationTimeDao implements ReservationTimeDaoInterface {

        @Override
        public ReservationTime addReservation(ReservationTime reservationTime) {
            return null;
        }

        @Override
        public List<ReservationTime> getTimeReservations() {
            return List.of();
        }

        @Override
        public int deleteTimeReservation(Long id) {
            return 0;
        }

        @Override
        public ReservationTime findTimeById(Long id) {
            if (id.equals(1L)) {
                return new ReservationTime(1L, LocalTime.parse("10:00"));
            }
            throw new org.springframework.dao.EmptyResultDataAccessException("예약 시간 찾기 실패", 1);
        }
    }
}
