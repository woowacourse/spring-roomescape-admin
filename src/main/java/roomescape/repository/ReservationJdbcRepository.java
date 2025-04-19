package roomescape.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.ReservationRequest;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Reservation> findById(final long id) {
        final var reservations = jdbcTemplate.query(
            "select * from RESERVATION where id = ?",
            (rs, rowNum) -> {
                final var name = rs.getString("name");
                final var date = rs.getDate("date").toLocalDate();
                final var time = rs.getTime("time").toLocalTime();
                return new Reservation(id, name, date, time);
            },
            id
        );
        return reservations.stream().findAny();
    }

    public Reservation save(ReservationRequest request) {
        final var sql = "insert into RESERVATION (name, date, time) values (?, ?, ?)";
        final var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                final var pstmt = connection.prepareStatement(sql, new String[]{"id"});
                pstmt.setString(1, request.name());
                pstmt.setDate(2, Date.valueOf(request.date()));
                pstmt.setTime(3, Time.valueOf(request.time()));
                return pstmt;
            },
            keyHolder
        );

        final var id = keyHolder.getKey().longValue();
        return findById(id).get();
    }

    public boolean removeById(long id) {
        final var removedRowsCount = jdbcTemplate.update("delete from RESERVATION where id = ?", id);
        return removedRowsCount > 0;
    }

    public List<Reservation> getReservations() {
        return jdbcTemplate.query("select * from RESERVATION", (rs, rowNum) -> {
            final var id = rs.getLong("id");
            final var name = rs.getString("name");
            final var date = rs.getDate("date").toLocalDate();
            final var time = rs.getTime("time").toLocalTime();
            return new Reservation(id, name, date, time);
        });
    }
}
