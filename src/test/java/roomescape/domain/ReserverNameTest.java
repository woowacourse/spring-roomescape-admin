package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.exception.EmptyReserverNameException;

class ReserverNameTest {

    @DisplayName("이름으로 객체를 생성할 수 있다.")
    @Test
    void createReservationName() {
        // given

        // when & then
        assertThatCode(() -> new ReserverName("엠제이"))
                .doesNotThrowAnyException();
    }

    @DisplayName("이름이 null이면 예외가 발생한다.")
    @Test
    void createNullName() {
        // given

        // when & then
        assertThatThrownBy(() -> new ReserverName(null))
                .isInstanceOf(EmptyReserverNameException.class);
    }
}
