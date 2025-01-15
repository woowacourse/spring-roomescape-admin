package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.fixture.ReservationTimeFixture.INITIAL_RESERVATION_TIME_SIZE;
import static roomescape.fixture.ReservationTimeFixture.NO_RESERVATION_TIME_ID;
import static roomescape.fixture.ReservationTimeFixture.TIME_1_ID;

import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationTimeFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void save() {
        // given
        ReservationTime time = ReservationTimeFixture.newTimeWithoutId();

        // when
        ReservationTime saved = reservationTimeRepository.save(time);

        // then
        assertThat(reservationTimeRepository.findById(saved.getId())).isNotEmpty();
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(INITIAL_RESERVATION_TIME_SIZE + 1);
    }

    @Test
    @DisplayName("예약 시간을 조회한다.")
    void findById() {
        // when
        ReservationTime found = reservationTimeRepository.findById(TIME_1_ID).get();

        // then
        assertThat(found.getStartAt().truncatedTo(ChronoUnit.MINUTES))
                .isEqualTo(ReservationTimeFixture.time1().getStartAt().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @DisplayName("모든 예약 시간을 조회한다.")
    void findAll() {
        // when
        List<ReservationTime> result = reservationTimeRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(INITIAL_RESERVATION_TIME_SIZE);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        // given
        assertThat(reservationTimeRepository.findById(NO_RESERVATION_TIME_ID)).isNotEmpty();

        // when
        reservationTimeRepository.deleteById(NO_RESERVATION_TIME_ID);

        // then
        assertThat(reservationTimeRepository.findById(NO_RESERVATION_TIME_ID)).isEmpty();
    }
}
