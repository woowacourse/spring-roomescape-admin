package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeRepository;
import roomescape.testRepository.FakeTimeRepository;

class TimeServiceTest {

    private TimeRepository timeRepository;
    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeRepository = new FakeTimeRepository();
        timeService = new TimeService(timeRepository);
    }

    @DisplayName("현재 등록되어있는 모든 시간을 조회한다.")
    @Test
    void getAllTimes() {
        // given
        timeRepository.save(ReservationTime.of(1L, LocalTime.of(10,0)));
        timeRepository.save(ReservationTime.of(2L, LocalTime.of(11,0)));
        timeRepository.save(ReservationTime.of(3L, LocalTime.of(12,0)));

        // when
        List<TimeResponse> allTimes = timeService.getAllTimes();

        // then
        assertAll(
                () -> assertThat(allTimes).hasSize(3),
                () -> assertThat(allTimes)
                        .extracting(TimeResponse::startAt)
                        .containsExactly("10:00", "11:00", "12:00")
        );
    }

    @DisplayName("요청을 받아 새로운 시간을 등록한다.")
    @Test
    void registerNewTime_withRequest() {
        // given
        TimeRequest timeRequest = new TimeRequest("10:00");

        // when
        TimeResponse timeResponse = timeService.registerNewTime(timeRequest);

        // then
        assertAll(
                () -> assertThat(timeResponse.id()).isEqualTo(1L),
                () -> assertThat(timeResponse.startAt()).isEqualTo("10:00")
        );
    }

    @DisplayName("시간 등록 요청의 time 형식이 유효하지 않은 형식일 때 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"100:00", "1:00", "10,00", "12:1"})
    void registerNewTimeFail_when_invalidFormattedTime(String startAt) {
        // given
        TimeRequest timeRequest = new TimeRequest(startAt);

        // when & then
        assertThatThrownBy(() ->timeService.registerNewTime(timeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 시간입니다: " + startAt);
    }

    @DisplayName("등록된 시간을 id로 제거한다.")
    @Test
    void deleteTime() {
        // given
        timeRepository.save(ReservationTime.of(1L, LocalTime.of(10,0)));
        assertThat(timeRepository.findAll()).hasSize(1);

        // when
        timeService.deleteTime(1L);

        // then
        assertThat(timeRepository.findAll()).hasSize(0);
    }

    @DisplayName("id로 ReservationTime 객체를 가져올 수 있다.")
    @Test
    void getReservationTime_byId() {
        // given
        timeRepository.save(ReservationTime.of(1L, LocalTime.of(10,0)));

        // when
        ReservationTime reservationTime = timeService.getTimeById(1L);

        // then
        assertAll(
                () -> assertThat(reservationTime.getId()).isEqualTo(1L),
                () ->assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10,0))
        );
    }

    @DisplayName("존재하지 않는 id로 객체를 가져오려고 할 때 예외가 발생한다.")
    @Test
    void getReservationTime_fail_when_nonExistId() {
        // then
        Long id = 1L;

        assertThatThrownBy(() -> timeService.getTimeById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("삭제하려는 id가 존재하지 않습니다, id: " + id);

    }
}
