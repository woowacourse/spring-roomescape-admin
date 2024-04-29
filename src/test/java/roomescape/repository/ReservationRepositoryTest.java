package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.DAY_AFTER_TOMORROW;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_ID_0300;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private GameTimeRepository gameTimeRepository;
    private ReservationTime savedTime0300;

    @BeforeEach
    void setUp() {
        savedTime0300 = gameTimeRepository.save(GAME_TIME_WITH_ID_0300);
    }

    @DisplayName("전체 예약을 조회할 수 있다")
    @Test
    void readAllTest() {
        Reservation reservation1 = new Reservation(null, "리비", DAY_AFTER_TOMORROW, savedTime0300);
        Reservation reservation2 = new Reservation(null, "웨지", DAY_AFTER_TOMORROW, savedTime0300);

        Reservation libiReservation = reservationRepository.save(reservation1);
        Reservation wedgeReservation = reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.readAll();

        assertThat(reservationRepository.readAll())
                .containsExactly(libiReservation, wedgeReservation);
    }

    @DisplayName("예약 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        Reservation reservation = new Reservation("폭포", DAY_AFTER_TOMORROW, savedTime0300);
        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved).isEqualTo(new Reservation(saved.getId(), reservation));
    }

    @DisplayName("같은 예약이 존재하는지 알 수 있다")
    @Test
    void existSameTimeReservationTest() {
        Reservation reservation = new Reservation("웨지", DAY_AFTER_TOMORROW, savedTime0300);
        reservationRepository.save(reservation);
        Reservation conflictReservation = new Reservation("리비", DAY_AFTER_TOMORROW, savedTime0300);

        assertThat(reservationRepository.existByReservationTime(DAY_AFTER_TOMORROW, savedTime0300.getId())).isTrue();
    }

    @DisplayName("예약 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        Reservation reservation = new Reservation(null, "리비", DAY_AFTER_TOMORROW, savedTime0300);
        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved.getName()).isEqualTo("리비");
    }

    @DisplayName("예약 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        Reservation reservation = new Reservation(null, "리비", DAY_AFTER_TOMORROW, savedTime0300);

        Reservation saved = reservationRepository.save(reservation);
        reservationRepository.deleteById(saved.getId());

        assertThatThrownBy(() -> reservationRepository.findById(saved.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
