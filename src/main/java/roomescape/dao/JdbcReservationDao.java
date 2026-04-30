package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
@Primary
@Profile
public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> selectReservations() {
        final String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation r
            INNER JOIN reservation_time t
            on r.time_id = t.id;
            """;
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value")
                    );
                    return new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            time
                    );
                });
    }

    @Override
    public Reservation insertReservation(ReservationRequest request, ReservationTime time) {
        final String sql = "insert into reservation (name, date, time_id) values(?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), request.getName(), request.getDate(), time);
    }

    @Override
    public void deleteReservation(Long id) {
        final String sql = "delete from reservation where id = ?;";
        jdbcTemplate.update(sql, id);
    }
}