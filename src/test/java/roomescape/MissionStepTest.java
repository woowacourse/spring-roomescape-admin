package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.repository.ReservationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private ReservationRepository repository;

    @LocalServerPort
    int port;

    @DisplayName("/ 요청 시 admin/reservation으로 리디렉션")
    @Test
    void welcomePage_redirect_to_reservationPage() {
        RestAssured.port = this.port;

        RestAssured.given().log().all()
                .redirects().follow(false) // 리디렉션 따라가지 말고 그대로 응답 확인
                .when().get("/")
                .then().log().all()
                .statusCode(302) // 또는 301, 실제 리디렉션 코드에 따라 다름
                .header("Location",  endsWith("/admin/reservation"));
    }

    @DisplayName("/admin 요청 시 200 OK 응답")
    @Test
    void request_adminPage_then_200() {
        RestAssured.port = this.port;
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin/reservation 요청 시 200 OK")
    @Test
    void request_ReservationAdminPage_then_200() {
        RestAssured.port = this.port;
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("모든 예약을 가져오는 api 호출 시, 현재 저장소의 예약 개수와 일치해야한다.")
    @Test
    void request_getAllReservations() {
        RestAssured.port = this.port;
        int reservationsCount = repository.findAll().size();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(reservationsCount));
    }

    @DisplayName("예약 추가 api 호출 시, id가 정상적으로 부여된다.")
    @Test
    public void request_addReservation() {
        int repositorySize = repository.findAll().size();
        int expectedSize = repositorySize + 1;

        RestAssured.port = this.port;
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(expectedSize));

        int afterAddSize = repository.findAll().size();
        assertThat(afterAddSize).isEqualTo(expectedSize);
    }

    @DisplayName("")
    @Test
    void requestDeleteReservation() {
        RestAssured.port = this.port;
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

}
