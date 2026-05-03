package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTimeTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1:13", "6:60", "24:20"})
    @DisplayName("시간 형식은 HH:MM 형식을 갖춰야 한다")
    void 시간_형식_검증(String time){
        //when & then
        assertThatThrownBy(()-> new ReservationTime(null, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시간 형식");
    }
}
