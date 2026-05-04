package roomescape.repository.h2;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.TestFixture.createReservation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@JdbcTest
@Import({
        H2ReservationRepository.class,
        H2ReservationTimeRepository.class
})
class H2ReservationRepositoryTest {
    private static final ReservationTime RESERVATION_TIME = new ReservationTime(1L, LocalTime.of(10, 0));

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeAll
    static void setUp(@Autowired ReservationTimeRepository reservationTimeRepository) {
        reservationTimeRepository.save(RESERVATION_TIME);
    }

    @Test
    @DisplayName("예약 목록을 조회하면 저장한 객체와 동등하다.")
    void findAll() {
        assertThat(reservationRepository.findAll())
                .isEmpty();

        List<Reservation> savedReservations = new ArrayList<>();
        savedReservations.add(reservationRepository.save(createReservation()));
        savedReservations.add(reservationRepository.save(createReservation()));

        assertThat(reservationRepository.findAll())
                .hasSize(savedReservations.size())
                .containsAll(savedReservations);
    }

    @Test
    @DisplayName("reservationId 예약을 조회한다.")
    void findById() {
        Reservation saved = reservationRepository.save(createReservation());
        assertThat(reservationRepository.findById(saved.getId()))
                .isEqualTo(saved);
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        assertThat(reservationRepository.save(createReservation()).getId())
                .isNotNull();
    }

    @Test
    @DisplayName("저장된 예약을 삭제하면, 존재하지 않는다.")
    void delete() {
        //given
        Reservation saved = reservationRepository.save(createReservation());
        assertThat(reservationRepository.isExists(saved.getId())).isTrue();

        //when
        reservationRepository.delete(saved.getId());

        //then
        assertThat(reservationRepository.isExists(saved.getId())).isFalse();
    }

    @Test
    @DisplayName("reservationId 예약이 존재하는지 확인한다.")
    void isExists() {
        Reservation save = reservationRepository.save(createReservation());
        assertThat(reservationRepository.isExists(save.getId())).isTrue();
    }

    @Test
    @DisplayName("timeId 예약이 존재하는지 확인한다.")
    void isExistsByTimeId() {
        assertThat(reservationRepository.isExistsByTimeId(createReservation().getTime().getId())).isFalse();
        reservationRepository.save(createReservation());
        assertThat(reservationRepository.isExistsByTimeId(createReservation().getTime().getId())).isTrue();
    }
}
