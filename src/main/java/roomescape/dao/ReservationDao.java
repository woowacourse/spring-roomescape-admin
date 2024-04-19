package roomescape.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationMapper = (resultSet, row) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time from reservation";

        return jdbcTemplate.query(sql, reservationMapper);
    }

    public Long insertWithKeyHolder(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, reservationRequest.name());
                preparedStatement.setDate(2, Date.valueOf(reservationRequest.date()));
                preparedStatement.setTime(3, Time.valueOf(reservationRequest.time()));

                return preparedStatement;
            }
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
