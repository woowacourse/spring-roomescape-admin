package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
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
 * {ID=1, NAME=브라운, DATE=2024-05-04, TIME={ID=1, START_AT="10:00"}}
 * {ID=2, NAME=엘라, DATE=2024-05-04, TIME={ID=2, START_AT="11:00"}}
 * {ID=3, NAME=릴리, DATE=2023-08-05, TIME={ID=2, START_AT="11:00"}}
 */
@JdbcTest
@Sql(scripts = "/reset_test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new JdbcReservationRepository(jdbcTemplate);
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
        Reservation findReservations = reservationRepository.findById(2L);

        // then
        assertAll(
                () -> assertThat(findReservations.getName().getValue()).isEqualTo("엘라"),
                () -> assertThat(findReservations.getDate()).isEqualTo("2024-05-04")
        );

    }

    @Test
    @DisplayName("새로운 예약을 생성한다.")
    void create() {
        // given
        Name name = new Name("브라운");
        LocalDate date = LocalDate.parse("2023-08-05");
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.parse("10:00"));
        Reservation createReservation = new Reservation(null, name, date, reservationTime);

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
        Long id = 2L;

        // when
        reservationRepository.removeById(id);

        // then
        assertThatThrownBy(() -> reservationRepository.findById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
