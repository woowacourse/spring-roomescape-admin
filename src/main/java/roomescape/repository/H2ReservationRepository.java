package roomescape.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ClientName;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;

@Repository
public class H2ReservationRepository implements ReservationRepository {
    private final NamedParameterJdbcTemplate template;

    public H2ReservationRepository(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        List<Reservation> reservations = template.query(sql, itemRowMapper());
        return reservations;
    }

    private RowMapper<Reservation> itemRowMapper() {
        return ((rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                new ClientName(rs.getString("name")),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ));
    }

    @Override
    public Reservation save(final Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (:name, :date, :time)";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", reservation.getClientName().getValue())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long savedReservationId = keyHolder.getKey().longValue();

        return reservation.initializeIndex(savedReservationId);
    }

    @Override
    public void deleteById(final Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        Map<String, Long> param = Map.of("id", reservationId);

        template.update(sql, param);
    }
}
