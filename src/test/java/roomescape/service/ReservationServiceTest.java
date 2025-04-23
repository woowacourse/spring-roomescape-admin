package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.StubReservationRepository;
import roomescape.controller.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

class ReservationServiceTest {

    private ReservationService service;

    @Test
    void 모든_예약_가져오기() {
        // given
        final Reservation r1 = new Reservation(1L, "테스트", LocalDate.of(2025, 5, 11),
                new ReservationTime(1L, LocalTime.of(14, 0)));
        final Reservation r2 = new Reservation(2L, "테스트2", LocalDate.of(2025, 6, 11),
                new ReservationTime(2L, LocalTime.of(13, 0)));
        StubReservationRepository repo = new StubReservationRepository(false, null, List.of(r1, r2));
        service = new ReservationService(repo, null);

        // when
        // then
        assertThat(service.getReservations()).hasSize(2)
                .containsExactly(new ReservationResponse(r1), new ReservationResponse(r2));
    }

    @Test
    void 해당_날짜와_시간에_이미_예약_존재하면_예외() {
        // given
        StubReservationRepository repo = new StubReservationRepository(true, null, null);
        service = new ReservationService(repo, null);

        ReservationRequest request = new ReservationRequest("철원", LocalDate.of(2025, 4, 21), 2L);

        // when
        // then
        assertThatThrownBy(() -> service.saveReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 시간은 이미 예약되어있습니다.");
    }

    @Test
    void 해당_날짜와_시간에_예약이_존재하지_않으면_예약_생성() {
        // given
        final Reservation r1 = new Reservation(1L, "테스트", LocalDate.of(2025, 5, 11),
                new ReservationTime(1L, LocalTime.of(14, 0)));
        StubReservationRepository repo = new StubReservationRepository(false, r1, null);
        service = new ReservationService(repo, null);

        ReservationRequest request = new ReservationRequest("철원", LocalDate.of(2025, 4, 21), 1L);

        // when
        // then
        assertThatCode(() -> service.saveReservation(request))
                .doesNotThrowAnyException();
    }
}
