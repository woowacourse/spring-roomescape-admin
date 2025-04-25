package roomescape.repository;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;

@JdbcTest
@Import(ReservationTimeRepository.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    private static final ReservationTimeRequest REQUEST_INPUT = new ReservationTimeRequest(
            "18:00"
    );

    @DisplayName("시간 관리 내역을 조회하는 기능을 구현한다")
    @Test
    void readReservationTimes() {
        assertThatCode(reservationTimeRepository::readAllReservationTimes).doesNotThrowAnyException();
    }

    @DisplayName("시간 관리 내역을 추가하는 기능을 구현한다")
    @Test
    void createReservationTime() {
        assertThatCode(() -> reservationTimeRepository.createReservationTime(
                REQUEST_INPUT.toReservationTime())).doesNotThrowAnyException();
    }

    @DisplayName("시간 관리 내역을 삭제하는 기능을 구현한다")
    @Test
    void deleteReservationTime() {
        reservationTimeRepository.createReservationTime(REQUEST_INPUT.toReservationTime());

        assertThatCode(() -> reservationTimeRepository.deleteReservationTimeById(1L)).doesNotThrowAnyException();
    }

    @DisplayName("동일 시간대를 추가하려는 경우 예외 처리한다")
    @Test
    void throwException_Duplicates() {
        ReservationTimeRequest reservationTimeRequest1 = new ReservationTimeRequest("18:00");
        reservationTimeRepository.createReservationTime(reservationTimeRequest1.toReservationTime());

        ReservationTimeRequest reservationTimeRequest2 = new ReservationTimeRequest("18:00");
        assertThatThrownBy(
                () -> reservationTimeRepository.createReservationTime(reservationTimeRequest2.toReservationTime()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("삭제하려는 시간 관리 내역의 아이디가 존재하지 않는 경우 예외 처리한다")
    @Test
    void throwException_InvalidId() {
        assertThatThrownBy(() -> reservationTimeRepository.deleteReservationTimeById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
