package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;
import roomescape.dto.ReservationRequestDTO;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationControllerTest {
    private ReservationController controller;
    private ReservationRequestDTO requestDTO;
    private ResponseEntity<Reservation> createResponse;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE reservation_time(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, start_at VARCHAR(255) NOT NULL)");

        jdbcTemplate.execute("CREATE TABLE reservation(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255) NOT NULL, " +
                "time_id BIGINT, " +
                "FOREIGN KEY (time_id) REFERENCES reservation_time (id))");

        ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);
        ReservationTimeDAO reservationTimeDAO = new ReservationTimeDAO(jdbcTemplate);
        ReservationService reservationService = new ReservationService(reservationDAO, reservationTimeDAO);
        controller = new ReservationController(reservationService);

        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "15:00");

        requestDTO = new ReservationRequestDTO("user1", LocalDate.of(2026, 4, 29), 1L);

        createResponse = controller.create(requestDTO);
    }

    @Test
    @DisplayName("예약자를 추가하면 200 코드를 반환한다.")
    void return200OK_When_AddReservation() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("예약자를 조회하면 추가한 예약자의 정보를 반환한다.")
    void returnReservationInfo_When_AddReservation() {
        ResponseEntity<List<Reservation>> readResponse = controller.read();
        List<Reservation> reservations = readResponse.getBody();

        assertThat(reservations).isNotNull();
        assertThat(reservations).hasSize(1);
        assertThat(reservations.getFirst().getName()).isEqualTo("user1");
    }

    @Test
    @DisplayName("예약자를 삭제하면 200 코드를 반환한다.")
    void return200OK_When_DeleteReservation() {
        ResponseEntity<List<Reservation>> readResponse = controller.read();
        List<Reservation> reservations = readResponse.getBody();
        Long id = reservations.getFirst().getId();
        int beforeSize = reservations.size();

        ResponseEntity<Void> deleteResponse = controller.delete(id);
        int afterSize = controller.read().getBody().size();

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
