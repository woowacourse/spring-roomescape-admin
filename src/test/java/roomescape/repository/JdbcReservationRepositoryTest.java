package roomescape.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.model.EntityId;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcReservationRepositoryTest {

    private static DataSource dataSource;
    private JdbcReservationRepository jdbcReservationRepository;

    @BeforeAll
    static void initializeDataSource() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "sa", "");
    }

    @BeforeEach
    void initialize() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("initialize.sql"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES(?)", LocalTime.of(10, 0));
        jdbcReservationRepository = new JdbcReservationRepository(jdbcTemplate);
    }

    @Test
    void 예약_객체를_성공적으로_저장한_후_반환한다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(new EntityId(1L), time);
        Reservation reservation1 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        Reservation reservation2 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);

        // When
        Reservation reservation1Entity = jdbcReservationRepository.insert(reservation1);
        Reservation reservation2Entity = jdbcReservationRepository.insert(reservation2);

        // Then
        assertThat(reservation1Entity.getId()).isEqualTo(1L);
        assertThat(reservation1Entity.getName()).isEqualTo(name);
        assertThat(reservation1Entity.getDate()).isEqualTo(date);
        assertThat(reservation1Entity.getTime()).isEqualTo(reservationTime);

        assertThat(reservation2Entity.getId()).isEqualTo(2L);
        assertThat(reservation2Entity.getName()).isEqualTo(name);
        assertThat(reservation2Entity.getDate()).isEqualTo(date);
        assertThat(reservation2Entity.getTime()).isEqualTo(reservationTime);
    }

    @Test
    void 저장된_예약_객체들을_모두_가져온다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(new EntityId(1L), time);
        Reservation reservation1 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        Reservation reservation2 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        Reservation reservation1Entity = jdbcReservationRepository.insert(reservation1);
        Reservation reservation2Entity = jdbcReservationRepository.insert(reservation2);

        // When & Then
        assertThat(jdbcReservationRepository.findAll()).containsExactlyInAnyOrder(reservation1Entity, reservation2Entity);
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
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(new EntityId(1L), time);
        Reservation reservation1 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        Reservation reservation2 = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        jdbcReservationRepository.insert(reservation1);
        Reservation reservation2Entity = jdbcReservationRepository.insert(reservation2);
        Long deleteId = 1L;

        // When
        int rowsAffected = jdbcReservationRepository.deleteById(deleteId);

        // Then
        assertThat(rowsAffected).isEqualTo(1);
        assertThat(jdbcReservationRepository.findAll()).containsExactlyInAnyOrder(reservation2Entity);
    }

    @Test
    void 주어진_날짜와_시간을_가진_예약_객체가_있는지_검사한다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(new EntityId(1L), time);
        Reservation reservation = new Reservation(EntityId.generateUnassigned(), name, date, reservationTime);
        jdbcReservationRepository.insert(reservation);

        // When & Then
        assertThat(jdbcReservationRepository.existByDateAndTimeId(date, 1L))
                .isTrue();
        assertThat(jdbcReservationRepository.existByDateAndTimeId(date, 2L))
                .isFalse();
        assertThat(jdbcReservationRepository.existByDateAndTimeId(LocalDate.of(2000, 1, 1), 1L))
                .isFalse();
    }
}
