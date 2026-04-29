package roomescape.repository;

import static roomescape.repository.rowmapper.RowMapperUtils.RESERVATION_ROW_MAPPER;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation create(Reservation reservation) {
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTimeId()
        ));

        return reservation.withId(id.longValue());
    }

    public List<Reservation> findAll() {
        String findSql = "SELECT r.*, rt.start_at"
                + " FROM reservation r"
                + " JOIN reservation_time rt"
                + " ON r.time_id = rt.id";

        return jdbcTemplate.query(findSql, RESERVATION_ROW_MAPPER);
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation"
                + " WHERE id = ?";

        int updatedRows = jdbcTemplate.update(deleteSql, id);
        if (updatedRows < 1) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
    }
}
