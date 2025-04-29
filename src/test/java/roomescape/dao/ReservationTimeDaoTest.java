package roomescape.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeDaoTest {
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Test
    @DisplayName("예약 시간 추가 기능 확인")
    void saveReservationTime() {
        //given
        Long timeId = reservationTimeDao.save(new ReservationTime(null, LocalTime.now()));
        //when & then
        assertThatCode(() -> reservationTimeDao.findById(timeId)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("generateReservationTime")
    @DisplayName("예약 시간 목록 조회 기능 확인")
    void findAllReservationTime(List<ReservationTime> requests) {
        //given
        for (ReservationTime time : requests) {
            reservationTimeDao.save(time);
        }
        //when
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        //then
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    static Stream<Arguments> generateReservationTime() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                new ReservationTime(null, LocalTime.now()),
                                new ReservationTime(null, LocalTime.now().plusHours(1))
                        )
                )
        );
    }

    @Test
    @DisplayName("예약 시간 id를 이용하여 예약 시간 조회 기능 확인")
    void findReservationTimeById() {
        //given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());
        Long timeId = reservationTimeDao.save(reservationTime);
        //when & then
        assertThatCode(() -> reservationTimeDao.findById(timeId)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약 시간 id를 이용하여 예약 시간 삭제 기능 확인")
    void deleteReservationTimeById() {
        //given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());
        Long timeId = reservationTimeDao.save(reservationTime);
        reservationTimeDao.deleteById(timeId);
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        //when & then
        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
