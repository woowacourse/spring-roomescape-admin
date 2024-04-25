package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @DisplayName("Request 객체를 이용하여 예약 시간을 저장한다.")
    @Test
    void addReservationTime() {
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:00");

        // when
        ReservationTime saved = reservationTimeService.addReservationTime(reservationTimeRequest);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getStartAt()).isEqualTo("10:00");
    }
}
