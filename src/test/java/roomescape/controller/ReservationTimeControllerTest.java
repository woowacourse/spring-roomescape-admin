package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.dto.ReservationTimeRequest;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationTimeControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void initPort() {
        RestAssured.port = port;
    }

    @AfterEach
    void initData() {
        RestAssured.get("/times")
                .then().extract().body().jsonPath().getList("id")
                .forEach(id -> RestAssured.delete("/times/" + id));
    }

    @DisplayName("시간 정보를 추가한다.")
    @Test
    void createReservationTime() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ReservationTimeRequest("10:00"))
                .when().post("/times")
                .then().log().all().statusCode(200).body("id", is(greaterThan(0)));
    }

    @DisplayName("등록된 시간 내역을 조회한다.")
    @Test
    void findAllReservationTime() {
        //given
        RestAssured.given().contentType(ContentType.JSON).body(new ReservationTimeRequest("10:00"))
                .when().post("/times");

        //when&then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all().statusCode(200).body("size()", is(1));
    }

    @DisplayName("시간 정보를 id로 삭제한다.")
    @Test
    void deleteReservationTimeById() {
        //given
        var id = RestAssured.given().contentType(ContentType.JSON).body(new ReservationTimeRequest("10:00"))
                .when().post("/times")
                .then().extract().response().jsonPath().get("id");

        //when&then
        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .assertThat().statusCode(200);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .assertThat().body("size()", is(0));
    }

    @DisplayName("시간 정보를 존재하지 않는 id로 삭제를 시도한다.")
    @Test
    void deleteFailReservationTimeByInvalidId() {
        //given
        long invalidId = 0;

        //when&then
        RestAssured.given().log().all()
                .when().delete("/times/" + invalidId)
                .then().log().all()
                .assertThat().statusCode(400).body(is("존재하지 않는 예약 시간입니다. id: " + invalidId));
    }
}
