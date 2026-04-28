package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationRepositoryTest {

    @Test
    void 예약_조회_시_불변_리스트를_반환한다() {
        ReservationRepository reservationRepository = new ReservationRepository();
        List<Reservation> result = reservationRepository.findAll();

        assertThatThrownBy(() -> result.add(new Reservation(1L, "name", LocalDate.now(), LocalTime.now())))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
