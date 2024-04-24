package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getLong("time_id")
            );

    public List<Reservation> findAllReservationInfos() {
        String sqlToFind = "SELECT id, name, date, time_id FROM reservation";

        return jdbcTemplate.query(
                sqlToFind,
                reservationRowMapper);
    }

    public Reservation createReservation(Reservation reservation) {
        String sqlToInsert = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sqlToInsert,
                    new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date());
            ps.setLong(3, reservation.timeId());
            return ps;
        }, keyHolder);
        return reservation.toIdAssigned(Objects.requireNonNull(keyHolder.getKey())
                .longValue());
    }

    public void deleteReservation(Long id) {
        String sqlToDelete = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sqlToDelete, id);
    }

}
