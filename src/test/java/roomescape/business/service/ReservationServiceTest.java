package roomescape.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.entity.ReservationEntity;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ReservationServiceTest {

    private ReservationDatabase reservationDatabase;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        this.reservationDatabase = Mockito.mock(ReservationDatabase.class);
        this.reservationService = new ReservationService(reservationDatabase);
    }

    @Test
    void 예약을_저장하고_그_결과를_반환한다() {
        Mockito.when(reservationDatabase.saveAndGetId(Mockito.any()))
                .thenReturn(1L);
        Mockito.when(reservationDatabase.findById(1L))
                .thenReturn(Optional.of(new ReservationEntity(1L, "dompoo", LocalDate.of(2025, 5, 17), new ReservationTimeEntity(3L, LocalTime.of(10, 0)))));
        final ReservationCreateRequest request = new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), 1L);

        final ReservationResponse result = reservationService.saveAndGet(request);

        assertThat(result).isEqualTo(new ReservationResponse(1L, "dompoo", LocalDate.of(2025, 5, 17), new ReservationTimeResponse(3L, "10:00")));
    }

    @Test
    void 모든_예약을_반환한다() {
        Mockito.when(reservationDatabase.findAll()).thenReturn(List.of(
                new ReservationEntity(1L, "dompoo", LocalDate.of(2025, 5, 17), new ReservationTimeEntity(3L, LocalTime.of(10, 0))),
                new ReservationEntity(2L, "popo", LocalDate.of(2025, 5, 20), new ReservationTimeEntity(4L, LocalTime.of(13, 0)))
        ));

        final List<ReservationResponse> result = reservationService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(new ReservationResponse(1L, "dompoo", LocalDate.of(2025, 5, 17), new ReservationTimeResponse(3L, "10:00")));
        assertThat(result.get(1)).isEqualTo(new ReservationResponse(2L, "popo", LocalDate.of(2025, 5, 20), new ReservationTimeResponse(4L, "13:00")));
    }

    @Test
    void ID를_통해_삭제한다() {
        Mockito.when(reservationDatabase.findById(1L))
                .thenReturn(Optional.of(new ReservationEntity(1L, "dompoo", LocalDate.of(2025, 5, 17), new ReservationTimeEntity(3L, LocalTime.of(10, 0)))));

        assertThatCode(() -> reservationService.deleteById(1L))
                .doesNotThrowAnyException();
    }
}
