package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    private ReservationTime time;

    @BeforeEach
    void setUp() {
        ReservationTime time = new ReservationTime(1L, "10:10");
        this.time=time;
    }

    @Test
    void 예약_Domain_정상테스트() {
        Reservation reservation = new Reservation("브라운", "2021-02-21", time);

        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo("2021-02-21");
        assertThat(reservation.getTime().getId()).isEqualTo(1L);
        assertThat(reservation.getTime().getStartAt()).isEqualTo("10:10");
    }

    @DisplayName("이름은 255자 이하여야한다.")
    @Test
    void 이름_길이_Domain_예외테스트() {
        String longName = "a".repeat(256);
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation(longName, "2021-02-21", time);
        });
    }

    @DisplayName("날짜는 날짜 형식에 맞아야한다.")
    @Test
    void 날짜_형식_Domain_예외테스트() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reservation("브라운", "sajkd", time);
        });
    }
}