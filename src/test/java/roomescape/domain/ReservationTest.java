package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.fixture.ReservationFixture;

class ReservationTest {

    @Test
    @DisplayName("id가 같으면 true를 반환한다.")
    void should_return_true_when_id_is_same() {
        // given
        Long id = 1L;
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;

        // when
        final boolean result = reservation.isSameId(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("id가 다르면 false를 반환한다.")
    void should_return_false_when_id_is_different() {
        // given
        Long id = 1L;
        Long otherId = 2L;
        Reservation reservation = ReservationFixture.RESERVATION_1_KIM_2025_04_21_10_00;

        // when
        final boolean result = reservation.isSameId(otherId);

        // then
        assertThat(result).isFalse();
    }
}
