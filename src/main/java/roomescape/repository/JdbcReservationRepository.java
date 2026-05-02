package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    @Override
    public List<Reservation> findAll() {
        String sql = """
                    select r.id as reservation_id,   
                    r.name, r.date, 
                    t.id as reservation_time_id,
                    t.start_at as time_value
                    from reservation as r 
                    inner join reservation_time as t 
                    on r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("reservation_time_id"),
                            LocalTime.parse(resultSet.getString("time_value"))
                    );
                    return new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            time
                    );
                }

        );
    }
    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
