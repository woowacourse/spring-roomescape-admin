package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCreationDto;
import roomescape.service.dto.ReservationTimeDto;

@JdbcTest
class ReservationJdbcRepositoryTest {

    private final ReservationRepository reservationRepository;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationJdbcInsert;
    private final SimpleJdbcInsert reservationTimeJdbcInsert;

    @Autowired
    public ReservationJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
        this.reservationTimeJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
        this.reservationRepository = new ReservationJdbcRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("예약 추가가 DB에 올바르게 반영된다.")
    void addReservationTest() {
        // given
        long timeId = reservationTimeJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", LocalTime.parse("12:00")
        )).longValue();
        ReservationCreationDto dto = new ReservationCreationDto("브라운", "2023-08-05",
                new ReservationTimeDto(timeId, "12:00"));
        // when
        Reservation reservation = reservationRepository.addReservation(dto);
        // then
        assertThat(databaseRowCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("DB에 저장된 예약을 올바르게 불러온다.")
    void findAllTest() {
        // given
        Long id = createReservationAndReturnId();
        // when
        List<Reservation> actual = reservationRepository.findAll();
        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("ID를 통해 예약을 삭제한다.")
    void deleteByIdTest() {
        // given
        Long id = createReservationAndReturnId();
        // when
        reservationRepository.deleteById(id);
        // then
        assertThat(databaseRowCount()).isZero();
    }

    private Long createReservationAndReturnId() {
        long timeId = reservationTimeJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", LocalTime.parse("12:00")
        )).longValue();
        return reservationJdbcInsert.executeAndReturnKey(Map.of(
                "name", "웨지",
                "date", "2024-04-21",
                "time_id", timeId
        )).longValue();
    }

    private int databaseRowCount() {
        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
