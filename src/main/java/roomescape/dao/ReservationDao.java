package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static roomescape.dao.Table.RESERVATION;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(RESERVATION.getName())
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            new ReservationTime(resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("time_value")))
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
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation insert(ReservationCreateRequest reservationCreateRequest, ReservationTime reservationTime) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationCreateRequest);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return reservationCreateRequest.toReservation(id, reservationTime);
    }

    public void delete(long id) {
        String sql = "delete from reservation where id=?";
        jdbcTemplate.update(sql, id);
    }
}
