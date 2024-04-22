package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;


/*
 * 테스트 데이터베이스 초기 데이터
 * {ID=1, NAME=브라운, DATE=2024-05-04, TIME=16:00}
 * {ID=2, NAME=엘라, DATE=2024-05-04, TIME=17:00}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME=15:40}
 */
@JdbcTest
@Sql(scripts = "/reset_test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 예약 데이터를 가져온다.")
    void findAll() {
        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(3);
    }

    @Test
    @DisplayName("특정 예약 id의 데이터를 조회한다.")
    void findById() {
        // when
        Reservation findReservations = reservationRepository.findById(2);

        // then
        assertThat(findReservations.getName()).isEqualTo("엘라");
    }

    @Test
    @DisplayName("새로운 예약을 생성한다.")
    void create() {
        // given
        Reservation createReservation = new Reservation(0L, "브라운", "2023-08-05", "10:00");

        // when
        reservationRepository.create(createReservation);
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(4);
    }

    @Test
    @DisplayName("특정 id를 가진 예약을 삭제한다")
    void remove() {
        // given
        long id = 2;
        Reservation reservation = reservationRepository.findById(id);

        // when
        reservationRepository.remove(reservation);

        // then
        assertThatThrownBy(() -> reservationRepository.findById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
