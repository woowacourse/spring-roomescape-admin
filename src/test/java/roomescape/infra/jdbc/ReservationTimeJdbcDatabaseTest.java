package roomescape.infra.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import roomescape.business.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(ReservationTimeJdbcDatabase.class)
@Transactional
class ReservationTimeJdbcDatabaseTest {

    @Autowired
    private ReservationTimeJdbcDatabase timeDatabase;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 전체_조회_테스트() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "13:00");

        final List<ReservationTime> result = timeDatabase.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).startTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(result.get(1).startTime()).isEqualTo(LocalTime.of(13, 0));
    }

    @Test
    void id_조회_테스트() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "10:00");
            return ps;
        }, keyHolder);
        long reservationTimeId = keyHolder.getKey().longValue();

        final ReservationTime result = timeDatabase.findById(reservationTimeId).get();

        assertThat(result.startTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 저장_테스트() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        final long savedId = timeDatabase.saveAndGetId(request);

        final ReservationTime savedReservation = timeDatabase.findById(savedId).get();
        assertThat(savedReservation.startTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 삭제_테스트() {
        final long savedId = timeDatabase.saveAndGetId(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        timeDatabase.deleteById(savedId);

        assertThat(timeDatabase.findAll()).hasSize(0);
    }

}
