package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.request.ReservationRequest;
import roomescape.response.ReservationResponse;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservations() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation",
                (rs, rowNum) ->
                        new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getDate("date").toLocalDate(),
                                rs.getTime("time").toLocalTime())
        );
    }

    @Override
    public ReservationResponse insert(ReservationRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                conn -> {
                    PreparedStatement preparedStatement = conn.prepareStatement(
                            "INSERT INTO reservation(name, date, time) " +
                                    "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, request.name());
                    preparedStatement.setDate(2, java.sql.Date.valueOf(request.date()));
                    preparedStatement.setTime(3, java.sql.Time.valueOf(request.time()));
                    return preparedStatement;
                },
                keyHolder);
        return new ReservationResponse(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                request.name(),
                request.date(),
                request.time());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
