package roomescape.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.service.dto.ReservationServiceRequest;
import roomescape.web.repository.JdbcReservationRepository;
import roomescape.web.repository.JdbcReservationTimeRepository;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private JdbcReservationRepository jdbcReservationRepository;
    @Autowired
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @AfterEach
    void afterEach() {
        jdbcReservationRepository.deleteAll();
        jdbcReservationTimeRepository.deleteAll();
    }

    @DisplayName("예약 시간이 존재하지 않으면 예약을 생성할 수 없다.")
    @Test
    void createNonExistingTime() {
        var reservation = new ReservationServiceRequest("pobi", LocalDate.parse("2020-10-10"), 1L);

        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 시간 ID에 해당하는 시간으로 예약을 생성한다.")
    @Test
    void create() {
        // given
        ReservationTime time = jdbcReservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:10")));
        Long timeId = time.getId();
        var reservation = new ReservationServiceRequest("pobi", LocalDate.parse("2020-10-10"), timeId);

        // when
        var reservationServiceResponse = reservationService.create(reservation);

        // then
        Reservation savedReservation = jdbcReservationRepository.findById(reservationServiceResponse.id()).get();

        assertThat(savedReservation.getTimeId()).isEqualTo(timeId);
    }

    @DisplayName("존재하지 않는 예약을 삭제할 수 없다.")
    @Test
    void deleteNonExisting() {
        assertThatThrownBy(() -> reservationService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 ID로 예약을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.save(new ReservationTime(LocalTime.parse("10:10")));
        Reservation reservation = jdbcReservationRepository.save(
                new Reservation("pobi", LocalDate.parse("2020-10-10"), reservationTime));
        Long id = reservation.getId();

        // when
        reservationService.delete(id);

        // then
        assertThat(jdbcReservationRepository.findById(id)).isEmpty();
    }
}
