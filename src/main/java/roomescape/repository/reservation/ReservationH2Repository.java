package roomescape.repository.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
@Primary
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation add(Reservation reservation) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return Reservation.of(id, reservation);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                )
        );
    }
}
