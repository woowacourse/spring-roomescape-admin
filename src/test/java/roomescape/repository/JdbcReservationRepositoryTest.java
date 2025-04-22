package roomescape.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.model.Reservation;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JdbcReservationRepositoryTest {

    private static DataSource dataSource;
    private JdbcReservationRepository jdbcReservationRepository;

    @BeforeAll
    static void initializeDataSource() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:database", "sa", "");
    }

    @BeforeEach
    void initialize() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("initialize.sql"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcReservationRepository = new JdbcReservationRepository(jdbcTemplate);
    }

    @Test
    void 예약_객체를_성공적으로_저장한_후_반환한다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());

        // When & Then
        assertThat(jdbcReservationRepository.insertAndGet(reservationExcludeIndex1)).isEqualTo(reservationExcludeIndex1);
        assertThat(jdbcReservationRepository.insertAndGet(reservationExcludeIndex2)).isEqualTo(reservationExcludeIndex2);
    }

    @Test
    void 저장된_예약_객체들을_모두_가져온다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());

        // When
        jdbcReservationRepository.insertAndGet(reservationExcludeIndex1);
        jdbcReservationRepository.insertAndGet(reservationExcludeIndex2);

        // Then
        assertThat(jdbcReservationRepository.findAll()).isEqualTo(List.of(
                reservationExcludeIndex1, reservationExcludeIndex2
        ));
    }

    @Test
    void 저장된_예약이_없는_경우_빈_리스트를_반환한다() {
        // Given
        // When
        // Then
        assertThat(jdbcReservationRepository.findAll()).isEqualTo(Collections.emptyList());
    }

    @Test
    void 주어진_id의_예약을_삭제한다() {
        // Given
        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), LocalTime.now());
        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), LocalTime.now());
        jdbcReservationRepository.insertAndGet(reservationExcludeIndex1);
        jdbcReservationRepository.insertAndGet(reservationExcludeIndex2);
        Long deleteId = 1L;

        // When
        jdbcReservationRepository.deleteById(deleteId);

        // Then
        assertThat(jdbcReservationRepository.findAll()).isEqualTo(List.of(reservationExcludeIndex2));
    }

    @Test
    void 존재하지_않는_예약의_id로는_삭제할_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> jdbcReservationRepository.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }
}
