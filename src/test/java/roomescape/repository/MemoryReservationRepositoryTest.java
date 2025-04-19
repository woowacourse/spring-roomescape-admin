package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;

class MemoryReservationRepositoryTest {

    @DisplayName("예약 목록을 조회할 수 있다.")
    @Test
    void findAllTest() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusDays(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();
        Reservation reservation = new Reservation(id, name, reservationDate, reservationTime);
        MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();
        Reservation addedReservation = memoryReservationRepository.add(reservation);

        // when
        List<Reservation> reservations = memoryReservationRepository.findAll();

        // then
        assertAll(
                () -> assertThat(reservations)
                        .contains(addedReservation),
                () -> assertThat(reservations)
                        .hasSize(1)
        );
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void addTest() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusDays(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();
        Reservation reservation = new Reservation(id, name, reservationDate, reservationTime);
        MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();

        // when
        Reservation addedReservation = memoryReservationRepository.add(reservation);

        // then
        assertAll(
                () -> assertThat(reservation.getName())
                        .isEqualTo(addedReservation.getName()),
                () -> assertThat(reservation.getDate())
                        .isEqualTo(addedReservation.getDate()),
                () -> assertThat(reservation.getTime())
                        .isEqualTo(addedReservation.getTime())
        );
    }

    @DisplayName("id로 예약을 삭제할 수 있다.")
    @Test
    void removeByIdTest() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusDays(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();
        Reservation reservation = new Reservation(id, name, reservationDate, reservationTime);
        MemoryReservationRepository memoryReservationRepository = new MemoryReservationRepository();
        Reservation addedReservation = memoryReservationRepository.add(reservation);

        // when & then
        assertAll(
                () -> assertThatCode(() -> memoryReservationRepository.removeById(addedReservation.getId()))
                        .doesNotThrowAnyException(),
                () -> assertThat(memoryReservationRepository.findAll())
                        .isEmpty()
        );
    }
}
