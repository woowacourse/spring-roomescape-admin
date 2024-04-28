package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @DisplayName("예약 저장")
    @Sql("/createTime.sql")
    @Test
    void saveReservation() {
        //given
        final List<ReservationResponse> beforeSaving = reservationService.findAll();
        final CreateReservationRequest request = new CreateReservationRequest("2024-02-13", "레디", 1L);

        //when
        reservationService.save(request);
        final List<ReservationResponse> afterSaving = reservationService.findAll();

        //then
        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("예약 삭제")
    @Sql("/createTimeAndReservation.sql")
    @Test
    void removeReservation() {
        final List<ReservationResponse> beforeRemoving = reservationService.findAll();
        reservationService.remove(1L);
        final List<ReservationResponse> afterRemoving = reservationService.findAll();

        assertAll(
                () -> assertThat(beforeRemoving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty()
        );
    }

    @DisplayName("전제 조회")
    @Sql("/createTimeAndReservations.sql")
    @Test
    void findAll() {
        final ReservationTimeResponse time = new ReservationTimeResponse(1L, "13:00");

        final List<ReservationResponse> expected = List.of(
                new ReservationResponse(1L, "레디", "2024-02-13", time),
                new ReservationResponse(2L, "감자", "2024-02-14", time));

        final List<ReservationResponse> findAll = reservationService.findAll();

        assertThat(findAll).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않은 예약 삭제시 예외")
    void removeNotExist() {
        assertThatThrownBy(() -> reservationService.remove(100L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
