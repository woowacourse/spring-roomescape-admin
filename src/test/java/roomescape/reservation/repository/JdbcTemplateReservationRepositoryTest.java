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
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.JdbcTemplateReservationTimeRepository;

@JdbcTest
class JdbcTemplateReservationRepositoryTest {
    private JdbcTemplateReservationRepository jdbcTemplateReservationRepository;
    private JdbcTemplateReservationTimeRepository jdbcTemplateReservationTimeRepository;
    private Long timeId;
    private Long reservationId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplateReservationRepository = new JdbcTemplateReservationRepository(jdbcTemplate);
        jdbcTemplateReservationTimeRepository = new JdbcTemplateReservationTimeRepository(jdbcTemplate);

        timeId = jdbcTemplateReservationTimeRepository.save(new ReservationTime(null, LocalTime.of(15, 40)));
        ReservationTime reservationTime = jdbcTemplateReservationTimeRepository.findById(timeId).get();

        reservationId = jdbcTemplateReservationRepository.save(
                new Reservation(null, "한다", LocalDate.of(2023, 8, 5), reservationTime));
        jdbcTemplateReservationRepository.save(new Reservation(null, "판다", LocalDate.of(2023, 10, 5), reservationTime));
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
        ReservationTime reservationTime = jdbcTemplateReservationTimeRepository.findById(timeId).get();
        jdbcTemplateReservationRepository.save(
                new Reservation(null, "새로운사람", LocalDate.of(2023, 6, 5), reservationTime));

        //then
        assertThat(jdbcTemplateReservationRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given & when
        jdbcTemplateReservationRepository.delete(reservationId);

        //then
        assertThat(jdbcTemplateReservationRepository.findAll().size()).isEqualTo(1);
    }
}