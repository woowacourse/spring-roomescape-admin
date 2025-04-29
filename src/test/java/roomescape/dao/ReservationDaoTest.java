package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationDaoTest {
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    private Long reservationTimeId;

    @BeforeEach
    void beforeEach() {
        reservationTimeId = reservationTimeDao.save(new ReservationTime(null,LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("예약 추가 기능 확인")
    void saveReservation() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest("hippo", LocalDate.now().plusDays(1), 1L);
        ReservationTime reservationTime = reservationTimeDao.findById(reservationTimeId);
        reservationDao.save(reservationRequest.toReservation(reservationTime));
        //when
        List<Reservation> findReservations = reservationDao.findAll();
        //then
        assertThat(findReservations.size()).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("generateReservationRequests")
    @DisplayName("예약 목록 조회 기능 확인")
    void findAllReservation(List<ReservationRequest> requests) {
        //given
        ReservationTime reservationTime = reservationTimeDao.findById(reservationTimeId);
        for (ReservationRequest request : requests) {
            reservationDao.save(request.toReservation(reservationTime));
        }
        //when
        List<Reservation> findReservations = reservationDao.findAll();
        //then
        assertThat(findReservations.size()).isEqualTo(2);
    }

    static Stream<Arguments> generateReservationRequests() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                new ReservationRequest("hippo", LocalDate.now().plusDays(1), 1L),
                                new ReservationRequest("kindHippo", LocalDate.now().plusDays(2), 1L)
                        )
                )
        );
    }

    @Test
    @DisplayName("예약 목록 조회 기능 확인")
    void findReservationById() {
        //given
        ReservationRequest request = new ReservationRequest("hippo", LocalDate.now().plusDays(1), 1L);
        ReservationTime reservationTime = reservationTimeDao.findById(reservationTimeId);
        Long id = reservationDao.save(request.toReservation(reservationTime));
        //when
        //then
        assertThatCode(() -> reservationDao.findById(id)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약 삭제 기능 확인")
    void deleteByIdReservation() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest("hippo", LocalDate.now().plusDays(1), 1L);
        ReservationTime reservationTime = reservationTimeDao.findById(reservationTimeId);
        //when
        Long reservationId = reservationDao.save(reservationRequest.toReservation(reservationTime));
        reservationDao.deleteById(reservationId);
        List<Reservation> findReservations = reservationDao.findAll();
        //then
        assertThat(findReservations.size()).isEqualTo(0);
    }
}
