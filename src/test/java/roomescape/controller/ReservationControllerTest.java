package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationService;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void 날짜가_형식에_맞지_않는_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-053");
        params.put("timeId", "1");

        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 모든_예약_정보를_정상적으로_반환한다() {
        // given
        final ReservationTime reservationTime = ReservationTime.of(LocalTime.MAX);
        final Reservation 윌슨 = new Reservation(1L, "윌슨", LocalDate.now().plusDays(1), reservationTime);
        final Reservation 히로 = new Reservation(2L, "히로", LocalDate.now().plusDays(1), reservationTime);
        윌슨.setId(1L);
        히로.setId(2L);
        List<Reservation> reservations = List.of(
                윌슨, 히로
        );
        final List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::toDto)
                .toList();

        given(reservationService.getAllReservation()).willReturn(responses);

        RestAssuredMockMvc.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].name", is("윌슨"))
                .body("[1].id", is(2))
                .body("[1].name", is("히로"));
    }

    @Test
    void 이름이_빈_값인_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", " ");
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("timeId", "1");

        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }


    @Test
    void id_값이_문자열_이므로_400을_반환한다() {
        RestAssuredMockMvc.given().log().all()
                .pathParam("id", "일")
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 이름이_4글자를_초과하면_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라우우운");
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("timeId", "1");

        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 예약_날짜가_현재보다_이전이면_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

}
