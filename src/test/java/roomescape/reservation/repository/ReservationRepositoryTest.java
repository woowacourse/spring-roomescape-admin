package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("전체 예약을 리스트로 조회한다")
    void 조회_테스트() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(10, 0)).build());
        reservationRepository.saveReservation(
                Reservation.builder().name("포비").date(LocalDate.now()).time(time).build());

        // when
        List<Reservation> reservations = reservationRepository.findAllReservations();

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getTime().getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("예약을 저장하면 생성된 ID를 포함한 객체를 반환한다")
    void 저장_테스트() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(14, 0)).build());
        Reservation reservation = Reservation.builder()
                .name("이산").date(LocalDate.now()).time(time).build();

        // when
        Reservation saved = reservationRepository.saveReservation(reservation);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("삭제 시 영향받은 행의 개수를 반환한다")
    void 삭제_테스트() {
        // given
        ReservationTime time = reservationTimeRepository.saveReservationTime(
                ReservationTime.builder().startAt(LocalTime.of(20, 0)).build());
        Reservation saved = reservationRepository.saveReservation(
                Reservation.builder().name("이산").date(LocalDate.now()).time(time).build());

        // when
        int deletedRows = reservationRepository.deleteById(saved.getId());

        // then
        assertThat(deletedRows).isEqualTo(1);
    }
}
