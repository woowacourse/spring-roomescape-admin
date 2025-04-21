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
import roomescape.business.domain.ReservationTime;
import roomescape.business.service.ReservationTimeService;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(ReservationTimeController.class)
class ReservationTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Nested
    class 전체_조회_테스트 {
        @Test
        void 모든_예약을_조회할_수_있다() {
            Mockito.when(reservationTimeService.getAll())
                    .thenReturn(List.of(
                            new ReservationTime(1, LocalTime.of(10, 0)),
                            new ReservationTime(2, LocalTime.of(13, 0))
                    ));

            RestAssuredMockMvc.given().log().all()
                    .when().get("/times")
                    .then().log().all()
                    .statusCode(200)
                    .body(
                            "[0].id", equalTo(1),
                            "[0].startAt", equalTo("10:00"),
                            "[1].id", equalTo(2),
                            "[1].startAt", equalTo("13:00")
                    );
        }
    }

    @Nested
    class 생성_테스트 {
        private final LocalTime startTime = LocalTime.of(10, 0);

        @Test
        void 시간으로_생성할_수_있다() {
            Mockito.when(reservationTimeService.saveAndGet(Mockito.any()))
                    .thenReturn(new ReservationTime(1, startTime));

            final Map<String, Object> values = Map.of(
                    "startAt", LocalTime.of(10, 0).toString()
            );

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(200)
                    .body(
                            "id", equalTo(1),
                            "startAt", equalTo("10:00")
                    );
        }

        @Test
        void 시간이_없으면_400_응답을_반환한다() {
            Mockito.when(reservationTimeService.saveAndGet(Mockito.any()))
                    .thenReturn(new ReservationTime(1, startTime));

            final Map<String, Object> values = Map.of();

            RestAssuredMockMvc.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(values)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(400);
        }
    }

    @Nested
    class 삭제_테스트 {
        @Test
        void 아이디를_통해_삭제할_수_있다() {
            Mockito.doNothing().when(reservationTimeService).deleteById(1);

            RestAssuredMockMvc.given().log().all()
                    .when().delete("/times/{id}", "1")
                    .then().log().all()
                    .statusCode(200);
        }
    }
}
