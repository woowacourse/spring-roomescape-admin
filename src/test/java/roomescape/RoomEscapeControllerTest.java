package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomEscapeControllerTest {

    public static Stream<Arguments> NullParameter() {
        return Stream.of(
                Arguments.of(
                        Map.of("name", "브라운", "date", "2023-08-05")
                ),
                Arguments.of(
                        Map.of("date", "2023-08-05", "time", "15:40")
                ),
                Arguments.of(
                        Map.of("name", "브라운", "time", "15:40")
                )
        );
    }

    @ParameterizedTest
    @MethodSource("NullParameter")
    @DisplayName("이름, 날짜, 시간 중 하나라도 없으면 400 Bad Request")
    void badRequestAnyParameterNull(Map<String, String> params) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    public static Stream<Arguments> InvalidParameter() {
        return Stream.of(
                Arguments.of(
                        Map.of("name", "브라운", "date", "2023-13-01", "time", "23:00")
                ),
                Arguments.of(
                        Map.of("name", "브라운", "date", "2023-08-05", "time", "25:00")
                ),
                Arguments.of(
                        Map.of("name", "여섯글자이름", "date", "2023-08-05", "time", "15:40")
                )
        );
    }

    @ParameterizedTest
    @MethodSource("InvalidParameter")
    @DisplayName("이름, 날짜, 시간 중 하나라도 잘못된 형식이면 400 Bad Request")
    void badRequestAnyParameterInvalid(Map<String, String> params) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
