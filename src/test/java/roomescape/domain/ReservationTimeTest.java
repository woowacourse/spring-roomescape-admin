package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    void 시간_형식_Domain_정상테스트() {
        ReservationTime time = new ReservationTime(1L, "10:10");
        assertThat(time.getStartAt()).isEqualTo("10:10");
    }

    @DisplayName("시간은 시간 형식에 맞아야한다.")
    @Test
    void 시간_형식_Domain_예외테스트() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ReservationTime(1L,"1kjfl");
        });
    }

}