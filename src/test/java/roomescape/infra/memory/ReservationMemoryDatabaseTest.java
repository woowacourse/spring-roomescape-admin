package roomescape.infra.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.entity.ReservationEntity;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest(properties = "room-escape.console-view.enabled=true")
@Import(MemoryDatabaseTestConfig.class)
class ReservationMemoryDatabaseTest {

    @Autowired
    private ReservationMemoryDatabase database;

    @Autowired
    private ReservationTimeMemoryDatabase timeDatabase;

    @AfterEach
    void tearDown() {
        timeDatabase.deleteAll();
        database.deleteAll();
    }

    @Test
    void 전체_조회_테스트() {
        final long timeId1 = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));
        final long timeId2 = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(13, 0))));
        database.saveAndGetId(ReservationEntity.beforeSave(new ReservationCreateRequest("dompoo", LocalDate.now().plusDays(20), timeId1)));
        database.saveAndGetId(ReservationEntity.beforeSave(new ReservationCreateRequest("popo", LocalDate.now().plusDays(25), timeId2)));

        final List<ReservationEntity> result = database.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).name()).isEqualTo("dompoo");
        assertThat(result.get(0).date()).isEqualTo(LocalDate.now().plusDays(20));
        assertThat(result.get(0).time().startAt()).isEqualTo(LocalTime.of(10, 0));

        assertThat(result.get(1).name()).isEqualTo("popo");
        assertThat(result.get(1).date()).isEqualTo(LocalDate.now().plusDays(25));
        assertThat(result.get(1).time().startAt()).isEqualTo(LocalTime.of(13, 0));
    }

    @Test
    void id_조회_테스트() {
        final long timeId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));
        final long reservationId = database.saveAndGetId(ReservationEntity.beforeSave(new ReservationCreateRequest("dompoo", LocalDate.now().plusDays(20), timeId)));

        final ReservationEntity result = database.findById(reservationId).get();

        assertThat(result.name()).isEqualTo("dompoo");
        assertThat(result.date()).isEqualTo(LocalDate.now().plusDays(20));
        assertThat(result.time().startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 저장_테스트() {
        final long timeId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));
        final ReservationCreateRequest request = new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), timeId);

        final long savedId = database.saveAndGetId(ReservationEntity.beforeSave(request));

        assertThat(database.findAll().size()).isEqualTo(1);
        final ReservationEntity savedReservation = database.findById(savedId).get();
        assertThat(savedReservation.name()).isEqualTo("dompoo");
        assertThat(savedReservation.date()).isEqualTo(LocalDate.of(2025, 5, 17));
        assertThat(savedReservation.time().startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 삭제_테스트() {
        final long timeId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));
        final long reservationId = database.saveAndGetId(ReservationEntity.beforeSave(new ReservationCreateRequest("dompoo", LocalDate.of(2025, 5, 17), timeId)));

        database.deleteById(reservationId);

        assertThat(database.findAll()).hasSize(0);
    }
}
