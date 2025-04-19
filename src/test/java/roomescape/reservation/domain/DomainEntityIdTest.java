package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.common.domain.DomainEntityId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DomainEntityIdTest {

    @Test
    @DisplayName("값이 설정되지 않았다면, 예외가 발생한다")
    void whenValueIsNullThrowException() {
        // given
        final DomainEntityId id = DomainEntityId.from(null);

        // when
        // then
        assertThatThrownBy(id::getValue)
                .isInstanceOf(IllegalStateException.class);
    }
}
