package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTest {

    @DisplayName("같은 아이디는 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,true", "2,false"}, delimiter = ',')
    void test1(long id, boolean expected) {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(1, "꾹이", now);

        // when
        boolean result = reservation.sameId(id);

        // then
        assertThat(result).isEqualTo(expected);
    }

}
