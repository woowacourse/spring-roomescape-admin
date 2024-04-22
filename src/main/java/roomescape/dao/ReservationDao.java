package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;

@Repository
public class ReservationDao {

    private static final String DATABASE = "reservation";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        List<Reservation> reservationResponses = new ArrayList<>();
        String sql = "SELECT id, name, date, time FROM " + DATABASE;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation
                            = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                    return reservation;
                }
        );
    }

    public Long addReservation(ReservationCreateRequest reservationCreateRequest) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationCreateRequest.name())
                .addValue("date", reservationCreateRequest.date())
                .addValue("time", reservationCreateRequest.time());
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }
}
