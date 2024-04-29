package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeService reservationTimeService;


    @DisplayName("Request 객체를 이용하여 예약을 저장한다.")
    @Test
    void addReservation() {
        // given
        reservationTimeService.addReservationTime(new ReservationTimeRequest("10:00"));
        ReservationRequest request = new ReservationRequest("상돌", "2024-04-25", 1L);

        // when
        Reservation saved = reservationService.addReservation(request);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("상돌");
        assertThat(saved.getDate()).isEqualTo("2024-04-25");
        assertThat(saved.getTime().getId()).isEqualTo(1L);
        assertThat(saved.getTime().getStartAt()).isEqualTo("10:00");
    }
}
