package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationTimeException;

class ReservationTimeTest {

    private static final long DEFAULT_ID = 1;

    @Test
    void 생성_시_시간_정보가_없다면_예외를_던진다() {
        assertThatThrownBy(() -> ReservationTime.create(
                null
        )).isInstanceOf(InvalidReservationTimeException.class)
                .hasMessage("예약 시간엔 시간 정보가 존재해야 합니다.");
    }

    @Test
    void 불러올_때는_시간_정보가_없어도_정상적으로_생성된다() {
        assertThatNoException().isThrownBy(() -> ReservationTime.retrieve(
                DEFAULT_ID,
                null
        ));
    }
}
