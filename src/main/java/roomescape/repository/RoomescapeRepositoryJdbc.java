package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class RoomescapeRepositoryJdbc implements RoomescapeRepository {

    private JdbcTemplate template;

    public RoomescapeRepositoryJdbc(final JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return template.query(sql, reservationRowMapper());
    }

    @Override
    public Reservation saveReservation(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        Reservation result = reservation.toEntity(id);
        return result;
    }

    @Override
    public int deleteById(final long id) {
        String sql = "delete from reservation where id = ?";
        return template.update(sql, id);
    }

    @Override
    public void clear() {
        String sql = "delete from reservation";
        String resetAutoIncrementSql = "ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1";
        template.update(sql);
        template.update(resetAutoIncrementSql);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    LocalTime.parse(rs.getString("time")));
            return reservation;
        };
    }
}
