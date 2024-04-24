package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.DATE_2024_04_20;
import static roomescape.fixture.DateTimeFixture.TIME_03_00;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;

@SpringBootTest
@Transactional
@Rollback
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private GameTimeRepository gameTimeRepository;
    private GameTime time_03_00;

    @BeforeEach
    void setUp() {
        time_03_00 = gameTimeRepository.save(TIME_03_00);
    }

    @DisplayName("전체 예약을 조회할 수 있다")
    @Test
    void readAllTest() {
        Reservation reservation1 = new Reservation(null, "리비", DATE_2024_04_20, time_03_00);
        Reservation reservation2 = new Reservation(null, "웨지", DATE_2024_04_20, time_03_00);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<Reservation> reservations = reservationRepository.readAll();

        assertThat(reservationRepository.readAll())
                .extracting("name")
                .containsExactly("리비", "웨지");
    }

    @DisplayName("예약 단건을 저장할 수 있다")
    @Test
    void saveTest() {
        Reservation reservation = new Reservation("폭포", DATE_2024_04_20, time_03_00);
        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved).isEqualTo(new Reservation(saved.getId(), reservation));
    }

    @DisplayName("예약 단건을 조회할 수 있다")
    @Test
    void findByIdTest() {
        Reservation reservation = new Reservation(null, "리비", DATE_2024_04_20, time_03_00);
        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved.getName()).isEqualTo("리비");
    }

    @DisplayName("예약 단건을 삭제할 수 있다")
    @Test
    void deleteByIdTest() {
        Reservation reservation = new Reservation(null, "리비", DATE_2024_04_20, time_03_00);

        Reservation saved = reservationRepository.save(reservation);
        reservationRepository.deleteById(saved.getId());

        assertThatThrownBy(() -> reservationRepository.findById(saved.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("특정 예약이 저장된 예약들과 시간이 겹치는 경우가 있는지 확인할 수 있다")
    @Test
    void isAnyReservationConflictWithTest() {
        Reservation reservation = new Reservation(null, "리비", DATE_2024_04_20, time_03_00);
        reservationRepository.save(reservation);

        Reservation conflictReservation = new Reservation(3L, "폭포", DATE_2024_04_20, time_03_00);

        assertThat(reservationRepository.isAnyReservationConflictWith(conflictReservation)).isTrue();
    }
}
