package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationTimeSaveDto;
import roomescape.testutil.ReservationTimeMemoryDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void init() {
        final ReservationTimeDao reservationTimeDao = new ReservationTimeMemoryDao();
        reservationTimeDao.save(new ReservationTimeSaveDto("11:00"));
        reservationTimeDao.save(new ReservationTimeSaveDto("12:00"));
        reservationTimeService = new ReservationTimeService(reservationTimeDao);
    }

    @DisplayName("예약 시간 목록 조회")
    @Test
    void getTimes() {
        final List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.getTimes();
        assertThat(reservationTimeResponses.size()).isEqualTo(2);
    }

    @DisplayName("예약 시간 추가")
    @Test
    void saveTime() {
        final ReservationTimeSaveRequest reservationTimeSaveRequest = new ReservationTimeSaveRequest("01:00");
        final ReservationTimeResponse reservationTimeResponse = reservationTimeService.saveTime(reservationTimeSaveRequest);
        assertThat(reservationTimeResponse).isEqualTo(new ReservationTimeResponse(3L, "01:00"));
    }

    @DisplayName("예약 시간 삭제")
    @Test
    void deleteTime() {
        reservationTimeService.deleteTime(1L);
        assertThat(reservationTimeService.getTimes().size()).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 예약 시간 삭제")
    @Test
    void deleteTimeNotFound() {
        assertThatThrownBy(() -> reservationTimeService.deleteTime(3L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

