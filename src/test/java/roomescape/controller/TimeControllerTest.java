package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TestTimeServiceImpl;

class TimeControllerTest {

    @Test
    @DisplayName("모든 시간 목록을 조회한다")
    void read_all_times() {
        // given
        TimeController timeController = new TimeController(new TestTimeServiceImpl());

        // when
        ResponseEntity<List<TimeResponse>> response = timeController.readTimes();
        List<TimeResponse> times = response.getBody();
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertAll(
                () -> assertThat(times).isEmpty(),
                () -> assertThat(statusCode).isEqualTo(200)
        );
    }

    @Test
    @DisplayName("시간을 생성한다")
    void create_time() {
        // given
        String startAt = "10:00";
        TimeRequest timeRequest = new TimeRequest(startAt);
        TimeController timeController = new TimeController(new TestTimeServiceImpl());

        // when
        ResponseEntity<TimeResponse> response = timeController.createTime(timeRequest);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    @DisplayName("id에 해당하는 시간을 삭제한다")
    void delete_time() {
        // given
        String startAt = "10:00";
        TimeRequest timeRequest = new TimeRequest(startAt);
        TimeController timeController = new TimeController(new TestTimeServiceImpl());
        ResponseEntity<TimeResponse> createdResponse = timeController.createTime(timeRequest);
        Long id = createdResponse.getBody()
                .id();

        // when
        ResponseEntity<Void> response = timeController.deleteTime(id);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    @DisplayName("존재하지 않는 예약 삭제시 400 반환")
    void delete_time_when_not_exist_id() {
        // given
        TimeController timeController = new TimeController(new TestTimeServiceImpl());

        // when
        ResponseEntity<Void> response = timeController.deleteTime(2L);
        int statusCode = response.getStatusCode()
                .value();

        // then
        assertThat(statusCode).isEqualTo(400);
    }
}
