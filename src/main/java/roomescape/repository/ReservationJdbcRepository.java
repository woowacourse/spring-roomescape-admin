package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import java.util.List;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        final Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final ReservationCreateRequest createRequest) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(createRequest);
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select * from reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
