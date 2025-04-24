package roomescape.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ReservationTimeServiceTest {

    private ReservationTimeDatabase reservationTimeDatabase;
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        this.reservationTimeDatabase = Mockito.mock(ReservationTimeDatabase.class);
        this.reservationTimeService = new ReservationTimeService(reservationTimeDatabase);
    }

    @Test
    void 시간을_저장하고_그_결과를_반환한다() {
        Mockito.when(reservationTimeDatabase.saveAndGetId(Mockito.any()))
                .thenReturn(1L);
        Mockito.when(reservationTimeDatabase.findById(1L))
                .thenReturn(Optional.of(new ReservationTimeEntity(3L, LocalTime.of(10, 0))));
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        final ReservationTimeResponse result = reservationTimeService.saveAndGet(request);

        assertThat(result).isEqualTo(new ReservationTimeResponse(3L, "10:00"));
    }

    @Test
    void 모든_시간을_반환한다() {
        Mockito.when(reservationTimeDatabase.findAll()).thenReturn(List.of(
                new ReservationTimeEntity(3L, LocalTime.of(10, 0)),
                new ReservationTimeEntity(4L, LocalTime.of(13, 0))
        ));

        final List<ReservationTimeResponse> result = reservationTimeService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(new ReservationTimeResponse(3L, "10:00"));
        assertThat(result.get(1)).isEqualTo(new ReservationTimeResponse(4L, "13:00"));
    }

    @Test
    void ID를_통해_삭제한다() {
        Mockito.when(reservationTimeDatabase.findById(1L))
                .thenReturn(Optional.of(new ReservationTimeEntity(3L, LocalTime.of(10, 0))));

        assertThatCode(() -> reservationTimeService.deleteById(1L))
                .doesNotThrowAnyException();
    }
}
