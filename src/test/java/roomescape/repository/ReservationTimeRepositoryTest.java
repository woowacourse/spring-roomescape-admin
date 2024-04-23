package roomescape.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.ReservationTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationTimeRepositoryTest {
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("ReservationTime 을 디비에 저장후, id 값을 반환한다")
    void create_reservationTime_with_domain() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);
        assertThat(reservationTime).isEqualTo(reservationTimeRepository.findById(id));
    }

    @Test
    @DisplayName("모든 ReservationTime 을 받아온다")
    void find_all_reservationTime() {
        final var reservationTime = ReservationTime.from("10:00");
        final var prevSize = reservationTimeRepository.findAll()
                                                      .size();
        reservationTimeRepository.create(reservationTime);

        final var afterSize = reservationTimeRepository.findAll()
                                                       .size();

        assertThat(afterSize).isEqualTo(prevSize + 1);
    }

    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 삭제한다")
    void delete_reservationTime_with_id() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.deleteById(id);

        assertThat(result).isTrue();
        assertThatThrownBy(() -> reservationTimeRepository.findById(id))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("id를 통해 해당하는 reservationTime 을 찾는다")
    void find_reservationTime_by_id() {
        final var reservationTime = ReservationTime.from("10:00");
        final var id = reservationTimeRepository.create(reservationTime);

        final var result = reservationTimeRepository.findById(id);

        assertThat(result).isEqualTo(reservationTime);
    }

}
