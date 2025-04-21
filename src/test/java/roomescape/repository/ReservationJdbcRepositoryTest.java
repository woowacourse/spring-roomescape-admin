package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.CreateReservationRequest;

@JdbcTest(properties = "spring.datasource.url=jdbc:h2:mem:database-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약을 아이디로 조회한다.")
    void findReservation() {
        //given
        ReservationJdbcRepository reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            LocalTime.of(1, 1)
        );
        final var savedId = reservationJdbcRepository.save(request);

        //when
        final var foundReservation = reservationJdbcRepository.findById(savedId);

        //then
        assertThat(foundReservation).isPresent();
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void addReservation() {
        // given
        ReservationJdbcRepository reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            LocalTime.of(1, 1)
        );

        // when
        final var savedId = reservationJdbcRepository.save(request);
        final var saved = reservationJdbcRepository.findById(savedId).get();

        // then
        assertThat(reservationJdbcRepository.getReservations()).containsOnly(saved);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void removeReservation() {
        // given
        ReservationJdbcRepository reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            LocalTime.of(1, 1)
        );
        final var savedId = reservationJdbcRepository.save(request);

        // when
        reservationJdbcRepository.removeById(savedId);

        // then
        assertThat(reservationJdbcRepository.getReservations()).isEmpty();
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservation() {
        // given
        ReservationJdbcRepository reservationJdbcRepository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request1 = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            LocalTime.of(1, 1)
        );
        CreateReservationRequest request2 = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            LocalTime.of(1, 1)
        );
        reservationJdbcRepository.save(request1);
        reservationJdbcRepository.save(request2);

        // when
        // then
        assertThat(reservationJdbcRepository.getReservations()).hasSize(2);
    }
}
