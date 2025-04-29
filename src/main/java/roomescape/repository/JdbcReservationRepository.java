package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate nameJdbcTemplate;

    public JdbcReservationRepository(NamedParameterJdbcTemplate nameJdbcTemplate) {
        this.nameJdbcTemplate = nameJdbcTemplate;
    }

    private static final RowMapper<Reservation> reservationRowMapper = (row, rowNum) ->
            new Reservation(row.getLong("id"),
                    row.getString("name"),
                    row.getDate("date").toLocalDate(),
                    new ReservationTime(row.getLong("time_id"), row.getTime("time_value").toLocalTime())
            );

    @Override
    public Long add(final Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (NAME, DATE, TIME_ID) VALUES (:name, :date, :time_id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        nameJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId())
        , keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        try {
            String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                    "FROM reservation as r inner join reservation_time as t on r.time_id = t.id " +
                    "WHERE r.id = :id";
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            Reservation reservation = nameJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource().addValue("id", id),
                    reservationRowMapper
            );
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = :id";

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        nameJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        return nameJdbcTemplate.query(sql, reservationRowMapper);
    }
}
