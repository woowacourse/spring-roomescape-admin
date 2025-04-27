package roomescape.business.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import roomescape.infra.ReservationTimeDatabase;
import roomescape.presentation.dto.request.ReservationTimeCreateRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext
public class ReservationTimeServiceTransactionTest {

    @MockitoSpyBean
    private ReservationTimeDatabase reservationTimeDatabase;
    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void 예약_시간을_저장하고_결과를_반환하는_중에_예외가_터지면_저장도_취소된다() {
        Mockito.doThrow(new IllegalArgumentException())
                .when(reservationTimeDatabase)
                .findById(Mockito.anyLong());

        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        assertThatThrownBy(() -> reservationTimeService.saveAndGet(request));
        assertThat(reservationTimeDatabase.findAll()).hasSize(0);
    }
}
