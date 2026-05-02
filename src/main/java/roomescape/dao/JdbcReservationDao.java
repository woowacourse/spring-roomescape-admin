package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> selectAll() {
        String sql = """
                select 
                    r.id,
                    r.name,
                    r.date,
                    r.time_id,
                    t.start_at
                from reservation r
                inner join reservation_time t 
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Long timeId = resultSet.getLong("time_id");
            LocalTime startAt = resultSet.getTime("start_at").toLocalTime();
            ReservationTime reservationTime = new ReservationTime(timeId, startAt);
            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    reservationTime
            );
        });
    }

    @Override
    public Reservation insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setObject(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from reservation where id = ?";
        int deletedRow = jdbcTemplate.update(sql, id);
        return deletedRow > 0;
    }

}
