package roomescape.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );

        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                reservationTime
        );
        return reservation;
    };

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResponse insert(ReservationRequest request) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(
                        sql, new String[]{"id"}
                );
                statement.setString(1, request.name());
                statement.setString(2, request.date());
                statement.setLong(3, request.timeId());

                return statement;
            }
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        return ReservationResponse.from(new Reservation(generatedId, request.name(), request.date(), new ReservationTime(request.timeId(), null)));
    }

    public List<ReservationResponse> findAllReservations() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as start_at from reservation as r "
                + "inner join reservation_time as t on r.time_id = t.id";
        try {
            List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);
            return reservations.stream()
                    .map(ReservationResponse::from)
                    .collect(Collectors.toList());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
