package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.ReservationTestSetting.createReservation;
import static roomescape.ReservationTestSetting.isEqualsReservation;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.MemoryReservationRepository;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemoryReservationRepositoryTest {

    MemoryReservationRepository memoryReservationRepository = MemoryReservationRepository.getInstance();

    @AfterEach
    void afterEach() {
        List<Reservation> reservations = memoryReservationRepository.findAll();
        if (!reservations.isEmpty()) {
            List<Long> reservationIds = reservations.stream()
                    .map(Reservation::getId)
                    .toList();
            reservationIds.forEach(id -> memoryReservationRepository.delete(id));
        }
    }

    @Test
    void 예약_저장() {
        //given
        Reservation reservation = createReservation();

        //when
        Long savedId = memoryReservationRepository.save(reservation);
        Reservation savedReservation = memoryReservationRepository.findById(savedId);

        //then
        Reservation reservationToCompare = new Reservation(savedId, reservation);
        assertThat(isEqualsReservation(reservationToCompare, savedReservation)).isTrue();
    }

    @Test
    void 전체_예약_조회() {
        //given
        Reservation reservation1 = createReservation();
        Reservation reservation2 = createReservation();
        Long savedReservation1Id = memoryReservationRepository.save(reservation1);
        Long savedReservation2Id = memoryReservationRepository.save(reservation2);

        //when
        List<Reservation> reservations = memoryReservationRepository.findAll();

        //then
        Reservation reservationToCompare1 = new Reservation(savedReservation1Id, reservation1);
        Reservation reservationToCompare2 = new Reservation(savedReservation2Id, reservation2);
        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(isEqualsReservation(reservationToCompare1, reservations.get(0))).isTrue(),
                () -> assertThat(isEqualsReservation(reservationToCompare2, reservations.get(1))).isTrue()
        );
    }

    @Test
    void 예약_삭제() {
        //given
        Reservation reservation = createReservation();
        Long savedId = memoryReservationRepository.save(reservation);
        Reservation reservationToDelete = memoryReservationRepository.findById(savedId);

        //when
        memoryReservationRepository.delete(savedId);

        //then
        List<Reservation> reservations = memoryReservationRepository.findAll();
        assertThat(reservations).doesNotContain(reservationToDelete);
    }
}
