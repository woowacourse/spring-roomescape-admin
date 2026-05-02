package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TestReservationDao;
import roomescape.fixture.TestReservationTimeDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class ReservationServiceTest {

    private static final LocalTime time = LocalTime.of(10, 0);
    private static final LocalDate date = LocalDate.of(2026, 5, 3);
    private static final String name = "송송";
    private static final ReservationTime reservationTime = new ReservationTime(time);
    private static final Reservation reservation = new Reservation(name, date, reservationTime);

    @Nested
    class ReadAll {

        @Test
        void 등록된_예약이_없으면_빈_리스트를_반환한다() {
            // given
            List<Reservation> emptyReservations = List.of();
            ReservationService service = generateReservationService(emptyReservations);

            // when
            List<Reservation> actual = service.readAll();

            // then
            Assertions.assertThat(actual)
                    .isEmpty();
        }

        @Test
        void 등록된_예약이_여러개이면_조회시_등록된_개수만큼_반환한다() {
            // given
            List<Reservation> reservations = List.of(reservation, reservation);
            ReservationService service = generateReservationService(reservations);

            // when
            List<Reservation> actual = service.readAll();

            // then
            Assertions.assertThat(actual)
                    .hasSize(reservations.size());
        }
    }

    private ReservationService generateReservationService(List<Reservation> reservations) {
        TestReservationDao testReservationDao = new TestReservationDao(reservations);
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(List.of());
        return new ReservationService(testReservationDao, testReservationTimeDao);
    }

    private ReservationService generateReservationService(List<Reservation> reservations, List<ReservationTime> times) {
        TestReservationDao testReservationDao = new TestReservationDao(reservations);
        TestReservationTimeDao testReservationTimeDao = new TestReservationTimeDao(times);
        return new ReservationService(testReservationDao, testReservationTimeDao);
    }

}
