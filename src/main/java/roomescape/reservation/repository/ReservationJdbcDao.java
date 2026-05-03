package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

@Repository
public class ReservationJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT\n"
                + "    r.id as reservation_id,\n"
                + "    r.name,\n"
                + "    r.date,\n"
                + "    t.id as time_id,\n"
                + "    t.start_at as time_value\n"
                + "FROM reservation as r\n"
                + "INNER JOIN reservation_time as t\n"
                + "  ON r.time_id = t.id";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = Reservation.create(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            ReservationTime.create(
                                    resultSet.getLong("id"),
                                    resultSet.getTime("start_at").toLocalTime()
                            )
                    );

                    return reservation;
                });
    }

    public List<Reservation> findByTimeId(Long timeId) {
        String sql = "SELECT id, name, date, time_id "
                + "FROM reservation "
                + "WHERE time_id = ?";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> Reservation.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        ReservationTime.create(
                                resultSet.getLong("time_id"),
                                null
                        )
                ),
                timeId
        );
    }

    public Long save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
