package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ReservationDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public @Autowired ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String findAllSql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(findAllSql,
                (resultSet, numRow) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                resultSet.getString("time_value")
                        )
                )
        );
    }

    public long insert(ReservationRequestDto reservationRequestDto) {
        String insertSql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date());
            ps.setLong(3, reservationRequestDto.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteById(long id) {
        String deleteFromIdSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteFromIdSql, id);
    }

    public Reservation findById(Long id) {
        String findByIdSql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r inner join reservation_time as t on r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.queryForObject(findByIdSql,
                (resultSet, numRow) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                resultSet.getString("time_value")
                        )
                ), id);
    }
}
