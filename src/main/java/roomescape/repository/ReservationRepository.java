package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;

@Repository
public class ReservationRepository {

    private static final String TABLE_NAME = "reservation";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM " + TABLE_NAME + " as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation
                            = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    LocalTime.parse(resultSet.getString("start_at"))
                            )
                    );
                    return reservation;
                }
        );
    }

    private ReservationTime findByTimeId(Long timeId) {
        String sql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime
                            = new ReservationTime(
                            timeId,
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                    return reservationTime;
                },
                timeId
        );
    }

    public Reservation save(ReservationCreateRequest reservationCreateRequest) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationCreateRequest.name())
                .addValue("date", reservationCreateRequest.date())
                .addValue("time_id", reservationCreateRequest.timeId());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        ReservationTime reservationTime = findByTimeId(reservationCreateRequest.timeId());
        return new Reservation(
                id,
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationTime);
    }

    public int deleteById(Long id) {
        String sql = "delete from " + TABLE_NAME + " where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
