package roomescape.repository.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservationtime.ReservationTimeH2Repository;

@JdbcTest
@Import({ReservationH2Repository.class, ReservationTimeH2Repository.class})
class ReservationH2RepositoryTest {

    @Autowired
    private ReservationH2Repository reservationH2Repository;
    @Autowired
    private DataSource source;
    private Reservation reservation;
    private ReservationTime reservationTime;

    @BeforeEach
    void init() {
        insertReservationTime();
        insertReservation();
    }

    private void insertReservationTime() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
        ReservationTime reservationTimeWithoutId = new ReservationTime(LocalTime.of(12, 0));
        SqlParameterSource reservationTimeParams = new BeanPropertySqlParameterSource(reservationTimeWithoutId);
        long id = jdbcInsert.executeAndReturnKey(reservationTimeParams).longValue();

        reservationTime = new ReservationTime(id, reservationTimeWithoutId.startAt());
    }

    private void insertReservation() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
        Reservation reservationWithoutId = new Reservation(
                "브라운",
                LocalDate.of(2024, 4, 24),
                reservationTime
        );
        SqlParameterSource reservationParams = new MapSqlParameterSource()
                .addValue("name", reservationWithoutId.name())
                .addValue("date", reservationWithoutId.date(DateTimeFormatter.ISO_DATE))
                .addValue("time_id", reservationTime.id());
        Long id = jdbcInsert.executeAndReturnKey(reservationParams).longValue();

        reservation = new Reservation(id, reservationWithoutId.name(), reservationWithoutId.date(), reservationTime);
    }

    @Test
    @DisplayName("Reservation을 저장하면 id가 포함된 Reservation이 반환된다.")
    void save() {
        Reservation reservation = new Reservation(
                "브라운",
                LocalDate.of(2024, 4, 24),
                reservationTime
        );

        Reservation save = reservationH2Repository.save(reservation);

        assertThat(save.id()).isNotNull();
    }

    @Test
    @DisplayName("Reservation을 제거한다.")
    void delete() {
        reservationH2Repository.delete(reservation.id());

        assertThat(reservationH2Repository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("저장된 모든 Reservation을 반환한다.")
    void findAll() {
        List<Reservation> found = reservationH2Repository.findAll();

        assertThat(found).containsExactly(reservation);
    }
}
