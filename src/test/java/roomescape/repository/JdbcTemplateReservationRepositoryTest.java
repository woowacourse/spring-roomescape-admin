package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class JdbcTemplateReservationRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("신규 예약을 저장할 수 있다.")
    @Test
    void save() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        Reservation reservation = createReservation(savedTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        int rowCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "reservation", "id = " + savedReservation.getId());
        assertThat(rowCount).isEqualTo(1);
    }

    @DisplayName("예약을 삭제할 수 있다.")
    @Test
    void delete() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        Reservation savedReservation = reservationRepository.save(createReservation(savedTime));

        reservationRepository.deleteBy(savedReservation.getId());

        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "reservation");
        assertThat(rowCount).isZero();
    }

    @DisplayName("모든 예약을 불러올 수 있다.")
    @Test
    void findAll() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        reservationRepository.save(createReservation(savedTime));
        reservationRepository.save(createReservation(savedTime));

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("예약이 존재하지 않으면 빈 리스트를 반환한다.")
    @Test
    void findAllWhenEmpty() {
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).isEmpty();
    }

    private ReservationTime createReservationTime() {
        return new ReservationTime(LocalTime.now());
    }

    private Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation(new Name("아톰"), LocalDate.now(), reservationTime);
    }
}
