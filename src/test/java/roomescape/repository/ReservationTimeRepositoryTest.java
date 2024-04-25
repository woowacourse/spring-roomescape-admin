package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationTimeRepositoryTest {
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("ReservationTime 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservationTime = ReservationTime.from("10:00");

        final var id = reservationTimeRepository.create(reservationTime);
        final var entity = reservationTimeRepository.findById(id)
                                                    .orElseThrow();

        assertThat(entity.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 삭제하면 참을 반환한다")
    void return_true_when_delete_reservationTime_with_id() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.deleteById(id);

        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("없는 id 를 통해 삭제하면 거짓을 반환한다")
    void return_false_when_delete_reservationTime_not_exist_id() {
        final var result = reservationTimeRepository.deleteById(-1);

        assertThat(result).isFalse();

    }


    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 찾는다")
    void find_reservationTime_by_id() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.findById(id);

        assertThat(result).isNotEmpty();
    }
}
