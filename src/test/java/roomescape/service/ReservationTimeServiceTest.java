package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;
import roomescape.time.service.ReservationTimeService;

@SpringBootTest
@Transactional
public class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setup() {
        ReservationTime nonIdReservationTime = ReservationTime.create("10:00");
        reservationTimeRepository.save(nonIdReservationTime);
    }

    @Test
    @DisplayName("예약 시간 저장")
    void save_test() {
        //given
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto("11:00");

        //when
        ReservationTimeResponseDto result = reservationTimeService.save(reservationTimeRequestDto);

        //then
        assertThat(result.id()).isNotNull();
        assertThat(result.startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("예약 시간 저장 중복 예외")
    void save_startAt_duplicate_test() {
        //given
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto("10:00");

        //when & then
        assertThatThrownBy(() -> reservationTimeService.save(reservationTimeRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 시간 중복 추가는 불가능합니다.");
    }

    @Test
    @DisplayName("예약 시간 단일 조회 id 없음 예외")
    void findById_null_search_test() {
        //given & when & then
        assertThatThrownBy(() -> reservationTimeService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 찾는 예약 시간이 없습니다.");
    }
}
