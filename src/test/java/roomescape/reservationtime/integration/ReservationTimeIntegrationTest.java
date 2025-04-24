package roomescape.reservationtime.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;
import roomescape.reservationtime.service.ReservationTimeService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeIntegrationTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("모든 시간을 DB에서 조회한다.")
    void getReservationTimes() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);

        // when
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();

        // then
        assertThat(reservationTimes.getFirst()).isEqualTo(reservationTime);
    }

    @Test
    @DisplayName("시간을 DB에 저장한다.")
    void createReservationTime() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(
                LocalTime.of(10, 0)
        );

        // when
        ReservationTime saved = reservationTimeService.createReservationTime(request);

        // then
        ReservationTime found = reservationTimeRepository.findById(1L).get();
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("시간을 DB에서 삭제한다.")
    void deleteReservationTime() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);

        // when
        reservationTimeService.deleteReservationTime(1L);

        // then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
