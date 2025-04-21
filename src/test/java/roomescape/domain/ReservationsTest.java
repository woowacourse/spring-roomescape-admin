//package roomescape.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.time.Clock;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class ReservationsTest {
//
/// /    private final Reservations reservations = new Reservations();
//    private final Clock clock = Clock.fixed(Instant.parse("2025-04-20T10:00:00Z"), ZoneId.systemDefault());
//
//    @DisplayName("예약을 조회한다.")
//    @Test
//    void getTest() {
//
//        // given
//
//        // when
//
//        // then
//        assertThat(reservations.findAll().size()).isEqualTo(0);
//    }
//
//    @DisplayName("예약을 추가한다.")
//    @Test
//    void addTest() {
//
//        // given
//        final LocalDate date = LocalDate.of(2025, 4, 21);
//        final LocalTime time = LocalTime.of(10, 0);
//
//        // when
//        reservations.add("체체", date, time, clock);
//
//        // then
//        assertThat(reservations.findAll().size()).isEqualTo(1);
//    }
//
//    @DisplayName("예약을 삭제한다.")
//    @Test
//    void deleteTest() {
//
//        // given
//        final LocalDate date = LocalDate.of(2025, 4, 21);
//        final LocalTime time = LocalTime.of(10, 0);
//        reservations.add("체체", date, time, clock);
//        List<Reservation> reservations = this.reservations.findAll();
//        Reservation findReservation = reservations.getFirst();
//
//        // when
//        this.reservations.remove(findReservation.getId());
//
//        // then
//        assertThat(this.reservations.findAll().size()).isEqualTo(0);
//    }
//
//}
