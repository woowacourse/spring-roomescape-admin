package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Reservation reservation) {
        jdbcTemplate.update("insert into reservation (name, date, time) values (?, ?, ?)", reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            Reservation reservation = Reservation.of(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );

            return reservation;
        };

        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        return reservations;
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
