package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationCreationRequestTest {
    @Test
    @DisplayName("과거의 시각을 가진 요청을 생성하면 예외가 발생한다.")
    void reservationRequestWithPastDateTimeTest() {
        String name = "페드로";
        LocalDateTime pastDateTime = LocalDateTime.MIN;
        LocalDate pastDate = pastDateTime.toLocalDate();
        LocalTime pastTime = pastDateTime.toLocalTime();

        assertThatThrownBy(
                () -> new ReservationCreationRequest(name, pastDate, pastTime)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거의 시각은 예약할 수 없습니다.");
    }
}
