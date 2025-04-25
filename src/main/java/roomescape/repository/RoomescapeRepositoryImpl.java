package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class RoomescapeRepositoryImpl implements RoomescapeRepository {

    private JdbcTemplate template;

    public RoomescapeRepositoryImpl(final JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value
                FROM reservation as r 
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        return template.query(sql, reservationRowMapper());
    }

    @Override
    public Reservation save(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return reservation.toEntity(id);
    }

    @Override
    public int deleteById(final long id) {
        String sql = "delete from reservation where id = ?";
        return template.update(sql, id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            ReservationTime reservationTime = ReservationTime.parse(rs.getString("time_value"))
                    .toEntity(rs.getLong("time_id"));
            Reservation reservation = new Reservation(
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    reservationTime
            ).toEntity(rs.getLong("reservation_id"));
            return reservation;
        };
    }
}
