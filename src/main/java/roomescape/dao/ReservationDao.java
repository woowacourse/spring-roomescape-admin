package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationResponse> reservationRowMapper = (resultSet, rowNum) -> new ReservationResponse(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            resultSet.getLong("time_id"),
            LocalTime.parse(resultSet.getString("time_value"))
    );

    public List<Reservation> findAll() {
        String sql = "select " +
                "r.id as reservation_id," +
                "r.name," +
                "r.date," +
                "t.id as time_id," +
                "t.start_at as time_value " +
                "from reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id=t.id";
        List<ReservationResponse> reservationResponses = jdbcTemplate.query(sql, reservationRowMapper);
        return reservationResponses.stream()
                .map(ReservationResponse::toReservation)
                .toList();
    }

    public Reservation insert(ReservationRequest reservationRequest, Time time) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationRequest);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return reservationRequest.toReservation(id, time);
    }

    public void delete(long id) {
        String sql = "delete from reservation where id=?";
        jdbcTemplate.update(sql, id);
    }
}
