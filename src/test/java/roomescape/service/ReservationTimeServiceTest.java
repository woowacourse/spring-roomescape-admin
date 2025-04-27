package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.fixture.TextFixture;
import roomescape.repository.FakeReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationTimeServiceTest {

    private final ReservationTimeRepository reservationTimeRepository = new FakeReservationTimeRepository();
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);

    @BeforeEach
    void setUp() {
        reservationTimeRepository.save(TextFixture.makeReservationTime(1L));
    }

    @Test
    void 예약_시간을_추가한다() {
        // Given
        ReservationTime reservationTime = TextFixture.makeReservationTime(2L);

        // When
        reservationTimeService.add(reservationTime);

        // Then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @Test
    void 전체_예약_시간을_조회한다() {
        // Given

        // When
        List<ReservationTime> reservationTimes = reservationTimeService.findAll();

        // Then
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    void 예약_시간을_삭제한다() {
        // Given

        // When
        reservationTimeService.delete(1L);

        // Then
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertThat(reservationTimes.isEmpty()).isEqualTo(true);
    }
}
