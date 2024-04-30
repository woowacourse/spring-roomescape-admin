package roomescape.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationDaoJdbc implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()
            )
    );

    @Autowired
    public ReservationDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value " +
                "FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation findById(long id) {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value " +
                "FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public long save(ReservationSaveDto reservationDto) {
        return simpleJdbcInsert
                .executeAndReturnKey(new BeanPropertySqlParameterSource(reservationDto))
                .longValue();
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
