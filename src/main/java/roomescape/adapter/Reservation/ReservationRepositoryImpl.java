package roomescape.adapter.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.usecase.Reservation.ReservationOutput;
import roomescape.usecase.Reservation.ReservationRepository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation addReservation(final Reservation reservation) {
        final String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, String.valueOf(reservation.getDate()));
            preparedStatement.setString(3, String.valueOf(reservation.getReservationTime().getId()));

            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getName(), reservation.getDate(),
                reservation.getReservationTime());

    }

    @Override
    public List<ReservationOutput> getAllReservations() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                new ReservationTime(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at"))
                )
        ));
        return reservations.stream()
                .map(ReservationOutput::from)
                .toList();
    }

    @Override
    public void deleteReservation(final long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
