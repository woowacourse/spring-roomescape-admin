package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.domain.Reservation;

@JdbcTest
class JdbcTemplateReservationRepositoryTest {
    private JdbcTemplateReservationRepository jdbcTemplateReservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");

        jdbcTemplateReservationRepository = new JdbcTemplateReservationRepository(jdbcTemplate);

        jdbcTemplateReservationRepository.save(new Reservation(
                null, "한다", LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)));
        jdbcTemplateReservationRepository.save(new Reservation(
                null, "판다", LocalDate.of(2023, 10, 5),
                LocalTime.of(15, 40)));
    }

    @Test
    @DisplayName("모든 예약 정보를 조회한다.")
    void findAll() {
        assertThat(jdbcTemplateReservationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void save() {
        //given & when
        jdbcTemplateReservationRepository.save(
                new Reservation(3L, "새로운사람", LocalDate.of(2023, 6, 5), LocalTime.of(12, 0)));

        //then
        assertThat(jdbcTemplateReservationRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given & when
        jdbcTemplateReservationRepository.delete(2L);

        //then
        assertThat(jdbcTemplateReservationRepository.findAll().size()).isEqualTo(1);
    }
}