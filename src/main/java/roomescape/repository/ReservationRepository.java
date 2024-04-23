package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.SaveReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(SaveReservationRequest reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "insert into reservation (name, date, time_id) values (?, ?, ?)"
                , new String[]{"id"}
            );
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date());
            ps.setLong(3, reservation.timeId());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    private Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(
            """
                SELECT
                    r.id as reservation_id,
                    r.name as reservation_name,
                    r.date as reservation_date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                WHERE r.id = ?
                """,
            (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getString("reservation_date"),
                new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("time_value"))
            ), id
        );
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
            """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """,
            (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("time_value"))
            )
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
