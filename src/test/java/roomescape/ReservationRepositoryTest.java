package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationRepositoryTest {

    @Test
    @DisplayName("예약 조회 시 불변 리스트를 반환한다.")
    void findAll_then_return_unmodifiableList() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository();

        // when
        List<Reservation> result = reservationRepository.findAll();

        // then
        assertThatThrownBy(() -> result.add(new Reservation(1L, "name", LocalDate.now(), LocalTime.now())))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("예약 추가 시 id가 생성되고 Reservation 객체가 생성되어 리스트에 추가된다.")
    void saveReservation() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository();

        // when
        Reservation savedReservation = reservationRepository.save("홍길동", LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));

        // then
        assertThat(reservationRepository.findAll()).hasSize(1);
        assertThat(savedReservation.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("에약 추가 시 id는 1씩 증가한다.")
    void save_then_id_is_incremented_by_1() {
        // given
        ReservationRepository reservationRepository = new ReservationRepository();

        // when
        reservationRepository.save("홍길동", LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));
        reservationRepository.save("김철수", LocalDate.of(2024, 1, 2), LocalTime.of(11, 0));
        List<Reservation> result = reservationRepository.findAll();

        // then
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }
}
