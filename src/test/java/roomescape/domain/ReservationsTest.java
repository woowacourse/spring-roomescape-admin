package roomescape.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    static String NAME = "브라운";
    static String DATE = "2023-08-05";
    static String TIME = "15:40";

    @DisplayName("방 탈출 예약 저장")
    @Test
    void reservation_save_test() {
        Reservations reservations = new Reservations();

        assertDoesNotThrow(() -> reservations.save(NAME, DATE, TIME));
    }

    @DisplayName("모든 방 탈출 예약 조회")
    @Test
    void reservation_retrieve_test() {
        Reservations reservations = new Reservations();
        reservations.save(NAME, DATE, TIME);

        Map<Long, Reservation> foundReservations = reservations.findAll();
        assertThat(foundReservations).containsKey(1L);
    }
}
