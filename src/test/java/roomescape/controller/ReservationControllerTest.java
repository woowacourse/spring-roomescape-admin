package roomescape.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;
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
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;
import roomescape.exceptions.EntityNotFoundException;
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

    @Test
    @DisplayName("예약 관리 페이지 내에서 예약 추가")
    void postReservation() {
        LocalDate fixedDate = LocalDate.of(2023, 5, 15);
        LocalTime fixedTime = LocalTime.of(14, 30);

        ReservationRequestDto dto = new ReservationRequestDto("브라운", fixedDate, fixedTime);

        RestAssuredMockMvc.given().log().all()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("존재하는 ID로 삭제 요청 시 성공적으로 처리되어야 한다")
    void deleteExistingReservation() {
        long reservationId = 1L;

        willDoNothing().given(reservationRepository).deleteById(reservationId);

        RestAssuredMockMvc.given().log().all()
                .when().delete("/reservations/" + reservationId)
                .then().log().all()
                .statusCode(204);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 삭제 요청 시 404 응답이 반환되어야 한다")
    void deleteNonExistingReservation() {
        long nonExistingId = 999L;

        willThrow(new EntityNotFoundException("데이터를 찾을 수 없습니다."))
                .given(reservationRepository)
                .deleteById(nonExistingId);

        RestAssuredMockMvc.given().log().all()
                .when().delete("/reservations/" + nonExistingId)
                .then().log().all()
                .statusCode(404);

        verify(reservationRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    @DisplayName("서버 내부 오류 발생 시 500 응답이 반환되어야 한다")
    void handleServerInternalError() {
        when(reservationRepository.findAll()).thenThrow(new RuntimeException("데이터베이스 오류"));

        RestAssuredMockMvc.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(500)
                .body("body.title", equalTo("Internal Server Error"))
                .body("body.detail", equalTo("서버 오류가 발생했습니다"));
    }
}
