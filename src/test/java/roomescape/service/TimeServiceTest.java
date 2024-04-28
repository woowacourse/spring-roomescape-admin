package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import roomescape.service.dto.ReservationTimeAddRequest;
import roomescape.service.dto.ReservationTimeResponse;
import roomescape.dao.TimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(value = "classpath:data-reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TimeServiceTest {
    private final TimeService service;
    private final TimeDao timeDao;

    @Autowired
    public TimeServiceTest(TimeService timeService, TimeDao timeDao) {
        this.service = timeService;
        this.timeDao = timeDao;
    }

    @DisplayName("예약 가능 시간을 추가한다.")
    @Test
    void addReservationTimeTest() {
        int initialLastElementId = timeDao.findAll().size();
        ReservationTimeAddRequest request = new ReservationTimeAddRequest(LocalTime.NOON);
        ReservationTimeResponse response = service.addReservationTime(request);

        assertThat(response.id()).isEqualTo(initialLastElementId + 1);
    }

    @DisplayName("예약 가능 시간을 중복해서 추가하면 예외가 발생한다.")
    @Test
    void addDuplicatedReservationTimeTest() {
        LocalTime duplicatedTime = LocalTime.of(0, 30);
        ReservationTimeAddRequest request = new ReservationTimeAddRequest(duplicatedTime);

        assertThatThrownBy(() -> service.addReservationTime(request)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 등록된 시간입니다.");
    }

    @DisplayName("모든 예약 가능 시간을 조회한다.")
    @Test
    void findAllReservationTimesTest() {
        List<ReservationTimeResponse> times = service.findAllReservationTimes();
        assertThat(times).hasSize(2);
    }

    @DisplayName("해당 시간의 예약이 없다면 예약 가능 시간을 삭제한다.")
    @Test
    void removeReservationTimeTest() {
        service.removeReservationTime(2L);
        assertThat(timeDao.findById(2L)).isNotPresent();
    }

    @DisplayName("해당 시간의 예약이 있을 때 예약 가능 시간을 삭제하면 예외가 발생한다.")
    @Test
    void removeReservationTimeWhenReservationExistTest() {
        assertThatThrownBy(() -> service.removeReservationTime(1L)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 시간에 예약이 존재합니다.");
    }
}
