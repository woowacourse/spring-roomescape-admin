package roomescape.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;

@Repository
public class JdbcRoomescapeRepository implements RoomescapeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;

    public JdbcRoomescapeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                new ReservationRowMapper()
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        Number id = reservationInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime()));
        return reservation.withId(id.longValue());
    }

    @Override
    public boolean deleteById(long id) {
        int affectedRows = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return affectedRows > 0;
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservation reservation = new Reservation(
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
            return reservation.withId(rs.getLong("id"));
        }
    }
}

