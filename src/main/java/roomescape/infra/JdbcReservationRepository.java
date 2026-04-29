package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getMemberName())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<Reservation> findAll() {
        String select = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(select, rowMapper);
    }

    @Override
    public void delete(Long id) {
        String delete = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(delete, id);
    }
}
