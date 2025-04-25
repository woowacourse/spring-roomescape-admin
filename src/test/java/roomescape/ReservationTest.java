package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String createTableSql = """
                DROP TABLE IF EXISTS reservation, reservation_time;
                
                CREATE TABLE reservation_time
                (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                
                CREATE TABLE reservation
                (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                );
                """;
        jdbcTemplate.execute(createTableSql);
        String insertSql = """
                INSERT INTO RESERVATION_TIME(id, start_at) VALUES 
                   ('1', '13:40'), 
                   ('2', '14:40'),
                   ('3', '15:40')
                ;
                
                INSERT INTO RESERVATION(name, date, time_id) VALUES
                    ('브라운', '2023-03-03', '1'),
                    ('솔라', '2023-03-03', '2'),
                    ('네오', '2023-03-05', '3')
                ;
                """;
        jdbcTemplate.update(insertSql);
    }

    @Test
    @DisplayName("예약을 성공적으로 추가한다")
    void createReservationTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(4));
    }

    @Test
    @DisplayName("필드의 형식이 맞지 않다면 예외가 발생한다")
    void reservationFormatDoesntMatchTest() {
        // given
        Map<String, String> invalidDate = new HashMap<>();
        invalidDate.put("name", "브라운");
        invalidDate.put("date", "20230-08-05");
        invalidDate.put("time", "15:40");

        Map<String, String> invalidTime = new HashMap<>();
        invalidTime.put("name", "브라운");
        invalidTime.put("date", "2023-08-05");
        invalidTime.put("time", "15:40.");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidDate)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidTime)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성 시 필드가 비어있다면 예외가 발생한다")
    void reservationNameBlankTest() {
        // given
        Map<String, String> blankName = new HashMap<>();
        blankName.put("name", " ");
        blankName.put("date", "2023-08-05");
        blankName.put("time", "15:40");

        Map<String, String> blankDate = new HashMap<>();
        blankDate.put("name", "브라운");
        blankDate.put("date", "");
        blankDate.put("time", "15:40");

        Map<String, String> blankTime = new HashMap<>();
        blankTime.put("name", "브라운");
        blankTime.put("date", "2023-08-05");
        blankTime.put("time", "");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(blankName)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(blankDate)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(blankTime)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성 시 필드가 없다면 예외가 발생한다")
    void reservationDateOrTimeNullTest() {
        // given
        Map<String, String> noName = new HashMap<>();
        noName.put("date", "2023-08-05");
        noName.put("time", "15:40");

        Map<String, String> noDate = new HashMap<>();
        noDate.put("name", "브라운");
        noDate.put("time", "15:40");

        Map<String, String> noTime = new HashMap<>();
        noTime.put("name", "브라운");
        noName.put("date", "2023-08-05");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(noName)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(noDate)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(noTime)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservations() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void deleteReservationTest() {
        // given
        createReservationTest();

        // when, then
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("존재하지 않는 예약 삭제 시 예외가 발생한다")
    void deleteNonExistReservationTest() {
        // given
        createReservationTest();

        // when, then
        RestAssured.given().log().all()
                .when().delete("/reservations2")
                .then().log().all()
                .statusCode(404);
    }
}
