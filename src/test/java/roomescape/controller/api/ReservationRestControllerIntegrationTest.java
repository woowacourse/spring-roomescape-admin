//package roomescape.controller.api;
//
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class ReservationRestControllerIntegrationTest {
//
//    @Test
//    void 클라이언트_예약_생성_유효성_테스트() {
//        Map<String, String> params = new HashMap<>();
//        params.put("date", "2023-08-05");
//        params.put("time", "15:40");
//
//        RestAssured.given().log().all()
//                .contentType(ContentType.JSON)
//                .body(params)
//                .when().post("/reservations")
//                .then().log().all()
//                .statusCode(400);
//    }
//
//    @Test
//    void 없는_인덱스로_예약을_삭제하는_경우() {
//        RestAssured.given().log().all()
//                .when().delete("/reservations/5")
//                .then().log().all()
//                .statusCode(404);
//    }
//}
