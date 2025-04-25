package roomescape.infra.memory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.presentation.dto.request.ReservationTimeCreateRequest;
import roomescape.infra.entity.ReservationTimeEntity;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest(properties = "room-escape.console-view.enabled=true")
@Import(MemoryDatabaseTestConfig.class)
class ReservationTimeMemoryDatabaseTest {

    @Autowired
    private ReservationTimeMemoryDatabase timeDatabase;

    @AfterEach
    void tearDown() {
        timeDatabase.deleteAll();
    }

    @Test
    void 전체_조회_테스트() {
        timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));
        timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(13, 0))));

        final List<ReservationTimeEntity> result = timeDatabase.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).startAt()).isEqualTo("10:00");
        assertThat(result.get(1).startAt()).isEqualTo("13:00");
    }

    @Test
    void id_조회_테스트() {
        final long reservationTimeId = timeDatabase.saveAndGetId(ReservationTimeEntity.beforeSave(new ReservationTimeCreateRequest(LocalTime.of(10, 0))));

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
