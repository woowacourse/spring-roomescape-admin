package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.common.domain.DomainId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainIdTest {

    @Test
    @DisplayName("값이 설정되지 않았다면, 예외가 발생한다")
    void whenValueIsNullThrowException() {
        // given
        final DomainId id = ReservationId.unassigned();

        // when
        // then
        assertThatThrownBy(id::getValue)
                .isInstanceOf(IllegalStateException.class);
    }
}
