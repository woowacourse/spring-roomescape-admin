package roomescape.repository;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.exception.ReservationNotFoundException;
import roomescape.model.Reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
    }

    @Test
    void 예약을_저장하면_1부터_시작하는_ID가_부여된다() {
        Reservation first = reservationRepository.save("브라운", "2023-08-05", "15:40");
        Reservation second = reservationRepository.save("코니", "2023-08-06", "16:00");

        assertThat(first.id()).isEqualTo(1L);
        assertThat(second.id()).isEqualTo(2L);
    }

    @Test
    void 저장된_예약을_전체_조회할_수_있다() {
        reservationRepository.save("브라운", "2023-08-05", "15:40");
        reservationRepository.save("코니", "2023-08-06", "16:00");

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .extracting(Reservation::name)
                .containsExactly("브라운", "코니");
    }

    @Test
    void 존재하는_ID로_예약을_삭제할_수_있다() {
        Reservation reservation = reservationRepository.save("브라운", "2023-08-05", "15:40");

        reservationRepository.delete(reservation.id());

        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_ID로_삭제하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationRepository.delete(1L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("Reservation not found: 1");
    }
}
