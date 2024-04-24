package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.data.vo.Reservation;
import roomescape.data.vo.ReservationTime;

@Component
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(final Reservation reservation) {
        final var sql = "INSERT INTO reservation(name, date, time_id) VALUES(?,?,?)";
        final var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setDate(2, Date.valueOf(reservation.getDate()));
            pstmt.setLong(3, reservation.getTimeId());
            return pstmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAll() {
        final var sql = "SELECT "
                + "r.id as reservation_id,"
                + "r.name,"
                + "r.date,"
                + "t.id as time_id,"
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id";

        return jdbcTemplate.query(sql, actorRowMapper());
    }

    public void delete(final long id) {
        final var sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Reservation findOne(final long id) {
        final var sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name,"
                + "r.date,"
                + "t.id as time_id,"
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = ?";

        return jdbcTemplate.queryForObject(sql, actorRowMapper(), id);
    }

    private static RowMapper<Reservation> actorRowMapper() {
        return (resultSet, rowNum) -> new Reservation.Builder()
                .id(resultSet.getLong("reservation_id"))
                .name(resultSet.getString("reservation.name"))
                .date(resultSet.getDate("reservation.date").toLocalDate())
                .time(new ReservationTime(resultSet.getLong("time_id"),
                        resultSet.getTime("reservation_time.start_at").toLocalTime()))
                .build();
    }
}
