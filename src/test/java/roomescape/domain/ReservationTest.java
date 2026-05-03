package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "pobizoninavy", " "})
    @DisplayName("이름은 2글자 이상 10글자 이내여야 한다.")
    void 불가한_이름(String name){
        //given
        ReservationTime reservationTime = new ReservationTime(null, "10:00");

        //when & then
        assertThatThrownBy(()-> new Reservation(null, name, "2023-04-10", reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름 형식");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"2020=04-33", "2026-13-13", "10-12-2020", "2020-4-1"})
    @DisplayName("날짜는 YYYY-MM-DD형식을 갖춰야 한다")
    void 날짜_형식_검증(String date){
        //given
        ReservationTime reservationTime = new ReservationTime(null, "10:00");

        //when & then
        assertThatThrownBy(()-> new Reservation(null, "pobi", date, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("날짜 형식");
    }
}
