package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.domain.TimeSlot;

@JdbcTest
class ReservationJdbcRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationJdbcInsert;
    private final TimeSlotRepository timeSlotRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_slot_id")
                .usingGeneratedKeyColumns("id");
        this.timeSlotRepository = new TimeSlotJdbcRepository(jdbcTemplate);
        this.reservationRepository = new ReservationJdbcRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("예약 추가가 DB에 올바르게 반영된다.")
    void addReservationTest() {
        // given
        TimeSlot timeSlot = timeSlotRepository.create(new TimeSlot("12:00"));
        Reservation reservation = new Reservation("웨지", "2024-04-21", timeSlot);
        // when
        reservationRepository.addReservation(reservation);
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
        TimeSlot timeSlot = timeSlotRepository.create(new TimeSlot("12:00"));
        return reservationJdbcInsert.executeAndReturnKey(Map.of(
                "name", "웨지",
                "date", "2024-04-21",
                "time_slot_id", timeSlot.getId()
        )).longValue();
    }

    private int databaseRowCount() {
        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
