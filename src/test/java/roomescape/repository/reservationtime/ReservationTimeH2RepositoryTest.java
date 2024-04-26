package roomescape.repository.reservationtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationTimeH2Repository.class)
class ReservationTimeH2RepositoryTest {

    private static final String TABLE_NAME = "RESERVATION_TIME";

    @Autowired
    private ReservationTimeH2Repository reservationTimeH2Repository;
    @Autowired
    private DataSource source;
    private ReservationTime reservationTime;

    @BeforeEach
    void init() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        ReservationTime reservationTimeWithoutId = new ReservationTime(LocalTime.of(12, 0));
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationTimeWithoutId);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        reservationTime = new ReservationTime(id, reservationTimeWithoutId.startAt());
    }

    @Test
    @DisplayName("ReservationTime을 저장한다.")
    void save() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 0));

        ReservationTime saved = reservationTimeH2Repository.save(reservationTime);

        assertThat(saved.id()).isNotNull();
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 제거한다.")
    void delete() {
        reservationTimeH2Repository.delete(reservationTime.id());

        assertThat(reservationTimeH2Repository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("모든 ReservationTime을 찾는다.")
    void findAll() {
        List<ReservationTime> found = reservationTimeH2Repository.findAll();

        assertThat(found).containsExactly(reservationTime);
    }

    @Test
    @DisplayName("id에 맞는 ReservationTime을 찾는다.")
    void findBy() {
        ReservationTime found = reservationTimeH2Repository.findById(reservationTime.id()).get();

        assertThat(found).isEqualTo(reservationTime);
    }

    @Test
    @DisplayName("존재하지 않는 id가 들어오면 빈 Optional 객체를 반환한다.")
    void findEmpty() {
        Optional<ReservationTime> reservationTime = reservationTimeH2Repository.findById(-1L);

        assertThat(reservationTime.isEmpty()).isTrue();
    }
}
