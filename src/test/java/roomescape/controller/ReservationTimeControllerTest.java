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
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Sql(value = "/createTimes.sql", executionPhase = BEFORE_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    ReservationTimeController controller;

    @Test
    @DisplayName("시간 예약 조회")
    void getTimes() {
        assertThat(controller.getTimes()).hasSize(3);
    }

    @DisplayName("시간 예약 저장")
    @Test
    void saveTime() {
        final CreateReservationTimeRequest time = new CreateReservationTimeRequest("14:00");
        final List<ReservationTimeResponse> beforeSaving = controller.getTimes();
        controller.save(time);
        final List<ReservationTimeResponse> afterSaving = controller.getTimes();

        assertThat(afterSaving.size() - beforeSaving.size()).isOne();
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        final List<ReservationTimeResponse> beforeDeleting = controller.getTimes();
        controller.delete(1L);
        final List<ReservationTimeResponse> afterDeleting = controller.getTimes();

        assertThat(beforeDeleting.size() - afterDeleting.size()).isOne();
    }

    @DisplayName("존재하지 않는 예약 삭제시 404 반환")
    @Test
    void deleteNonExistentReservation() {
        final ResponseEntity<Void> response = controller.delete(100L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
