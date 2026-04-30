package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    private Long timeId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        JdbcTemplateReservationRepository reservationRepository = new JdbcTemplateReservationRepository(jdbcTemplate);
        JdbcTemplateReservationTimeRepository reservationTimeRepository = new JdbcTemplateReservationTimeRepository(
                jdbcTemplate);
        this.reservationService = new ReservationService(reservationRepository, reservationTimeRepository);

        timeId = reservationTimeRepository.save(new ReservationTime(null, LocalTime.of(15, 40)));
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
        reservationService.create(new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), timeId));

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 예약 시간이면 예외를 발생한다.")
    void create_does_not_exist_reservation_time() {
        assertThatThrownBy(
                () -> reservationService.create(new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), 999L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given
        ReservationResponse reservationResponse = reservationService.create(
                new CreateReservationRequest("브라운", LocalDate.of(2023, 1, 1), timeId));
        Long id = reservationResponse.id();

        //when
        reservationService.delete(id);

        //then
        assertThat(reservationService.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("존재하지 않는 예약을 삭제하면 예외를 발생한다.")
    void delete_does_not_exist() {
        assertThatThrownBy(
                () -> reservationService.delete(999L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }
}