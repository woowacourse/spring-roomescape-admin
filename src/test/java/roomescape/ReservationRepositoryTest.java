package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationRepositoryTest {

    @Test
    @DisplayName("예약 조회 시 불변 리스트를 반환한다.")
    void findAll_then_return_unmodifiableList() {
        ReservationRepository reservationRepository = new ReservationRepository();
        List<Reservation> result = reservationRepository.findAll();

        assertThatThrownBy(() -> result.add(new Reservation(1L, "name", LocalDate.now(), LocalTime.now())))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
