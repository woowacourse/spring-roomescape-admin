package serialize;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class ReservationTimeSerializeTest {

    @DisplayName("Intellij로 빌드할 경우 기본생성자가 있는 객체만 역직렬화 할 수 있다.")
    @Test
    void test() {
        JsonPath jsonPath = new JsonPath("""
        {
            "id": "1",
            "startAt": "10:10"
        }""");

        ReservationTime reservationTime = jsonPath.getObject(".", ReservationTime.class);

        Assertions.assertThat(reservationTime.getId()).isEqualTo(1);
        Assertions.assertThat(reservationTime.getStartAt()).isEqualTo("10:10");
    }
}
