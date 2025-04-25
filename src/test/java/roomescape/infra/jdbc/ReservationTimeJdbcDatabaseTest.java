package roomescape.infra.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import roomescape.presentation.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.entity.ReservationTimeEntity;

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

        final List<ReservationTimeEntity> result = timeDatabase.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).startAt()).isEqualTo("10:00");
        assertThat(result.get(1).startAt()).isEqualTo("13:00");
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

        final ReservationTimeEntity result = timeDatabase.findById(reservationTimeId).get();

        assertThat(result.startAt()).isEqualTo("10:00");
    }

    @Test
    void 저장_테스트() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));

        final long savedId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(request));

        final ReservationTimeEntity savedReservation = timeDatabase.findById(savedId).get();
        assertThat(savedReservation.startAt()).isEqualTo("10:00");
    }

    @Test
    void 삭제_테스트() {
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.of(10, 0));
        final long savedId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(request));

        timeDatabase.deleteById(savedId);

        assertThat(timeDatabase.findAll()).hasSize(0);
    }

}
