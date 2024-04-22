package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    @DisplayName("id가 동일하면 true를, 동일하지 않으면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1, true", "2, false"})
    void checkSameId(long id, boolean result) {
        Reservation reservation = new Reservation(1, "켬미", "2023-08-05", "15:40");
        assertThat(reservation.checkSameId(id)).isEqualTo(result);
    }
}
