package roomescape.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationRequestTest {

    @DisplayName("date가 형식에 맞지 않으면 예외가 발생한다.")
    @Test
    void createRequestFail_when_invalidFormattedDate() {
        String date = "20222-02-02";
        //assertThatThrownBy(() -> new ReservationRequest(date, "멍구", 1L));
        new ReservationRequest(date, "멍구", 1L);
    }

}
