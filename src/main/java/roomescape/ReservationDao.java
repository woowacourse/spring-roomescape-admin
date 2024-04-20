package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
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
        String findAllSql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(findAllSql,
                (resultSet, numRow) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
    }

    public long insert(ReservationRequestDto reservationRequestDto) {
        String insertSql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date());
            ps.setString(3, reservationRequestDto.time());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteById(long id) {
        String deleteFromIdSql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(deleteFromIdSql, id);
    }
}
