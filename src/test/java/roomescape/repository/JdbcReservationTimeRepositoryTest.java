package roomescape.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import roomescape.model.ReservationTime;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JdbcReservationTimeRepositoryTest {

    private static DataSource dataSource;
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @BeforeAll
    static void initializeDataSource() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "sa", "");
    }

    @BeforeEach
    void initialize() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("initialize.sql"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcReservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Test
    void 예약시간_객체를_성공적으로_저장한_후_반환한다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(time);

        // When
        ReservationTime reservationTimeEntity = jdbcReservationTimeRepository.insertAndReturn(reservationTime);

        // Then
        assertThat(reservationTimeEntity.getId()).isEqualTo(1L);
        assertThat(reservationTimeEntity.getStartAt()).isEqualTo(time);
    }

    @Test
    void 저장된_예약시간_객체들을_모두_가져온다() {
        // Given
        LocalTime time1 = LocalTime.of(10, 0);
        ReservationTime reservationTime1 = new ReservationTime(time1);
        ReservationTime reservationTime1Entity = jdbcReservationTimeRepository.insertAndReturn(reservationTime1);
        LocalTime time2 = LocalTime.of(11, 0);
        ReservationTime reservationTime2 = new ReservationTime(time2);
        ReservationTime reservationTime2Entity = jdbcReservationTimeRepository.insertAndReturn(reservationTime2);

        // When & Then
        assertThat(jdbcReservationTimeRepository.findAll()).containsExactlyInAnyOrder(reservationTime1Entity, reservationTime2Entity);
    }


    @Test
    void 저장된_예약시간이_없는_경우_빈_리스트를_반환한다() {
        // Given
        // When
        // Then
        assertThat(jdbcReservationTimeRepository.findAll()).isEqualTo(Collections.emptyList());
    }

    @Test
    void 주어진_id를_가진_예약시간을_반환한다() {
        // Given
        LocalTime time1 = LocalTime.of(10, 0);
        ReservationTime reservationTime1 = new ReservationTime(time1);
        ReservationTime reservationTime1Entity = jdbcReservationTimeRepository.insertAndReturn(reservationTime1);
        LocalTime time2 = LocalTime.of(11, 0);
        ReservationTime reservationTime2 = new ReservationTime(time2);
        ReservationTime reservationTime2Entity = jdbcReservationTimeRepository.insertAndReturn(reservationTime2);

        // When & Then
        assertThat(jdbcReservationTimeRepository.findById(1L)).isEqualTo(reservationTime1Entity);
        assertThat(jdbcReservationTimeRepository.findById(2L)).isEqualTo(reservationTime2Entity);
    }

    @Test
    void 존재하지_않는_예약시간의_id로_찾으면_예외가_발생한다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> jdbcReservationTimeRepository.findById(5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약시간의 id입니다.");
    }

    @Test
    void 주어진_id의_예약시간을_삭제한다() {
        // Given
        LocalTime time1 = LocalTime.of(10, 0);
        ReservationTime reservationTime1 = new ReservationTime(time1);
        jdbcReservationTimeRepository.insertAndReturn(reservationTime1);
        LocalTime time2 = LocalTime.of(11, 0);
        ReservationTime reservationTime2 = new ReservationTime(time2);
        ReservationTime reservationTime2Entity = jdbcReservationTimeRepository.insertAndReturn(reservationTime2);
        Long deleteId = 1L;

        // When
        int rowsAffected = jdbcReservationTimeRepository.deleteByIdAndCountAffected(deleteId);

        // Then
        assertThat(rowsAffected).isEqualTo(1);
        assertThat(jdbcReservationTimeRepository.findAll()).containsExactlyInAnyOrder(reservationTime2Entity);
    }

    @Test
    void 주어진_시작시간을_가진_예약시간_객체가_있는지_검사한다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(time);
        jdbcReservationTimeRepository.insertAndReturn(reservationTime);

        // When & Then
        assertThat(jdbcReservationTimeRepository.existByStartAt(time)).isTrue();
        assertThat(jdbcReservationTimeRepository.existByStartAt(LocalTime.of(11, 0))).isFalse();
    }
}
