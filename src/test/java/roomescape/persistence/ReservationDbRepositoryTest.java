package roomescape.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.TestFixture.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDbRepositoryTest implements ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Test
    @DisplayName("예약을 저장한다.")
    public void save() {
        // given
        Reservation reservation = MIA_RESERVATION();

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertThat(savedReservation.getId()).isNotNull();
    }

    @Override
    @Test
    @DisplayName("동일시간대의 예약 목록을 조회한다.")
    public void findAllByDateAndTime() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?)";
        jdbcTemplate.update(
                insertSql,
                "브라운", MIA_RESERVATION_DATE.toString(), MIA_RESERVATION_TIME.toString(),
                "미아", MIA_RESERVATION_DATE.toString(), MIA_RESERVATION_TIME.toString(),
                "토미", MIA_RESERVATION_DATE.toString(), MIA_RESERVATION_TIME.toString()
        );

        // when
        List<Reservation> reservations = reservationRepository.findAllByDateAndTime(MIA_RESERVATION_DATE, MIA_RESERVATION_TIME);

        // then
        assertThat(reservations).hasSize(3)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "미아", "토미");
    }

    @Override
    @Test
    @DisplayName("모든 예약 목록을 조회한다.")
    public void findAll() {
        // given
        String insertSql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, "브라운", "2030-08-05", "15:40");

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(reservations.size()).isEqualTo(count);
    }

    @Override
    public void findById() {

    }

    @Override
    public void deleteById() {

    }
}
