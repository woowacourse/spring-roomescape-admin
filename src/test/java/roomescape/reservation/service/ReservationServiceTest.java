package roomescape.reservation.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.dao.ReservationTimeDao;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.dto.request.ReservationRequestDto;
import roomescape.reservation.dto.response.ReservationResponseDto;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationService reservationService;

    @BeforeEach
    void init() {
        ReservationDao reservationDao = new ReservationDao(jdbcTemplate, dataSource);
        ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate, dataSource);
        reservationService = new ReservationService(reservationDao, reservationTimeDao);

        reservationTimeDao.insert(new ReservationTime(LocalTime.now()));
    }

    @DisplayName("예약 정보 삽입 테스트")
    @Test
    void insertTest() {

        ReservationRequestDto request = new ReservationRequestDto("name", LocalDate.now(), 1L);
        ReservationResponseDto response = reservationService.addReservation(request);
        assertThat(response.id()).isEqualTo(1L);
    }

    @DisplayName("예약 정보 전체 조회 테스트")
    @Test
    void findAllTest() {
        reservationService.addReservation(new ReservationRequestDto("name1", LocalDate.now(), 1L));
        reservationService.addReservation(new ReservationRequestDto("name2", LocalDate.now(), 1L));
        reservationService.addReservation(new ReservationRequestDto("name3", LocalDate.now(), 1L));

        int findSize = reservationService.findAllReservation().reservations().size();
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Test
    void deleteTest() {
        ReservationRequestDto request = new ReservationRequestDto("name", LocalDate.now(), 1L);
        ReservationResponseDto response = reservationService.addReservation(request);

        reservationService.removeReservationById(response.id());
        int findSize = reservationService.findAllReservation().reservations().size();
        assertThat(findSize).isEqualTo(0);
    }

    @DisplayName("예약 정보 삭제 실패 테스트")
    @Test
    void deleteFailTest() {
        Long noneExistId = -1L;

        Assertions.assertThatThrownBy(() -> reservationService.removeReservationById(noneExistId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
