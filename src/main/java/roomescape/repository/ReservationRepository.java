package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    public void insert(ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, reservationDto.name(), reservationDto.date(), reservationDto.time());

    }

    public Integer count() {
        String sql = "SELECT count(*) FROM reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Reservation> readAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
