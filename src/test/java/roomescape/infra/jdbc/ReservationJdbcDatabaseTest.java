package roomescape.infra.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import roomescape.business.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import({ReservationJdbcDatabase.class, ReservationTimeJdbcDatabase.class})
@Transactional
class ReservationJdbcDatabaseTest {

    @Autowired
    private ReservationJdbcDatabase database;

    @Autowired
    private ReservationTimeJdbcDatabase timeDatabase;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 전체_조회_테스트() {
        final long timeId1 = timeDatabase.saveAndGetId(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        final long timeId2 = timeDatabase.saveAndGetId(new ReservationTimeCreateRequest(LocalTime.of(13, 0)));
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "dompoo", LocalDate.now().plusDays(20), timeId1);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "popo", LocalDate.now().plusDays(25), timeId2);

        final List<Reservation> result = database.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("dompoo");
        assertThat(result.get(0).date()).isEqualTo(LocalDate.now().plusDays(20));
        assertThat(result.get(0).startTime()).isEqualTo(LocalTime.of(10, 0));

        assertThat(result.get(1).name()).isEqualTo("popo");
        assertThat(result.get(1).date()).isEqualTo(LocalDate.now().plusDays(25));
        assertThat(result.get(1).startTime()).isEqualTo(LocalTime.of(13, 0));
    }

    @Test
    void 저장_테스트() {
        final long timeId = timeDatabase.saveAndGetId(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        final ReservationCreateRequest request = new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), timeId);

        final long savedId = database.saveAndGetId(request);

        assertThat(database.findAll().size()).isEqualTo(1);
        final Reservation savedReservation = database.findById(savedId);
        assertThat(savedReservation.name()).isEqualTo("dompoo");
        assertThat(savedReservation.startTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(savedReservation.date()).isEqualTo(LocalDate.of(2025, 5, 17));
    }

    @Test
    void 삭제_테스트() {
        final long timeId = timeDatabase.saveAndGetId(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        final long reservationId = database.saveAndGetId(new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), timeId));

        database.deleteById(reservationId);

        assertThat(database.findAll()).hasSize(0);
    }
}
