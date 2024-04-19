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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDbRepositoryTest implements ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void save() {

    }

    @Override
    public void findAllByDateAndTime() {

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
