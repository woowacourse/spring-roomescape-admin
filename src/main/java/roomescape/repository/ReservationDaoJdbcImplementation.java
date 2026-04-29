package roomescape.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDaoJdbcImplementation implements ReservationDao {

    private static final String SELECT_RESERVATION_QUERY = "SELECT reservation.id, reservation.name, reservation.date, reservation.time_id, reservation_time.start_at FROM reservation INNER JOIN reservation_time ON reservation.time_id = reservation_time.id;";
    private static final String INSERT_RESERVATION_QUERY = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
    private static final String DELETE_SPECIFIC_RESERVATION_QUERY = "DELETE FROM reservation WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationEntityMapper mapper;

    public ReservationDaoJdbcImplementation(JdbcTemplate jdbcTemplate, ReservationEntityMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                SELECT_RESERVATION_QUERY,
                (resultSet, rowNum) -> mapper.toReservation(
                        parseToReservationEntity(resultSet)
                )
        );
    }

    private ReservationEntity parseToReservationEntity(ResultSet resultSet) throws SQLException {
        return new ReservationEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                parseToReservationTimeEntity(resultSet)
        );
    }

    private ReservationTimeEntity parseToReservationTimeEntity(ResultSet resultSet) throws SQLException {
        return new ReservationTimeEntity(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        ReservationEntity entity = mapper.toReservationEntity(reservation);

        jdbcTemplate.update(
                connection -> createReservationInsertPreparedStatement(connection, entity),
                keyHolder
        );

        Long id = getEntityIdAtKeyHolder(keyHolder);

        return mapper.toReservation(entity.initializeId(id));
    }

    private static Long getEntityIdAtKeyHolder(KeyHolder keyHolder) {
        Number primaryKey = keyHolder.getKey();

        return Objects.requireNonNull(primaryKey)
                .longValue();
    }

    private PreparedStatement createReservationInsertPreparedStatement(Connection connection, ReservationEntity entity)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entity.name());
        ps.setString(2, entity.date());
        ps.setString(3, extractReservationTimeId(entity));

        return ps;
    }

    private String extractReservationTimeId(ReservationEntity entity) {
        return String.valueOf(
                entity.timeEntity().id()
        );
    }

    @Override
    public void delete(Long targetId) {
        int rows = jdbcTemplate.update(DELETE_SPECIFIC_RESERVATION_QUERY, targetId);
        if (rows == 0) {
            throw new IllegalArgumentException("삭제 대상이 존재하지 않습니다");
        }
    }
}
