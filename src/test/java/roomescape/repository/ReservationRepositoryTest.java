package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Reservation 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservation =
                Reservation.from(null, "조이썬", "2024-10-03",
                        ReservationTime.from("10:00"));
        final var id = reservationRepository.create(reservation, 1);
        assertThat(id).isNotZero()
                      .isNotNegative();
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservation 을 삭제하면 참을 반환한다")
    void return_true_when_delete_reservation_with_id() {
        final var reservation =
                Reservation.from(null, "조이썬", "2024-10-03",
                        ReservationTime.from("10:00"));
        final var reservationId = reservationRepository.create(reservation, 1);

        final var result = reservationRepository.deleteById(reservationId);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("없는 id 를 통해 삭제하면 거짓을 반환한다")
    void return_false_when_delete_reservation_not_exist_id() {
        final var result = reservationRepository.deleteById(-1);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("없는 id 를 통해 검색하면 empty 를 반환한다")
    void return_empty_when_find_reservation_not_exist_id() {
        assertThat(reservationRepository.findById(-1)).isEmpty();
    }
}
