package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TimeRepository timeRepository;

    private ReservationTime savedTime;

    @BeforeEach
    void beforeEach() {
        savedTime = timeRepository.add(new ReservationTime(null, LocalTime.of(10,0)));
    }

    @Test
    @DisplayName("전체 예약을 조회한다.")
    void findAllReservationsTest() {
        reservationRepository.add(new Reservation("브라운", LocalDate.of(2026, 8, 5), savedTime));
        reservationRepository.add(new Reservation("네오", LocalDate.of(2026, 8, 6), savedTime));

        List<Reservation> reservations = reservationRepository.findAllReservations();

        assertThat(reservations).hasSize(2);
    }

    @Test
    @DisplayName("예약을 추가하면 id가 부여된 객체가 반환된다.")
    void addTest() {
        Reservation reservation = new Reservation("네오", LocalDate.of(2026, 8, 6), savedTime);

        Reservation saved = reservationRepository.add(reservation);

        assertThat(saved.getName()).isEqualTo("네오");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void removeTest() {
        Reservation saved = reservationRepository.add(new Reservation("브라운", LocalDate.of(2026, 8, 5), savedTime));

        reservationRepository.remove(saved.getId());

        List<Reservation> reservations = reservationRepository.findAllReservations();
        assertThat(reservations).hasSize(0);
    }
}
