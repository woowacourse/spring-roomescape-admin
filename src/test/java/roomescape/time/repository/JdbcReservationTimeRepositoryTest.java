package roomescape.time.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.time.domain.ReservationTime;

@JdbcTest
class JdbcReservationTimeRepositoryTest {

    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public JdbcReservationTimeRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("새로운 시간 정보를 저장하고 반환된 객체의 ID를 확인한다.")
    void saveTest() {
        ReservationTime time = ReservationTime.create("10:00");

        ReservationTime savedTime = reservationTimeRepository.save(time);

        assertThat(savedTime.getId()).isNotNull();
        assertThat(savedTime.getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("ID를 통해 저장된 시간 정보를 정확히 조회한다.")
    void findByIdTest() {
        ReservationTime savedTime = reservationTimeRepository.save(ReservationTime.create("11:00"));

        ReservationTime foundTime = reservationTimeRepository.findById(savedTime.getId());

        assertThat(foundTime.getId()).isEqualTo(savedTime.getId());
        assertThat(foundTime.getStartAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("존재하는 모든 시간 목록을 리스트로 조회한다.")
    void findAllTest() {
        reservationTimeRepository.save(ReservationTime.create("10:00"));
        reservationTimeRepository.save(ReservationTime.create("11:00"));

        List<ReservationTime> times = reservationTimeRepository.findAll();

        assertThat(times).hasSize(2);
        assertThat(times).extracting("startAt").containsExactly("10:00", "11:00");
    }

    @Test
    @DisplayName("특정 ID의 데이터 존재 여부를 boolean으로 반환한다.")
    void existsByIdTest() {
        ReservationTime savedTime = reservationTimeRepository.save(ReservationTime.create("12:00"));

        boolean exists = reservationTimeRepository.existsById(savedTime.getId());
        boolean notExists = reservationTimeRepository.existsById(999L);

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("ID를 지정하여 데이터를 삭제하면 더 이상 조회되지 않는다.")
    void deleteByIdTest() {
        ReservationTime savedTime = reservationTimeRepository.save(ReservationTime.create("13:00"));

        reservationTimeRepository.deleteById(savedTime.getId());

        boolean exists = reservationTimeRepository.existsById(savedTime.getId());
        assertThat(exists).isFalse();
    }
}
