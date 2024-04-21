package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation.Builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("name"))
                            .date(resultSet.getDate("date").toLocalDate())
                            .time(resultSet.getTime("time").toLocalTime())
                            .build();
                    return reservation;
                });
    }
}
