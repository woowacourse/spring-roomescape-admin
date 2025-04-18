package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
    }

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given

        // when

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservationRepository.add("체체", LocalDate.now(), LocalTime.now().plusHours(1));

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given

        // when
        reservationRepository.add("체체", LocalDate.now(), LocalTime.now().plusHours(1));
        List<Reservation> reservations = reservationRepository.findAll();
        Reservation findReservation = reservations.getFirst();
        reservationRepository.remove(findReservation.getId());

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(0);
    }

}
