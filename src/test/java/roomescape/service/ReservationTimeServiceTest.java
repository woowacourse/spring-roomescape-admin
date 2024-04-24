package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void createReservationTime() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("10:09"));

        final ReservationTimeResponse actual = reservationTimeService.createReservationTime(request);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getStartAt()).isEqualTo("10:09")
        );
    }
    
    @Test
    @DisplayName("예약 시간 목록을 조회한다.")
    void getAllReservationTimes() {
        createInitialReservationTime();

        final List<ReservationTimeResponse> actual = reservationTimeService.getAllReservationTimes();

        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("예약 시간을 취소한다.")
    void deleteReservationTime() {
        final Long id = createInitialReservationTime();

        reservationTimeService.deleteReservationTime(id);
        final List<ReservationTime> actual = reservationTimeRepository.findAll();

        assertThat(actual).hasSize(0);
    }

    private Long createInitialReservationTime() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("10:09"));
        final ReservationTime reservationTime = request.toReservationTime();
        return reservationTimeRepository.save(reservationTime);
    }
}
