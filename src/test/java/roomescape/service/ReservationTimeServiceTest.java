package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@ExtendWith(MockitoExtension.class)
class ReservationTimeServiceTest {

    @Mock
    private ReservationTimeDao reservationTimeDao;

    @InjectMocks
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("전체 예약 시간 목록을 조회한다.")
    void should_return_all_reservation_times() {
        final List<ReservationTime> times = List.of(
                new ReservationTime(1L, "10:00"),
                new ReservationTime(2L, "11:00")
        );
        given(reservationTimeDao.getTimes()).willReturn(times);

        final List<ReservationTime> result = reservationTimeService.getTimes();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간을 생성하고 반환한다.")
    void should_create_and_return_reservation_time() {
        final ReservationTimeRequest request = new ReservationTimeRequest("10:00");
        given(reservationTimeDao.insertAndGetId(request)).willReturn(1L);

        final ReservationTime result = reservationTimeService.createTime(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void should_delete_reservation_time() {
        reservationTimeService.deleteTime(1L);

        then(reservationTimeDao).should(times(1)).delete(1L);
    }
}
