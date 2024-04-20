package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.InMemoryReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeDto;

class ReservationTimeServiceTest {

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new InMemoryReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    @DisplayName("모든 시간을 조회한다.")
    void getAllTimesTest() {
        // given
        Stream.of("13:00", "14:00", "15:00")
                .map(LocalTime::parse)
                .map(ReservationTimeDto::new)
                .forEach(reservationTimeRepository::create);
        // when
        List<ReservationTimeResponse> actual = reservationTimeService.getAllTimes();
        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void addTimeTest() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest("13:00");
        // when
        ReservationTimeResponse response = reservationTimeService.addTime(request);
        // then
        assertThat(response.startAt()).isEqualTo("13:00");
    }

    @Test
    @DisplayName("ID로 시간을 삭제한다.")
    void removeTime() {
        // given
        ReservationTime time = reservationTimeRepository.create(
                new ReservationTimeDto(LocalTime.parse("13:00"))
        );
        // when
        reservationTimeService.removeTime(time.getId());
        List<ReservationTime> actual = reservationTimeRepository.findAll();
        // then
        assertThat(actual).isEmpty();
    }
}
