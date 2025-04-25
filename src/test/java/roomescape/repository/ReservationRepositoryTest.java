package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.model.ReservationTime;

@JdbcTest
@Import({ReservationRepository.class, ReservationTimeRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    private static final ReservationRequest REQUEST_INPUT = new ReservationRequest(
            "kim",
            "2025-04-19",
            1L
    );

    @BeforeEach
    void setUp() {
        reservationTimeRepository.createReservationTime(new ReservationTime(1L, LocalTime.parse("18:00")));
    }

    @DisplayName("예약 내역을 조회하는 기능을 구현한다")
    @Test
    void readReservations() {
        assertThatCode(reservationRepository::readAllReservations).doesNotThrowAnyException();
    }

    @DisplayName("예약 내역을 추가하는 기능을 구현한다")
    @Test
    void createReservation() {
        assertThatCode(() -> reservationRepository.createReservation(
                REQUEST_INPUT.toReservation())).doesNotThrowAnyException();
    }

    @DisplayName("예약 내역을 삭제하는 기능을 구현한다")
    @Test
    void deleteReservation() {
        reservationRepository.createReservation(REQUEST_INPUT.toReservation());

        assertThatCode(() -> reservationRepository.deleteReservationById(1L)).doesNotThrowAnyException();
    }

    @DisplayName("동일 날짜의 동일 시간대에 예약하려는 경우 예외 처리한다")
    @Test
    void throwException_Duplicates() {
        String date = "2025-04-18";
        Long timeId = 1L;

        ReservationRequest reservationRequest1 = new ReservationRequest("brie", date, timeId);
        reservationRepository.createReservation(reservationRequest1.toReservation());

        ReservationRequest reservationRequest2 = new ReservationRequest("neo", date, timeId);
        assertThatThrownBy(() -> reservationRepository.createReservation(reservationRequest2.toReservation()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("삭제하려는 예약 데이터의 아이디가 존재하지 않는 경우 예외 처리한다")
    @Test
    void throwException_InvalidId() {
        assertThatThrownBy(() -> reservationRepository.deleteReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("jdbc template 데이터베이스 연결을 확인한다")
    @Test
    void checkJdbcTemplateConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
