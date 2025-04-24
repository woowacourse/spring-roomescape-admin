package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.Dao;
import roomescape.repository.repository.ReservationRepository;
import roomescape.repository.repository.ReservationTimeRepository;

// Junit5만 사용하라는 이번 요구사항에 위배되는 테스트인가?
@JdbcTest
class ReservationTimeServiceTestWithJdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationTimeService service;

    @BeforeEach
    void setup() {
        reservationRepository = new ReservationRepository(new Dao(jdbcTemplate));
        reservationTimeRepository = new ReservationTimeRepository(new Dao(jdbcTemplate));
        service = new ReservationTimeService(reservationTimeRepository);
    }

    @Test
    @DisplayName("모든 데이터를 조회한다")
    void getAll() {
        // given
        reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));

        // when
        List<ReservationTimeResponse> all = service.getAll();

        // then
        assertThat(all).size().isEqualTo(2);
    }

    @Test
    @DisplayName("데이터를 저장한다")
    void create() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(null, LocalTime.now());

        // when
        service.create(request);

        // then
        assertThat(reservationTimeRepository.getAll()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("데이터를 제거한다")
    void remove() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));

        // when
        service.remove(time.id());

        // then
        assertThat(reservationTimeRepository.getAll()).size().isEqualTo(0);
    }

    // DB에 실제 쿼리를 날리는 테스트이기 때문에 정상 동작한다.
    @Test
    @DisplayName("제거를 시도하는 데이터와 연관(FK)된 데이터가 존재할 경우 예외를 반환한다")
    void removeException() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(null, "moko", LocalDate.now(), time));

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> service.remove(time.id());

        // then
        assertThatThrownBy(throwingCallable)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 데이터 무결성을 위반했습니다.");
    }
}
