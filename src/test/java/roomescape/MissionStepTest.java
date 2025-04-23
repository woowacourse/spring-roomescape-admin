package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("[2단계] /admin/reservation 경로 요청시 200 OK를 반환한다.")
    @Test
    void requestSuccessReservation() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("[2단계] /reservations 경로 요청시 200 OK를 반환한다.")
    @Test
    void requestSuccessReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("[3단계] 예약을 추가 할 수 있다.")
    @Test
    void create() {
        //given
        Map<String, String> params = dataFixture();

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @DisplayName("[3단계] 모든 예약을 조회할 수 있다.")
    @Test
    void read() {
        //given
        create();

        // when //then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("[3단계] 단건 예약을 삭제할 수 있다.")
    @Test
    void delete() {
        //given
        create();

        //when //then
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("[5단계] 데이터베이스에서 예약을 조회 할 수 있다.")
    @Test
    void readFromDatabase() {
        //given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "15:40");

        //when
        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        //then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("[6단계] 예약을 데이터베이스에 추가할 수 있다.")
    @Test
    void creatFromDatabase() {
        //given
        Map<String, String> params = dataFixture();

        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        //then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("[6단계] 예약번호에 따른 예약 정보를 데이터베이스에서 삭제할 수 있다.")
    @Test
    void deleteFromDatabase() {
        //given
        create();

        //when
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        //then
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    private Map<String, String> dataFixture() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");
        return params;
    }

    @DisplayName("[7단계] 예약시간을 데이터베이스에 추가할 수 있다.")
    @Test
    void saveTime() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("[7단계] 예약시간을 데이터베이스에서 조회할 수 있다.")
    @Test
    void readAllTime() {
        //given
        saveTime();

        //when //then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("[7단계] 예약시간 번호에 따른 예약시간을 데이터베이스에서 삭제할 수 있다.")
    @Test
    void deleteTime() {
        //given
        saveTime();

        //when //then
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}

