package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
    }

    @Test
    void 예약을_저장하면_id가_부여된다() {
        // given
        Reservation reservation = Reservation.createWithoutId(
            "보예",
            LocalDate.of(2023, 8, 5),
            LocalTime.of(15, 40)
        );

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertAll(
            () -> assertThat(savedReservation.getId()).isEqualTo(1L),
            () -> assertThat(savedReservation.getName()).isEqualTo("보예"),
            () -> assertThat(savedReservation.getDate()).isEqualTo(LocalDate.of(2023, 8, 5)),
            () -> assertThat(savedReservation.getTime()).isEqualTo(LocalTime.of(15, 40)),
            () -> assertThat(savedReservation.isDeleted()).isFalse()
        );
    }

    @Test
    void 예약은_저장된_순서대로_조회된다() {
        // given
        Reservation firstReservation = Reservation.createWithoutId(
            "보예",
            LocalDate.of(2023, 8, 5),
            LocalTime.of(15, 40)
        );
        Reservation secondReservation = Reservation.createWithoutId(
            "수민",
            LocalDate.of(2023, 8, 6),
            LocalTime.of(16, 0)
        );

        // when
        reservationRepository.save(firstReservation);
        reservationRepository.save(secondReservation);
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertAll(
            () -> assertThat(reservations).hasSize(2),
            () -> assertThat(reservations).extracting(Reservation::getId).containsExactly(1L, 2L),
            () -> assertThat(reservations).extracting(Reservation::getName).containsExactly("보예", "수민")
        );
    }

    @Test
    void 삭제된_예약은_전체_조회에서_제외된다() {
        // given
        Reservation activeReservation = reservationRepository.save(
            Reservation.createWithoutId(
                "보예",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40))
        );
        Reservation deletedReservation = reservationRepository.save(
            Reservation.createWithoutId(
                "수민",
                LocalDate.of(2023, 8, 6),
                LocalTime.of(16, 0))
        );

        // when
        deletedReservation.delete();
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertAll(
            () -> assertThat(reservations).hasSize(1),
            () -> assertThat(reservations.getFirst().getId()).isEqualTo(activeReservation.getId())
        );
    }

    @Test
    void id로_예약을_조회한다() {
        // given
        Reservation savedReservation = reservationRepository.save(
            Reservation.createWithoutId(
                "보예",
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40))
        );

        // when
        Optional<Reservation> foundReservation = reservationRepository.findReservation(savedReservation.getId());

        // then
        assertAll(
            () -> assertThat(foundReservation).isPresent(),
            () -> assertThat(foundReservation.get().getName()).isEqualTo("보예")
        );
    }
}
