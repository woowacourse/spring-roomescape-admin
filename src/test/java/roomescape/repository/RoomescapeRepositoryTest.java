package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomescapeRepositoryTest {

    @Autowired
    RoomescapeRepository repository;
    @Autowired
    JdbcTemplate template;

    @BeforeEach
    void setUp() {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into reservation_time (start_at) values (?)";
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, "15:40");
            return ps;
        }, keyHolder);

        String reservationSql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(reservationSql, new String[]{"id"});
            ps.setString(1, "브라운");
            ps.setString(2, "2023-08-05");
            ps.setLong(3, 1L);
            return ps;
        }, keyHolder);
    }

    @Test
    void findAll() {
        //when
        List<Reservation> reservations = repository.findAll();

        //then
        assertThat(reservations).hasSize(1);
    }

    @Test
    void saveReservation() {
        //given
        Reservation reservation = new Reservation("네오", LocalDate.parse("2023-08-05"),
                ReservationTime.parse("15:40").toEntity(1L));

        //when
        Reservation saved = repository.saveReservation(reservation);

        //then
        assertThat(saved.getName()).isEqualTo("네오");
        assertThat(saved.getDate()).isEqualTo(LocalDate.parse("2023-08-05"));
        assertThat(saved.getTime().isSameTime(ReservationTime.parse("15:40"))).isTrue();
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void deleteById() {
        //when
        int deleteCounts = repository.deleteById(1L);

        //then
        assertThat(deleteCounts).isEqualTo(1);
    }

}
