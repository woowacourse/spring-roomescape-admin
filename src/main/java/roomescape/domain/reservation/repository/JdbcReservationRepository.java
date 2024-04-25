package roomescape.domain.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;
import roomescape.global.query.QueryBuilder;
import roomescape.global.query.condition.ComparisonCondition;
import roomescape.global.query.join.JoinCondition;
import roomescape.global.query.join.JoinType;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(rs.getLong("time_id"), rs.getTime("start_at").toLocalTime())
    );

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTimeId());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return reservation.updateId(id);
    }

    @Override
    public boolean existsByReservationDateTime(LocalDate date, long timeId) {
        String query = QueryBuilder.select("reservation")
                .addColumns("count(*)")
                .where(ComparisonCondition.equalTo("date", date))
                .where(ComparisonCondition.equalTo("time_id", timeId))
                .build();
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        String query = QueryBuilder.select("reservation")
                .alias("r")
                .addAllColumns()
                .join(JoinType.INNER, "reservation_time", JoinCondition.on("r.time_id", "t.id"), "t")
                .where(ComparisonCondition.equalTo("r.id", id))
                .build();
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String query = QueryBuilder.select("reservation")
                .alias("r")
                .addAllColumns()
                .join(JoinType.INNER, "reservation_time", JoinCondition.on("r.time_id", "t.id"), "t")
                .build();
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public void deleteById(long id) {
        String query = QueryBuilder.delete("reservation")
                .where(ComparisonCondition.equalTo("id", id))
                .build();
        jdbcTemplate.update(query);
    }
}
