package roomescape.time.service;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.time.dao.FakeReservationTimeDao;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.utils.ReservationTimeMapper;

import static org.assertj.core.api.Assertions.*;

class ReservationReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeDao(), new ReservationTimeMapper());
    }

    @Test
    void 데이터를_전달받아_시간을_추가한다() {
        // Given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(
                LocalTime.of(11, 30)
        );

        // When & Then
        assertThat(reservationTimeService.addTime(reservationTimeRequest).id())
                .isNotNull();
    }

    @Test
    void 저장된_모든_시간을_반환한다() {
        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationTimeService.findAllTimes());
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재한다면_삭제한다() {
        // Given
        final long id = 1L;
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(
                LocalTime.of(12, 10)
        );
        reservationTimeService.addTime(reservationTimeRequest);

        // When & Then
        assertThatNoException()
                .isThrownBy(() -> reservationTimeService.deleteTimeById(id));
    }

    @Test
    void ID를_전달받아_DB에_해당_ID가_존재하지_않는다면_예외가_발생한다() {
        // Given
        final long id = 1L;

        // When & Then
        assertThatThrownBy(() -> reservationTimeService.deleteTimeById(id))
                .isInstanceOf(NoSuchElementException.class);
    }
}
