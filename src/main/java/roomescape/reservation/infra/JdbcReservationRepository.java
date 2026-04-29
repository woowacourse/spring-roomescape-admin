package roomescape.reservation.infra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
@RequiredArgsConstructor
public class JdbcReservationRepository implements ReservationRepository {
    private final NamedParameterJdbcTemplate template;

    @Override
    public Reservation save(String name, LocalDate date, LocalTime time) {
        String sql = """
                INSERT INTO reservation(name, date, time) VALUES (:name, :date, :time)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("date", date.toString())
                .addValue("time", time.toString());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), name, date, time);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void deleteById(Long id) {
    }
}
