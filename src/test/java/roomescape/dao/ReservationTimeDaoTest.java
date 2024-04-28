package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservationTime.sql")
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    private final ReservationTime reservationTime = new ReservationTime(LocalTime.of(10,0));

    @DisplayName("예약시간을 저장한다.")
    @Test
    void save() {
        // given & when
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        // then
        assertAll(
                () -> assertThat(savedReservationTime.getId()).isEqualTo(2L),
                () -> assertThat(savedReservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약시간을 조회한다.")
    @Test
    void findAll() {
        // given & when
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        // then
        ReservationTime reservationTime = reservationTimes.get(0);
        assertAll(
                () -> assertThat(reservationTimes).hasSize(1),
                () -> assertThat(reservationTime.getId()).isEqualTo(1L),
                () -> assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0))
        );
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        // given & when
        reservationTimeDao.delete(1L);

        // then
        assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
