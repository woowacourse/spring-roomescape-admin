package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
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

    @BeforeEach
    void setup() {
        ReservationTime nonIdReservationTime = ReservationTime.create("11:00");
        reservationTimeRepository.save(nonIdReservationTime);
    }

    @Test
    @DisplayName("예약 저장")
    void save_test() {
        // given
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("쿠다", "2023-08-06", 1L);

        // when
        ReservationResponseDto result = reservationService.save(reservationRequestDto);

        // then
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo("쿠다");
    }

}
