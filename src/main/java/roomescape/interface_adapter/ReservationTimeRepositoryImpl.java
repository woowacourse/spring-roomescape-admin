package roomescape.interface_adapter;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.enttity.ReservationTime;
import roomescape.usecase.ReservationTimeOutput;
import roomescape.usecase.ReservationTimeRepository;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeOutput addReservationTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values(?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(reservationTime.getStart_at()));

            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return new ReservationTimeOutput(id, reservationTime.getStart_at());
    }

    @Override
    public List<ReservationTimeOutput> getReservationTime() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNUm) -> {
            return new ReservationTimeOutput(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );
        });
    }

//        String sql = "select * from reservation_time";
//        List<ReservationTimeResponseDto> reservationTimeResponseDtos = jdbcTemplate.query(sql, (resultSet, rowNUm) -> {
//            return new ReservationTimeResponseDto(resultSet.getLong("id"),
//                    LocalTime.parse(resultSet.getString("start_at")));
//        });
//        return ResponseEntity.ok(reservationTimeResponseDtos);
}
