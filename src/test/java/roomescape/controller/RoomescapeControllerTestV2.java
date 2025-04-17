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
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@WebMvcTest(RoomescapeController.class)
public class RoomescapeControllerTestV2 {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void 날짜가_형식에_맞지_않는_경우_400을_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-053");
        params.put("time", "15:40");

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
        final Reservation 윌슨 = new Reservation("윌슨", LocalDate.now(), LocalTime.now());
        final Reservation 히로 = new Reservation("히로", LocalDate.now(), LocalTime.now());
        윌슨.setId(1L);
        히로.setId(2L);
        List<Reservation> reservations = List.of(
                윌슨, 히로
        );

        given(reservationRepository.findAll()).willReturn(reservations);

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

}
