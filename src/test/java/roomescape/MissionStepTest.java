package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.model.ReservationResponse;
import roomescape.model.ReservationRequest;
import roomescape.model.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    private final RowMapper<ReservationRequest> reservationDtoMapper = (resultSet, rowNum) ->
            new ReservationRequest(
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getLong("timeId")
            );

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;

        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("99:99");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeRequest)
                .post("/times");
    }

    @Test
    @DisplayName("1단계 Test- 예약 첫 페이지를 성공적으로 응답한다.")
    void showMainPage_ShouldGetIndexPageAndOK_WhenReqeustOccurs() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("2단계 Test- 예약 페이지를 성공적으로 응답한다.")
    void showReservationPage_ShouldGetReservationPage_WhenReqeustOccurs() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("2단계 Test- 예약 정보 요청 시 JSON 형식으로 응답한다.")
    void listReservations_ShouldReturnAllReservations_WhenRequestOccurs() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("3단계, 8단계 Test- 새로운 예약 정보 등록 요청을 처리하고 성공 시 200 상태 코드를 응답한다.")
    void createReservation_ShouldReturnOK_WhenProceedCreateRequestSuccessfully() {
        ReservationRequest requestReservationRequest = new ReservationRequest("브라운", "2023-08-05", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestReservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("3단계, 8단계 Test- 특정 id를 가진 예약 정보를 삭제 요청을 처리하고 성공 시 204 상태 코드를 응답한다.")
    void deleteReservation_ShouldDeleteReservationAndReturnOK_WhenProceedDeleteRequestSuccessfully() {
        ReservationRequest requestReservationRequest = new ReservationRequest("브라운", "2023-08-05", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestReservationRequest)
                .when().post("/reservations");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("4단계 - DB 연결을 확인한다.")
    void test_ShouldNotAssertError_WhenCheckJDBCTemplateConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("5단계 - 예약 조회 API와 데이터베이스 쿼리를 통해 각각 조회한 예약 수가 같아야한다.")
    void findAllReservation_ShouldEqualReservationNumbers_WhenReadDBTable() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                1L);

        List<ReservationResponse> reservationResponses = RestAssured.given()
                .log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract().jsonPath()
                .getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservationResponses.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("6단계 - 특정 Id의 reservation의 삭제 요청을 처리하고 성공 시 200 상태 코드를 응답한다.")
    void deleteReservation_ShouldReturnNOCONTENT_WhenDeleteSuccessfully() {

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isZero();
    }

    @Test
    @DisplayName("7단계 - time 추가 요청을 처리하고 성공 시 200 상태 코드를 응답한다.")
    void createReservationTime_ShouldReturnCREATED_WhenCreateSuccessfully() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeRequest)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("7단계 - time 삭제 요청을 처리하고 성공 시 200 상태 코드를 응답한다.")
    void deleteReservationTime_ShouldReturnNOCONTENT_WhenDeleteSuccessfully() {

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("9단계- 기존 ReservationController의 데이터베이스 관련 로직을 다른 클래스로 분리한 것을 확인한다.")
    void reservationControllerTest_ShouldAssertFalse_WhenChecked() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

}
