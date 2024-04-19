package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;

import java.util.List;

@Service
public class ReservationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readReservations() {
        String sql = "SELECT * FROM reservation";
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
            new Reservation(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime());

        return jdbcTemplate.query(sql, rowMapper);
    }
}
