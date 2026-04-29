package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.JdbcTemplateReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.JdbcTemplateReservationTimeRepository;

@JdbcTest
class ReservationServiceTest {
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");

        JdbcTemplateReservationRepository reservationRepository = new JdbcTemplateReservationRepository(jdbcTemplate);
        JdbcTemplateReservationTimeRepository reservationTimeRepository = new JdbcTemplateReservationTimeRepository(
                jdbcTemplate);
        this.reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

        Long timeId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(15, 40)));
        reservationService.create(new CreateReservationRequest("한다", LocalDate.of(2023, 8, 5), timeId));
        reservationService.create(new CreateReservationRequest("판다", LocalDate.of(2023, 10, 5), timeId));
    }

    @Test
    @DisplayName("전체 예약 정보를 가져온다.")
    void findAll() {
        //given & when
        List<ReservationResponse> reservationsResponse = reservationService.findAll();

        //then
        assertThat(reservationsResponse.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void create() {
        //given & when
        reservationService.create(new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), 1L));

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given
        ReservationResponse reservationResponse = reservationService.create(
                new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), 1L));
        Long id = reservationResponse.id();

        //when
        reservationService.delete(id);

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(2);
    }
}