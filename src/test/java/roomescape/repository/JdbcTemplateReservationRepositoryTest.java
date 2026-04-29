package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcTemplateReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateReservationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateReservationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("Reservation 데이터를 데이터베이스에 저장한다.")
    public void save() {
        // given
        Reservation reservation = new Reservation("name", "2023-08-05", "15:40");

        // when
        Reservation saved = repository.save(reservation);

        // then
        assertThat(saved).extracting(
                Reservation::getName,
                Reservation::getDate,
                Reservation::getTime
        ).containsExactlyInAnyOrder(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Test
    @DisplayName("저장된 모든 Reservation 데이터를 조회한다.")
    public void findAll() throws Exception {
        // given
        repository.save(new Reservation("kim", "2023-08-05", "15:40"));
        repository.save(new Reservation("lee", "2023-08-06", "16:10"));
        repository.save(new Reservation("park", "2023-08-07", "17:30"));

        // when
        List<Reservation> reservations = repository.findAll();

        // then
        assertThat(reservations).hasSize(3);
    }

    @Test
    @DisplayName("특정 id의 Reservation을 삭제한다.")
    public void delete() {
        // given
        Reservation saved = repository.save(new Reservation("kim", "2023-08-05", "15:40"));

        // when
        repository.delete(saved.getId());

        // then
        List<Reservation> reservations = repository.findAll();
        assertThat(reservations).isEmpty();
    }
}
