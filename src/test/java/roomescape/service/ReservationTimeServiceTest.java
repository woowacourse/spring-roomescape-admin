package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void 중복된_시간을_저장하면_예외가_발생한다() {
        // given
        ReservationTime existTime = new ReservationTime(null, "10:00");
        reservationTimeService.save(existTime);

        // when & then
        ReservationTime newTime = new ReservationTime(null, "10:00");
        assertThatThrownBy(() -> reservationTimeService.save(newTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 존재하는 예약시간입니다.");
    }
}
