package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @DisplayName("같은 시간인지 확인한다.")
    @Test
    void isSameTime() {
        //given
        ReservationTime time1 = ReservationTime.parse("10:30");
        ReservationTime time2 = ReservationTime.parse("10:30");
        ReservationTime time3 = ReservationTime.parse("10:31");

        //when
        boolean sameTime = time1.isSameTime(time2);
        boolean isNotSameTime = time2.isSameTime(time3);

        //then
        assertThat(sameTime).isTrue();
        assertThat(isNotSameTime).isFalse();
    }
}
