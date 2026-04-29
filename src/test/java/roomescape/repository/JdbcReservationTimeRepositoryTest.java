package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JdbcReservationTimeRepository.class)
@JdbcTest
class JdbcReservationTimeRepositoryTest {
    private final ReservationTimeRepository repository;

    @Autowired
    public JdbcReservationTimeRepositoryTest(DataSource dataSource) {
        this.repository = new JdbcReservationTimeRepository(dataSource);
    }

    @Test
    void 시간_데이터_생성_테스트() {
        // given, when
        ReservationTime reservationTime = repository.createReservationTime(new ReservationTime(null, "16:20"));

        // then
        assertThat(reservationTime).isNotNull();

        List<ReservationTime> all = reservationTime.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getId()).isEqualTo(reservationTime.getId());
        assertThat(all.get(0).getStartAt()).isEqualTo(reservationTime.getStartAt());
    }

    @Test
    void 시간_데이터_전체_조회_테스트() {
        // given
        ReservationTime time1 = new ReservationTime(null, "20:43");
        ReservationTime time2 = new ReservationTime(null, "10:00");

        repository.createReservationTime(time1);
        repository.createReservationTime(time2);

        // when
        List<ReservationTime> times = repository.findAll();

        //then
        assertThat(times).hasSize(2);
        assertThat(times)
                .extracting(ReservationTime::getStartAt)
                .anySatisfy(getStartAt -> assertThat(getStartAt).isEqualTo(time1.getStartAt()))
                .anySatisfy(getStartAt -> assertThat(getStartAt).isEqualTo(time2.getStartAt()));
    }
}