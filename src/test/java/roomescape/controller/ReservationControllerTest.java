package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("/admin/reservation 요청 시 예약 관리 페이지 응답")
    void readReservation() {
        Reservation reservation1 = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), LocalTime.now());
        Reservation reservation2 = new Reservation(2L, "네오", LocalDate.now().plusDays(1), LocalTime.now());

        List<Reservation> reservations = List.of(
                reservation1, reservation2
        );

        given(reservationRepository.findAll()).willReturn(reservations);

        RestAssuredMockMvc.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].name", is("브라운"))
                .body("[1].id", is(2))
                .body("[1].name", is("네오"));
    }
}
