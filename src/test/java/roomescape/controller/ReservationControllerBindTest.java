package roomescape.controller;

import io.restassured.RestAssured;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerBindTest {

    @DisplayName("잘못된 형식의 요청으로 파라미터 바인딩 실패 시 400 Bad Request")
    @ParameterizedTest
    @MethodSource("invalidRequestParameters")
    void badRequestWhenRequestInvalid(Map<String, String> request) {
        RestAssured
            .given().contentType("application/json")
            .when().body(request).post("/reservations")
            .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    public static Stream<Arguments> invalidRequestParameters() {
        return Stream.of(
            Arguments.of(Map.of("name", "brown", "date", "2-0-2-3", "time", "12:00")),
            Arguments.of(Map.of("name", "brown", "date", "2023-01-01", "time", "30:00"))
        );
    }
}
