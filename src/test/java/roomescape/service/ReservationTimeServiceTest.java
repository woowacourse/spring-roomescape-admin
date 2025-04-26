package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;
    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        DataSource testDataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("schema.sql", "data.sql")
                .build();
        reservationTimeDao = new ReservationTimeDao(testDataSource);
        reservationTimeService = new ReservationTimeService(reservationTimeDao);
    }

    @Test
    @DisplayName("유효한 값에 대해 예약 시간 저장 성공")
    void createReservationTimeTest() {
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.of(10, 0));

        // when
        ReservationTime reservationTime = reservationTimeService.createReservationTime(reservationTimeRequest);

        // then
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("저장된 예약 시간을 모두 불러온다")
    void getReservationTimesTest() {
        // when
        List<ReservationTime> reservationTimes = reservationTimeService.getReservationTimes();

        // then
        assertThat(reservationTimes).hasSize(3);
    }

    @Test
    @DisplayName("id를 통해 저장된 값을 삭제한다")
    void deleteReservationTimeTest() {
        // given
        ReservationTime saved = reservationTimeDao.save(new ReservationTime(null, LocalTime.of(10, 0)));

        // when
        reservationTimeService.deleteReservationTime(saved.getId());

        // then
        assertThat(reservationTimeDao.findById(saved.getId())).isEmpty();
    }
}