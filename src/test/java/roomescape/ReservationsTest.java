package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReservationsTest {

    public Reservations createTestReservations() {
        final Reservations reservations = new Reservations();
        reservations.add(new Reservation(1L, "테스트", LocalDate.of(2025, 4, 16), LocalTime.of(12, 44)));

        return reservations;
    }

    @Test
    void 예약_조회_성공() {
        //given, when
        Reservations reservations = createTestReservations();

        //then
        assertThat(reservations.findById(1L)).isEqualTo(new Reservation(1L, "테스트", LocalDate.of(2025, 4, 16), LocalTime.of(12, 44)));
    }

    @Test
    void 예약_조회_실패() {
        //given, when
        Reservations reservations = createTestReservations();

        //then
        assertThatThrownBy(() -> reservations.findById(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }

    @Test
    void 예약_추가_실패() {
        //given, when
        Reservations reservations = createTestReservations();
        final Reservation reservation = new Reservation(1L, "테스트2", LocalDate.of(2026, 4, 16), LocalTime.of(13, 44));

        // then
        assertThatThrownBy(() -> reservations.add(reservation)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 예약_추가_성공() {
        //given, when
        Reservations reservations = createTestReservations();
        reservations.add(new Reservation(2L, "테스트2", LocalDate.of(2026, 4, 16), LocalTime.of(13, 44)));

        // then
        assertThat(reservations.getReservations().size()).isEqualTo(2);
    }

    @Test
    void 예약_삭제_성공() {
        //given, when
        Reservations reservations = createTestReservations();
        reservations.removeById(1L);

        //then
        assertThat(reservations.getReservations().size()).isEqualTo(0);
    }

    @Test
    void 예약_삭제_실패() {
        //given, when
        Reservations reservations = createTestReservations();

        //then
        assertThatThrownBy(() -> reservations.removeById(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }
}
