package roomescape.reservation.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateReservationsRepository implements ReservationsRepository {

    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";
    private final static String DATE_COLUMN = "date";
    private final static String TIME_COLUMN = "time";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateReservationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationEntity> getReservations() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationEntity(
                        rs.getLong(ID_COLUMN),
                        rs.getString(NAME_COLUMN),
                        rs.getDate(DATE_COLUMN),
                        rs.getTime(TIME_COLUMN)
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
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        preparedStatement.setString(1, reservationEntity.name());
        preparedStatement.setDate(2, reservationEntity.date());
        preparedStatement.setTime(3, reservationEntity.time());

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
