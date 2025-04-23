package roomescape.user.repository.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.domain.ReservationTime;
import roomescape.exception.DataNotFoundException;
import roomescape.user.domain.Reservation;

@Repository
@RequiredArgsConstructor
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Reservation reservation) {
        String sql = "insert into reservations (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Reservation getOneById(Long id) {
        String sql = """
                SELECT 
                    r.id as id,
                    r.name as name,
                    r.date as date,
                    t.id as time_id,
                    t.start_at as start_at
                FROM reservations as r
                INNER JOIN reservation_times as t
                ON r.time_id = t.id
                WHERE r.id = ?
                """;
        List<Reservation> reservation = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Reservation(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getDate("date").toLocalDate(),
                                new ReservationTime(
                                        rs.getLong("time_id"),
                                        LocalTime.parse(rs.getString("start_at"))
                                )
                        ),
                id
        );
        if (reservation.size() != 1) {
            throw new DataNotFoundException("해당 예약 정보가 존재하지 않습니다. id = " + id);
        }

        return reservation.getFirst();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id as id, 
                    r.name as name, 
                    date, 
                    time_id, 
                    start_at 
                FROM reservations as r
                inner join reservation_times as t
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                LocalTime.parse(rs.getString("start_at"))
                        )
                )
        );
    }

    @Override
    public void delete(Reservation reservation) {
        String sql = "delete from reservations where id = ?";
        jdbcTemplate.update(sql, reservation.getId());
    }
}
