package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.domain.Name;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

@DisplayName("예약 레포지토리")
@JdbcTest
@Import({ReservationRepository.class, TimeRepository.class})
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeRepository timeRepository;

    private Reservation savedReservation;

    @BeforeEach
    void init() {
        Time savedTime = timeRepository.save(new Time(null, LocalTime.parse("10:00")));

        Reservation reservation = new Reservation(
                null,
                new Name("브라운"), LocalDate.parse("2024-08-05"),
                new Time(savedTime.getId(), savedTime.getStartAt())
        );
        savedReservation = reservationRepository.save(reservation);
    }

    @DisplayName("id로 예약 정보를 조회한다.")
    @Test
    void findById() {
        // given
        Optional<Reservation> findReservation = reservationRepository.findById(savedReservation.getId());

        // when
        Reservation reservation = findReservation.get();

        // then
        assertAll(
                () -> assertThat(reservation.getId()).isEqualTo(savedReservation.getId()),
                () -> assertThat(reservation.getName()).isEqualTo(savedReservation.getName()),
                () -> assertThat(reservation.getDate()).isEqualTo(savedReservation.getDate()),
                () -> assertThat(reservation.getTime().getId()).isEqualTo(savedReservation.getTime().getId()),
                () -> assertThat(reservation.getTime().getStartAt()).isEqualTo(savedReservation.getTime().getStartAt())
        );
    }

    @DisplayName("모든 예약 정보를 조회한다.")
    @Test
    void findAll() {
        // given & when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("id로 예약 정보를 제거한다.")
    @Test
    void delete() {
        // given & when
        reservationRepository.deleteById(savedReservation.getId());

        // then
        assertThat(reservationRepository.findById(savedReservation.getId()).isEmpty()).isTrue();
    }
}
