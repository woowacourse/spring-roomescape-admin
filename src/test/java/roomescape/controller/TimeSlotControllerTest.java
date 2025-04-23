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
        var controller = new TimeSlotController(new TimeSlotFakeRepository());
        var request = new CreateTimeSlotRequest(LocalTime.of(10, 0));

        //when
        var responseEntity = controller.create(request);

        //then
        var timeSlotList = controller.getAllTimeSlots().getBody();
        assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(timeSlotList).hasSize(1)
        );
    }

    @Test
    @DisplayName("예약 시간을 삭제할 수 있다.")
    void deleteTimeSlot() {
        //given
        var controller = new TimeSlotController(new TimeSlotFakeRepository());
        var createRequest = new CreateTimeSlotRequest(LocalTime.of(10, 0));
        var createdTimeSlot = controller.create(createRequest).getBody();

        //when
        var responseEntity = controller.delete(createdTimeSlot.id());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
