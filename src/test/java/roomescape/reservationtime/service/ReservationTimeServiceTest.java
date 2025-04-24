package roomescape.reservationtime.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.dto.repository.InMemoryReservationTimeRepository;
import roomescape.reservationtime.dto.repository.ReservationTimeRepository;
import roomescape.reservationtime.dto.request.ReservationTimeCreateRequest;

class ReservationTimeServiceTest {

    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new InMemoryReservationTimeRepository();
        reservationTimeService = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    void createReservationTime_shouldThrowException_IfDuplicated() {
        LocalTime time = LocalTime.of(1, 1);
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(time);

        reservationTimeService.create(request);

        assertThatThrownBy(() -> reservationTimeService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

}