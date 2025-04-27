package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class ReservationTest {

    @DisplayName("같은 아이디를 가진 예약 객체인지 확인합니다.")
    @Test
    void sameIdTest() {
        Person person = new Person("이름");
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Reservation reservation = new Reservation(1, person, LocalDate.of(2025, 12, 25), reservationTime);

        assertAll(
                () -> assertThat(reservation.isSameId(1)).isTrue(),
                () -> assertThat(reservation.isSameId(2)).isFalse()
        );
    }

    @DisplayName("날짜가 비어있는지 확인합니다.")
    @ParameterizedTest
    @NullSource
    void dateNullTest(LocalDate date) {
        assertThatCode(() -> new Reservation(1, new Person("하하"), date, new ReservationTime(LocalTime.of(11, 0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 비어있을 수 없습니다.");
    }

    @DisplayName("날짜가 과거인지 확인합니다.")
    @Test
    void datePastTest() {
        LocalDateTime minTime = LocalDateTime.MIN;
        assertThatCode(() -> new Reservation(1, new Person("하하"), minTime.toLocalDate(),
                new ReservationTime(minTime.toLocalTime())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약은 과거일 수 없습니다.");
    }
}
