package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationsTest {

    Reservations reservations;

    @BeforeEach
    void setUp() {
        reservations = new Reservations();
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void save() {
        // when
        Reservation reservation = new Reservation(1, Name.from("훌라"),
                LocalDate.of(2024, 4, 18), LocalTime.of(10, 0));

        //then
        assertThatCode(() -> reservations.save(reservation)).doesNotThrowAnyException();
    }

    @DisplayName("모든 예약을 반환한다.")
    @Test
    void findAll() {
        // when
        List<Reservation> findAllReservations = reservations.findAll();

        //then
        assertThat(findAllReservations).hasSize(0);
    }

    @DisplayName("id를 입력하면 예약을 반환한다.")
    @Test
    void findById() {
        // given
        Reservation reservation = new Reservation(1, Name.from("훌라"),
                LocalDate.of(2024, 4, 18), LocalTime.of(10, 0));
        reservations.save(reservation);

        // when
        Reservation found = reservations.findById(1);

        //then
        assertThat(found.getId()).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 id를 입력하면 예외를 던진다.")
    @Test
    void findById_throw_when_id_not_exists() {
        assertThatThrownBy(() -> reservations.findById(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 ID 입니다.");
    }
}
