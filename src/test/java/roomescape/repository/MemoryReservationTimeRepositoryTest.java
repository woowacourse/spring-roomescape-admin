package roomescape.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemoryReservationTimeRepositoryTest {

    ReservationTimeRepository reservationTimeRepository;

    @DisplayName("Reservation Time을 저장할 수 있다")
    @Test
    void saveReservationTimeTest() {
        reservationTimeRepository = new MemoryReservationTimeRepository(new ArrayList<>());
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());

        ReservationTime savedReservation = reservationTimeRepository.save(reservationTime);
        Long saveId = savedReservation.id();

        assertThat(saveId).isEqualTo(1L);
    }

    @DisplayName("저장된 Reservation Time을 불러올 수 있다")
    @Test
    void findAllReservationTimeTest() {
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.now());
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.now());
        ReservationTime reservationTime3 = new ReservationTime(3L, LocalTime.now());
        reservationTimeRepository = new MemoryReservationTimeRepository(List.of(reservationTime1, reservationTime2, reservationTime3));

        List<ReservationTime> allReservationTimes = reservationTimeRepository.findAll();

        Long reservationTimeId1 = allReservationTimes.get(0).id();
        Long reservationTimeId2 = allReservationTimes.get(1).id();
        Long reservationTimeId3 = allReservationTimes.get(2).id();

        assertAll(
                () -> assertThat(reservationTimeId1).isEqualTo(1L),
                () -> assertThat(reservationTimeId2).isEqualTo(2L),
                () -> assertThat(reservationTimeId3).isEqualTo(3L),
                () -> assertThat(allReservationTimes).hasSize(3)
        );
    }

    @DisplayName("존재하는 Id의 Reservation Time을 삭제할 수 있다")
    @Test
    void deleteReservationTimeTest() {
        ArrayList<ReservationTime> reservationTimes = new ArrayList<>();
        reservationTimes.add(new ReservationTime(1L, LocalTime.now()));
        reservationTimeRepository = new MemoryReservationTimeRepository(reservationTimes);

        Long deleteId = 1L;

        assertDoesNotThrow(() -> reservationTimeRepository.deleteById(deleteId));
    }

    @DisplayName("존재하지 않은 Id의 Reservation Time을 삭제할 수 없다")
    @Test
    void deleteInvalidReservationTimeTest() {
        ArrayList<ReservationTime> reservationTimes = new ArrayList<>();
        reservationTimes.add(new ReservationTime(1L, LocalTime.now()));
        reservationTimeRepository = new MemoryReservationTimeRepository(reservationTimes);

        Long deleteId = 5L;

        Assertions.assertThatThrownBy(() -> reservationTimeRepository.deleteById(deleteId))
                .isInstanceOf(IllegalStateException.class);

    }
}
