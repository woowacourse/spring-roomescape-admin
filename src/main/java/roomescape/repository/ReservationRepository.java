package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationRepository(
            JdbcTemplate jdbcTemplate,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation create(Reservation reservation, long timeId) {
        String createSql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservation.getName());
            statement.setObject(2, reservation.getDate());
            statement.setLong(3, timeId);

            return statement;
        }, keyHolder);

        Number id = keyHolder.getKey();
        validateNotNull(id);

        return reservation.withId(id.longValue())
                .withTime(reservationTimeRepository.findById(timeId));
    }

    public List<Reservation> findAll() {
        String findSql = "SELECT r.*, rt.start_at"
                + " FROM reservation r JOIN reservation_time rt"
                + " ON r.time_id = rt.id";

        return jdbcTemplate.query(findSql, reservationRowMapper());
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";

        int updatedRows = jdbcTemplate.update(deleteSql, id);
        if (updatedRows < 1) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            long timeId = resultSet.getLong("time_id");
            String startAt = resultSet.getString("start_at");

            return Reservation.retrieve(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    ReservationTime.retrieve(timeId, startAt)
            );
        };
    }

    private void validateNotNull(Number id) {
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("ID 조회에 실패했습니다.");
        }
    }
}
