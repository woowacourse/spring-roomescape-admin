package roomescape.time.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.time.domain.ReservationTime;

@JdbcTest
class ReservationTimeRepositoryTest {
    private JdbcTemplateReservationTimeRepository jdbcTemplateReservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");

        jdbcTemplateReservationTimeRepository = new JdbcTemplateReservationTimeRepository(jdbcTemplate);

        jdbcTemplateReservationTimeRepository.save(new ReservationTime(null,
                LocalTime.of(15, 40)));
        jdbcTemplateReservationTimeRepository.save(new ReservationTime(
                null, LocalTime.of(15, 40)));
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void save() {
        //given & when
        jdbcTemplateReservationTimeRepository.save(
                new ReservationTime(null, LocalTime.of(12, 0)));

        //then
        assertThat(jdbcTemplateReservationTimeRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 예약 시간 정보를 조회한다.")
    void findAll() {
        assertThat(jdbcTemplateReservationTimeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given & when
        jdbcTemplateReservationTimeRepository.delete(2L);

        //then
        assertThat(jdbcTemplateReservationTimeRepository.findAll().size()).isEqualTo(1);
    }

}