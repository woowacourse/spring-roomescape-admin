package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponse;
import roomescape.fixture.TextFixture;
import roomescape.repository.FakeReservationRepository;
import roomescape.repository.ReservationRepository;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @BeforeEach
    void setUp() {
        reservationRepository.save(TextFixture.makeReservation(1L, 1L));
    }

    @Test
    void 모든_예약을_조회한다() {
        // Given

        // When
        List<ReservationResponse> responses = reservationService.findAll();

        // Then
        assertThat(responses.size()).isEqualTo(1);
    }

    @Test
    void 예약을_저장한다() {
        // Given

        // When
        reservationService.add(TextFixture.makeReservation(2L, 2L));

        // Then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

    @Test
    void 예약을_삭제한다() {
        // Given

        // When
        reservationService.delete(1L);

        // Then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.isEmpty()).isTrue();
    }
}
