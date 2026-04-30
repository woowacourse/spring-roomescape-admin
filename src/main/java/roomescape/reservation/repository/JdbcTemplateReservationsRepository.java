package roomescape.reservation.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.repository.dto.Reservation;
import roomescape.time.application.dto.TimeInfo;

@Repository
public class JdbcTemplateReservationsRepository implements ReservationsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateReservationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservationsWithTime() {
        String reservationIdColumn = "reservation_id";
        String reservationNameColumn = "reservation_name";
        String reservationDateColumn = "reservation_date";
        String timeIdColumn = "time_id";
        String timeValueColumn = "time_value";

        String sql = "SELECT\n"
                + "    r.id as " + reservationIdColumn + ",\n"
                + "    r.name as " + reservationNameColumn + ",\n"
                + "    r.date as " + reservationDateColumn + ",\n"
                + "    t.id as " + timeIdColumn + ",\n"
                + "    t.start_at as " + timeValueColumn + "\n"
                + "FROM reservation as r\n"
                + "INNER JOIN reservation_time as t\n"
                + "  ON r.time_id = t.id\n";


        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Reservation(
                    rs.getLong(reservationIdColumn),
                    rs.getString(reservationNameColumn),
                    rs.getDate(reservationDateColumn).toLocalDate(),
                    new TimeInfo(
                            rs.getLong(timeIdColumn),
                            rs.getTime(timeValueColumn).toLocalTime()
                    )
            )
        );
    }

    @Override
    public ReservationEntity saveReservation(ReservationEntity entity) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection ->
                        createPreparedStatement(entity, connection),
                keyHolder
        );

        long id = keyHolder.getKey().longValue();
        return entity.updateId(id);
    }

    private PreparedStatement createPreparedStatement(
            ReservationEntity reservationEntity,
            Connection connection
    ) throws SQLException {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, reservationEntity.name());
        preparedStatement.setDate(2, Date.valueOf(reservationEntity.date()));
        preparedStatement.setLong(3, reservationEntity.timeId());

        return preparedStatement;
    }

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("해당 예약은 존재하지 않습니다.");
        }
    }
}
