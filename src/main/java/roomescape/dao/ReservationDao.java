package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationDto;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ReservationDto> findAll() {
        String sql = "SELECT id, name, `date`, `time` FROM reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> ReservationDto.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
    }

    public ReservationDto findById(long id) {
        String sql = "SELECT id, name, `date`, `time` FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> ReservationDto.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ),
                id
        );
    }

    public long add(ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement
                            = connection.prepareStatement(sql, new String[]{"id"});
                    preparedStatement.setString(1, reservationDto.getName());
                    preparedStatement.setString(2, reservationDto.getDate());
                    preparedStatement.setString(3, reservationDto.getTime());
                    return preparedStatement;
                },
                keyHolder
        );
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
