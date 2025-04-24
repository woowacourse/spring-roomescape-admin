package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.common.domain.DomainId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainIdTest {

    @Test
    @DisplayName("값이 설정되지 않았다면, 예외가 발생한다")
    void whenValueIsThrowException() {
        // given
        final DomainId id = ReservationId.unassigned();

        // when
        // then
        assertThatThrownBy(id::getValue)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("저장되지 않아서 식별할 수 없습니다.");
    }

    @Test
    @DisplayName("값이 설정되지 않았다면, 같은 객체가 아니게 취급된다. (null로 관리된다면 같은 객체였을 것이다)")
    void when() {
        // given
        final DomainId id1 = ReservationId.unassigned();
        final DomainId id2 = ReservationId.unassigned();

        // when
        // then
        assertThat(id1.equals(id2)).isFalse();
    }
}
