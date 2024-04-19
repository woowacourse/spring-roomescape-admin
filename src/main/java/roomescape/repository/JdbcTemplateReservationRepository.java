package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcTemplateReservationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationEntityRowMapper());
    }

    private RowMapper<Reservation> reservationEntityRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    }
}
