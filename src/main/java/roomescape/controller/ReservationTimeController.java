package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getAllReservationTime() {
        String sql = "SELECT * FROM reservation_time";
        List<ReservationTimeResponseDto> responseDtoList = jdbcTemplate.query(sql,(resultSet, rowNum) ->
                new ReservationTimeResponseDto(
                        resultSet.getLong("id"),
                        resultSet.getObject("start_at", LocalTime.class)
                ));
        return ResponseEntity.ok(responseDtoList);

    }

}
