package roomescape.presentation.web;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.business.service.ReservationService;
import roomescape.presentation.dto.response.ReservationResponse;
import roomescape.presentation.dto.response.ReservationTimeResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Nested
    class 전체_조회_테스트 {
        @Test
        void 모든_예약을_조회할_수_있다() {
            final LocalDate date1 = LocalDate.now().plusDays(20);
            final LocalDate date2 = LocalDate.now().plusDays(25);

            Mockito.when(reservationService.getAll())
                    .thenReturn(List.of(
                            new ReservationResponse(1L, "dompoo", date1, new ReservationTimeResponse(5L, "10:00")),
                            new ReservationResponse(2L, "popo", date2, new ReservationTimeResponse(10L, "13:00"))
                    ));

            RestAssuredMockMvc.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body(
                            "[0].id", equalTo(1),
                            "[0].name", equalTo("dompoo"),
                            "[0].date", equalTo(date1.toString()),
                            "[0].time.id", equalTo(5),
                            "[0].time.startAt", equalTo("10:00"),
                            "[1].id", equalTo(2),
                            "[1].name", equalTo("popo"),
                            "[1].date", equalTo(date2.toString()),
                            "[1].time.id", equalTo(10),
                            "[1].time.startAt", equalTo("13:00")
                    );
        }
    }

    @Nested
    class 생성_테스트 {
        private final LocalDate createDate = LocalDate.now().plusDays(20);
        private final String name = "dompoo";

        @Test
        void 이름_날짜_시간으로_생성할_수_있다() {
            Mockito.when(reservationService.saveAndGet(Mockito.any()))
                    .thenReturn(new ReservationResponse(1L, name, createDate, new ReservationTimeResponse(5L, "10:00")));

            final Map<String, Object> values = Map.of(
                    "name", name,
                    "date", createDate.toString(),
                    "timeId", 5
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body(
                            "id", equalTo(1),
                            "name", equalTo(name),
                            "date", equalTo(createDate.toString()),
                            "time.id", equalTo(5),
                            "time.startAt", equalTo("10:00")
                    );
        }

        @Test
        void 이름이_없으면_400_응답을_반환한다() {
            final Map<String, Object> values = Map.of(
                    "date", createDate.toString(),
                    "time_id", 1
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

        @Test
        void 이름이_비어있으면_400_응답을_반환한다() {
            final Map<String, Object> values = Map.of(
                    "name", "",
                    "date", createDate.toString(),
                    "time_id", 1
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

        @Test
        void 날짜가_없으면_400_응답을_반환한다() {
            final Map<String, Object> values = Map.of(
                    "name", name,
                    "time_id", 1
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }

        @Test
        void 시간이_없으면_400_응답을_반환한다() {
            final Map<String, Object> values = Map.of(
                    "name", name,
                    "date", createDate.toString()
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }
    }

    @Nested
    class 삭제_테스트 {
        @Test
        void 아이디를_통해_삭제할_수_있다() {
            Mockito.doNothing().when(reservationService).deleteById(1);

            RestAssuredMockMvc.given().log().all()
                    .when().delete("/reservations/{id}", "1")
                    .then().log().all()
                    .statusCode(200);
        }
    }
}
