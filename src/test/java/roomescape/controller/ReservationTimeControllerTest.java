package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    ReservationTimeController controller;

    @Test
    @DisplayName("시간 예약 조회")
    void getTimes() {
        assertThat(controller.getTimes()).isEmpty();
    }

    @DisplayName("시간 예약 저장")
    @Test
    void saveTime() {
        //given
        final CreateReservationTimeRequest time = new CreateReservationTimeRequest("14:00");
        final ReservationTimeResponse expected = new ReservationTimeResponse(1L, "14:00");

        //when
        final ReservationTimeResponse saved = controller.save(time);

        //then
        assertThat(saved).isEqualTo(expected);
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        //given
        final CreateReservationTimeRequest time = new CreateReservationTimeRequest("14:00");
        final ReservationTimeResponse saved = controller.save(time);

        //when
        assert saved != null;
        controller.delete(saved.id());

        //then
        assertThat(controller.getTimes()).isEmpty();
    }

    @DisplayName("존재하지 않는 예약 삭제시 404 반환")
    @Test
    void deleteNonExistentReservation() {
        final ResponseEntity<Void> response = controller.delete(100L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
