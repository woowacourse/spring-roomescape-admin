package roomescape.reservation.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setObject(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";

        List<Reservation> reservations =  jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("date", LocalDate.class),
                        resultSet.getObject("time", LocalTime.class)
                ));

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
}
