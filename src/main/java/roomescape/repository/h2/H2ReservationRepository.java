package roomescape.repository.h2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;

@Repository
public class H2ReservationRepository implements ReservationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    reservationTimeRowMapper.mapRow(resultSet, rowNum));

    public H2ReservationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id, r.name, r.date, t.id as time_id, t.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public Reservation findById(long id) {
        String sql = """
                SELECT r.id, r.name, r.date, t.id as time_id, t.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                WHERE r.id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForObject(sql, params, reservationRowMapper);
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (:name, :date, :timeId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", reservation.getName());
        params.addValue("date", reservation.getDate());
        params.addValue("timeId", reservation.getTime().getId());

        jdbcTemplate.update(sql, params, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Reservation(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, params);
    }
}
