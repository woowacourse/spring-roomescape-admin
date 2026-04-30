package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.service.stub.StubReservationTimeRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.exception.ReservationTimeException;
import roomescape.time.service.ReservationTimeService;


class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup() {
        reservationTimeService = new ReservationTimeService(new StubReservationTimeRepository());
    }

    @Test
    @DisplayName("예약 시간 저장")
    void save_test() {
        //given
        LocalTime time = LocalTime.parse("11:00");

        //when
        ReservationTime result = reservationTimeService.save(time);
        ReservationTime saved = reservationTimeService.getById(result.getId());

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStartAt()).isEqualTo(time);

        assertThat(result.getId()).isEqualTo(saved.getId());
        assertThat(result.getStartAt()).isEqualTo(saved.getStartAt());
    }

    @Test
    @DisplayName("예약 시간 단일 조회 id 없음 예외")
    void findById_null_search_test() {
        //given & when & then
        assertThatThrownBy(() -> reservationTimeService.getById(99L))
                .isInstanceOf(ReservationTimeException.class)
                .hasMessageContaining("찾는 예약 시간이 없습니다.");
    }

}
