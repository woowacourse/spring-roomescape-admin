package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Sql(value = "/createTimeAndReservations.sql", executionPhase = BEFORE_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Test
    @DisplayName("예약 조회")
    void getReservation() {
        assertThat(controller.getReservations()).hasSize(2);
    }

    @DisplayName("예약 추가")
    @Test
    void saveReservation() {
        //given
        final CreateReservationRequest reservation = new CreateReservationRequest("2024-04-18", "레디", 1L);

        //when
        final List<ReservationResponse> beforeSaving = controller.getReservations();
        controller.save(reservation);
        final List<ReservationResponse> afterSaving = controller.getReservations();

        //then
        assertThat(afterSaving.size() - beforeSaving.size()).isOne();
    }


    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {

        final List<ReservationResponse> beforeDeleting = controller.getReservations();
        controller.delete(1L);
        final List<ReservationResponse> afterDeleting = controller.getReservations();

        //then
        assertThat(beforeDeleting.size() - afterDeleting.size()).isOne();
    }

    @DisplayName("존재하지 않는 예약 삭제시 404 반환")
    @Test
    void deleteNonExistentReservation() {
        final ResponseEntity<Void> response = controller.delete(1000L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
