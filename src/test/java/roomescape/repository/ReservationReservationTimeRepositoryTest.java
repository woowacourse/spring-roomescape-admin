package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationTimeFixture;

@JdbcTest
class ReservationReservationTimeRepositoryTest {

    @Autowired
    private DataSource dataSource;

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new ReservationTimeRepository(dataSource);
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void save() {
        // given
        ReservationTime reservationTime = ReservationTimeFixture.entity(0);

        // when
        ReservationTime saved = reservationTimeRepository.save(reservationTime);

        // then
        ReservationTime found = reservationTimeRepository.findById(saved.getId()).get();
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("예약 시간을 조회한다.")
    void findById() {
        // given
        ReservationTime reservationTime1 = ReservationTimeFixture.entity(0);
        ReservationTime reservationTime2 = ReservationTimeFixture.entity(1);
        ReservationTime saved = reservationTimeRepository.save(reservationTime1);
        reservationTimeRepository.save(reservationTime1);

        // when
        ReservationTime found = reservationTimeRepository.findById(saved.getId()).get();

        // then
        assertThat(found.getStartAt().truncatedTo(ChronoUnit.MINUTES))
                .isEqualTo(saved.getStartAt().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    @DisplayName("모든 예약 시간을 조회한다.")
    void findAll() {
        // given
        ReservationTime reservationTime1 = ReservationTimeFixture.entity(0);
        ReservationTime reservationTime2 = ReservationTimeFixture.entity(1);
        ReservationTime reservationTime3 = ReservationTimeFixture.entity(2);
        ReservationTime saved1 = reservationTimeRepository.save(reservationTime1);
        ReservationTime saved2 = reservationTimeRepository.save(reservationTime2);
        ReservationTime saved3 = reservationTimeRepository.save(reservationTime3);

        // when
        List<ReservationTime> result = reservationTimeRepository.findAll();

        // then
        assertThat(result).containsExactly(saved1, saved2, saved3);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        // given
        ReservationTime reservationTime = ReservationTimeFixture.entity(0);
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        assertThat(reservationTimeRepository.findById(saved.getId())).isNotEmpty();

        // when
        reservationTimeRepository.delete(saved.getId());

        // then
        assertThat(reservationTimeRepository.findById(saved.getId())).isEmpty();
    }
}
