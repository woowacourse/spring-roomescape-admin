package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.dto.CreateReservationRequest;

@JdbcTest(properties = "spring.datasource.url=jdbc:h2:mem:database-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long timeSlotId;

    @DisplayName("예약 시간을 미리 세팅")
    @BeforeEach
    void setUp() {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        final var savedTimeSlotId = insertActor
            .withTableName("RESERVATION_TIME")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(Map.of("start_at", "10:00"));
        timeSlotId = savedTimeSlotId.longValue();
    }

    @Test
    @DisplayName("예약을 아이디로 조회한다.")
    void findReservation() {
        //given
        ReservationRepository repository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            timeSlotId
        );
        final var savedId = repository.save(request);

        //when
        final var foundReservation = repository.findById(savedId);

        //then
        assertThat(foundReservation).isPresent();
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void addReservation() {
        // given
        ReservationRepository repository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            timeSlotId
        );

        // when
        final var savedId = repository.save(request);
        final var saved = repository.findById(savedId).get();

        // then
        assertThat(repository.getReservations()).containsOnly(saved);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void removeReservation() {
        // given
        ReservationRepository repository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            timeSlotId
        );
        final var savedId = repository.save(request);

        // when
        repository.removeById(savedId);

        // then
        assertThat(repository.getReservations()).isEmpty();
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservation() {
        // given
        ReservationRepository repository = new ReservationJdbcRepository(jdbcTemplate);
        CreateReservationRequest request1 = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            timeSlotId
        );
        CreateReservationRequest request2 = new CreateReservationRequest(
            "브라운",
            LocalDate.of(2023, 12, 1),
            timeSlotId
        );
        repository.save(request1);
        repository.save(request2);

        // when
        // then
        assertThat(repository.getReservations()).hasSize(2);
    }
}
