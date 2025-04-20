package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTest {

    @ParameterizedTest
    @CsvSource({
            "yyyy-MM-dd, HH:mm, 2024-12-23, 15:00",
            "yyyy/MM/dd, HH:mm, 2024/12/23, 15:00",
            "dd-MM-yyyy, HH:mm, 23-12-2024, 15:00",
            "MM/dd/yyyy, HH:mm, 12/23/2024, 15:00"
    })
    void 시간과_날짜를_포멧팅_형식으로_반환한다(String datePattern, String timePattern, String dateExpected, String timeExpected) {
        // given
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 23, 15, 0);
        Reservation reservation = new Reservation(null, null, dateTime);

        // when
        String resultDate = reservation.formatDateTime(dateFormatter);
        String resultTime = reservation.formatDateTime(timeFormatter);

        // then
        assertAll(
                () -> assertThat(resultDate).isEqualTo(dateExpected),
                () -> assertThat(resultTime).isEqualTo(timeExpected)
        );
    }
}
