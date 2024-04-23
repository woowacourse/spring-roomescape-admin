package roomescape.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.ReservationTestSetting.createReservation;
import static roomescape.ReservationTestSetting.isEqualsReservation;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcReservationRepositoryTest {

    @Autowired
    JdbcReservationRepository jdbcReservationRepository;

    @Test
    void 예약_저장() {
        //given
        Reservation reservation = createReservation();

        //when
        Long savedId = jdbcReservationRepository.save(reservation);
        Reservation savedReservation = jdbcReservationRepository.findById(savedId);

        //then
        Reservation reservationToCompare = new Reservation(savedId, reservation);
        assertThat(isEqualsReservation(reservationToCompare, savedReservation)).isTrue();
    }

    @Test
    void 전체_예약_조회() {
        //given
        Reservation reservation1 = createReservation();
        Reservation reservation2 = createReservation();
        Long savedReservation1Id = jdbcReservationRepository.save(reservation1);
        Long savedReservation2Id = jdbcReservationRepository.save(reservation2);

        //when
        List<Reservation> reservations = jdbcReservationRepository.findAll();

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
        Long savedId = jdbcReservationRepository.save(reservation);
        Reservation reservationToDelete = jdbcReservationRepository.findById(savedId);

        //when
        jdbcReservationRepository.delete(savedId);

        //then
        List<Reservation> reservations = jdbcReservationRepository.findAll();
        assertThat(reservations).doesNotContain(reservationToDelete);
    }
}
