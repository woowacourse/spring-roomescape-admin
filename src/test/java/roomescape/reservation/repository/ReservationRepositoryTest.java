package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.config.TestConfig;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;

class ReservationRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void init() {
        jdbcTemplate = TestConfig.getJdbcTemplate();
        reservationRepository = new ReservationRepositoryImpl(jdbcTemplate);
    }

    @DisplayName("예약 정보를 저장한다.")
    @Test
    void test1() {
        // given
        LocalDateTime now = LocalDateTime.now();
        String name = "꾹";
        Reservation reservation = Reservation.withoutId(name, now);

        // when
        Reservation result = reservationRepository.save(reservation);

        // then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDateTime()).isEqualTo(now);
    }

    @DisplayName("id가 같다면 해당 예약 정보로 변경한다.")
    @Test
    void test4() {
        // given
        long id = 1;
        LocalDateTime now = LocalDateTime.now();
        String originalName = "꾹";
        saveReservation(id, originalName, now);

        String changedName = "드라고";

        Reservation updateReservation = new Reservation(id, changedName, now);

        // when
        Reservation result = reservationRepository.save(updateReservation);

        // then
        assertThat(result).isEqualTo(updateReservation);
    }

    @DisplayName("존재하지 않는 id를 save한다면 예외를 반환한다.")
    @Test
    void test8() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(1L, "꾹", now);

        // when & then
        assertThatThrownBy(() -> reservationRepository.save(reservation))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("id로 예약 정보를 가져온다")
    @Test
    void test5() {
        // given
        long id = 1;
        String name = "꾹";
        LocalDateTime now = LocalDateTime.now();
        saveReservation(id, name, now);

        // when
        Reservation result = reservationRepository.findById(id).get();

        // then

        assertThat(result.getName()).isEqualTo(name);

        ChronoLocalDateTime.timeLineOrder();
        assertThat(result.getDateTime()).isEqualToIgnoringNanos(now);
    }

    @DisplayName("모든 예약 정보를 가져온다.")
    @Test
    void test6() {
        // given
        LocalDateTime now = LocalDateTime.now();

        List<String> names = List.of("꾹", "헤일러", "라젤");

        String sql = "insert into reservation (name, date_time) values (?, ?)";

        for (String name : names) {
            jdbcTemplate.update(sql, name, now);
        }

        // when
        List<Reservation> result = reservationRepository.findAll();

        // then

        List<String> resultNames = result.stream().map(Reservation::getName).toList();
        List<LocalDateTime> resultDateTimes = result.stream().map(Reservation::getDateTime).toList();

        assertThat(resultNames).containsAll(names);

        for (LocalDateTime resultDateTime : resultDateTimes) {
            assertThat(resultDateTime).isEqualToIgnoringNanos(now);
        }
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void test7() {
        // given
        long id = 1;
        String name = "꾹";
        LocalDateTime now = LocalDateTime.now();

        saveReservation(id, name, now);

        // when
        reservationRepository.deleteById(id);

        // then
        Optional<Reservation> result = reservationRepository.findById(id);
        assertThat(result).isEmpty();
    }

    private void saveReservation(Long id, String name, LocalDateTime dateTime) {
        String sql = "insert into reservation (id, name, date_time) values (?, ?, ?)";
        jdbcTemplate.update(sql, id, name, dateTime);
    }

    @AfterEach
    void cleanUp(){
        jdbcTemplate.update("truncate TABLE reservation");
    }
}
