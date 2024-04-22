package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.controller.dto.TimeSlotCreationResponse;
import roomescape.domain.TimeSlot;
import roomescape.repository.TimeSlotRepository;
import roomescape.service.dto.TimeSlotDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TimeSlotServiceTest {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @AfterEach
    void tearDown() {
        timeSlotRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void getAllTimesTest() {
        // given
        Stream.of("13:00", "14:00", "15:00")
                .map(LocalTime::parse)
                .map(TimeSlotDto::new)
                .forEach(timeSlotRepository::create);
        // when
        List<TimeSlotCreationResponse> actual = timeSlotService.getAllTimes();
        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void addTimeTest() {
        // given
        TimeSlotCreationRequest request = new TimeSlotCreationRequest("13:00");
        // when
        TimeSlotCreationResponse response = timeSlotService.addTime(request);
        // then
        assertThat(response.startAt()).isEqualTo("13:00");
    }

    @Test
    @DisplayName("이미 시간이 존재하는 경우, 추가할 때 예외가 발생한다.")
    void duplicatedTimeSlotTest() {
        // given
        TimeSlotDto dto = new TimeSlotDto(LocalTime.parse("13:00"));
        timeSlotRepository.create(dto);
        TimeSlotCreationRequest request = new TimeSlotCreationRequest("13:00");
        // when, then
        assertThatThrownBy(() -> timeSlotService.addTime(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간을 중복하여 등록할 수 없습니다.");
    }

    @Test
    @DisplayName("ID로 시간을 삭제한다.")
    void removeTime() {
        // given
        TimeSlot time = timeSlotRepository.create(
                new TimeSlotDto(LocalTime.parse("13:00"))
        );
        // when
        timeSlotService.removeTime(time.getId());
        List<TimeSlot> actual = timeSlotRepository.findAll();
        // then
        assertThat(actual).isEmpty();
    }
}
