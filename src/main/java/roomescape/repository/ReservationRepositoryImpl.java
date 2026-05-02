package roomescape.repository;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.ErrorCode;
import roomescape.exception.ReservationException;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final String TABLE_NAME = "reservation";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName(TABLE_NAME)
            .usingColumns("name", "date", "time_id")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = String.format("SELECT id, name, date, time_id FROM %s", TABLE_NAME);

        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getLong("time_id")
            ));
    }

    @Override
    public Reservation save(final Reservation reservation) {
        try {
            final Map<String, Object> args = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTimeId());

            final long generatedKey = simpleJdbcInsert.executeAndReturnKey(args).longValue();
            return Reservation.builder()
                .id(generatedKey)
                .name(reservation.getName())
                .date(reservation.getDate())
                .timeId(reservation.getTimeId())
                .build();
        } catch (DataIntegrityViolationException e) {
            throw new ReservationException(ErrorCode.RESERVATION_TIME_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = :id", TABLE_NAME);
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        jdbcTemplate.update(sql, parameters);
    }
}
