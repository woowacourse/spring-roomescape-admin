package roomescape.service;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateDto;
import roomescape.dto.ReservationTimeDto;

@RequiredArgsConstructor
@Service
public class ReservationTimeService {

    private final JdbcTemplate jdbcTemplate;

    public List<ReservationTimeDto> findAllReservationTimes() {
        return jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time",
                (rs, rowNum) -> ReservationTimeDto.from(
                        ReservationTime.builder()
                                .id(rs.getLong("id"))
                                .startAt(rs.getTime("start_at").toLocalTime())
                                .build()
                )
        );
    }

    public ReservationTimeDto saveReservationTime(ReservationTimeCreateDto request) {
        ReservationTime reservationTime = request.toEntity();
        String formattedStartAt = reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, formattedStartAt);
            return ps;
        }, keyHolder);

        Long saveId = keyHolder.getKey().longValue();

        ReservationTime saved = ReservationTime.builder()
                .id(saveId)
                .startAt(reservationTime.getStartAt())
                .build();

        return ReservationTimeDto.from(saved);
    }

    public void deleteReservationTime(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
