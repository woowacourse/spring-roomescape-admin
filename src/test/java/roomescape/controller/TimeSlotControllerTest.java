package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.repository.TimeSlotFakeRepository;

class TimeSlotControllerTest {

    @Test
    @DisplayName("예약 시간을 추가할 수 있다.")
    void createTimeSlot() {
        //given
        final var controller = new TimeSlotController(new TimeSlotFakeRepository());

        //when
        final var responseEntity = controller.create(new CreateTimeSlotRequest(LocalTime.of(10, 0)));
        final var timeSlots = controller.getAllTimeSlots();

        //then
        assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(timeSlots.getBody()).hasSize(1)
        );
    }

    @Test
    @DisplayName("예약 시간을 삭제할 수 있다.")
    void deleteTimeSlot() {
        //given
        final var controller = new TimeSlotController(new TimeSlotFakeRepository());
        final var createRequest = new CreateTimeSlotRequest(LocalTime.of(10, 0));
        final var createdTimeSlot = controller.create(createRequest).getBody();

        //when
        final var deleteResponse = controller.delete(createdTimeSlot.id());

        //then
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
