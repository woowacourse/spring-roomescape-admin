package roomescape.business.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import roomescape.infra.ReservationDatabase;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.infra.entity.ReservationTimeEntity;
import roomescape.presentation.dto.request.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class ReservationServiceTransactionTest {

    @MockitoSpyBean
    private ReservationDatabase reservationDatabase;
    @MockitoSpyBean
    private ReservationTimeDatabase reservationTimeDatabase;
    @Autowired
    private ReservationService reservationService;

    @Test
    void 예약을_저장하고_결과를_반환하는_중에_예외가_터지면_저장도_취소된다() {
        Mockito.doThrow(new IllegalArgumentException())
                .when(reservationDatabase)
                .findById(Mockito.anyLong());

        reservationTimeDatabase.saveAndGetId(new ReservationTimeEntity(null, LocalTime.of(10, 0)));
        final ReservationCreateRequest request = new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), 1L);

        assertThatThrownBy(() -> reservationService.saveAndGet(request));
        assertThat(reservationDatabase.findAll()).hasSize(0);
    }
}
