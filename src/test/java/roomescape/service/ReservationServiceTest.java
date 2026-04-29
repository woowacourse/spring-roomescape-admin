package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.controller.dto.ReservationRequest;
import roomescape.reservation.controller.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약 저장")
    void save_test() {
        // given
        ReservationTime nonIdReservationTime = ReservationTime.create(LocalTime.parse("10:00"));
        ReservationTime reservationTime = reservationTimeRepository.save(nonIdReservationTime);
        ReservationRequest reservationRequest = new ReservationRequest("쿠다", LocalDate.parse("2023-08-06"), reservationTime.getId());

        // when
        ReservationResponse result = reservationService.save(reservationRequest);

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo("쿠다");
    }

}
