package roomescape.reservation.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                select
                    r.id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at
                from reservation as r
                    join reservation_time as t
                    on r.time_id = t.id
                """;

        List<Reservation> reservations =  jdbcTemplate.query(sql, getRowMapper());

        return reservations;
    }

    @Override
    public void delete(long id) {
        String sql = "delete from reservation where id = ?";
        int result = jdbcTemplate.update(sql, id);
        if (result != 1) {
            throw new NoSuchElementException("데이터베이스에 해당 id가 존재하지 않습니다.");
        }
    }

    private RowMapper<Reservation> getRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getObject("start_at", LocalTime.class)
                )
        );
    }
}
