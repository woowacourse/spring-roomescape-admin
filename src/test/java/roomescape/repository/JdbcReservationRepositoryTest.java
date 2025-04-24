//package roomescape.repository;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//import roomescape.model.Reservation;
//import roomescape.model.ReservationTime;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class JdbcReservationRepositoryTest {
//
//    private static DataSource dataSource;
//    private JdbcReservationRepository jdbcReservationRepository;
//    private ReservationTimeRepository reservationTimeRepository;
//
//    @BeforeAll
//    static void initializeDataSource() {
//        dataSource = new DriverManagerDataSource("jdbc:h2:mem:database", "sa", "");
//    }
//
//    @BeforeEach
//    void initialize() throws SQLException {
//        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("initialize.sql"));
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcReservationRepository = new JdbcReservationRepository(jdbcTemplate);
//        reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
//    }
//
//    @Test
//    void 예약_객체를_성공적으로_저장한_후_반환한다() {
//        // Given
//        LocalTime time = LocalTime.now();
//        ReservationTime reservationTime = new ReservationTime(time);
//        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), reservationTime);
//        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), reservationTime);
//
//        // When & Then
//        assertThat(jdbcReservationRepository.insertAndGet(reservationExcludeIndex1)).isEqualTo(reservationExcludeIndex1);
//        assertThat(jdbcReservationRepository.insertAndGet(reservationExcludeIndex2)).isEqualTo(reservationExcludeIndex2);
//    }
//
//    @Test
//    void 저장된_예약_객체들을_모두_가져온다() {
//        // Given
//        LocalTime time = LocalTime.now();
//        ReservationTime reservationTime = new ReservationTime(time);
//        reservationTimeRepository.insertAndGet(reservationTime);
//        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), reservationTime);
//        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), reservationTime);
//
//        // When
//        jdbcReservationRepository.insertAndGet(reservationExcludeIndex1);
//        jdbcReservationRepository.insertAndGet(reservationExcludeIndex2);
//
//        // Then
//        assertThat(jdbcReservationRepository.findAll()).isEqualTo(List.of(
//                reservationExcludeIndex1, reservationExcludeIndex2
//        ));
//    }
//
//    @Test
//    void 저장된_예약이_없는_경우_빈_리스트를_반환한다() {
//        // Given
//        // When
//        // Then
//        assertThat(jdbcReservationRepository.findAll()).isEqualTo(Collections.emptyList());
//    }
//
//    @Test
//    void 주어진_id의_예약을_삭제한다() {
//        // Given
//        LocalTime time = LocalTime.now();
//        ReservationTime reservationTime = new ReservationTime(time);
//        Reservation reservationExcludeIndex1 = new Reservation("프리", LocalDate.now(), reservationTime);
//        Reservation reservationExcludeIndex2 = new Reservation("프리2", LocalDate.now(), reservationTime);
//        jdbcReservationRepository.insertAndGet(reservationExcludeIndex1);
//        jdbcReservationRepository.insertAndGet(reservationExcludeIndex2);
//        Long deleteId = 1L;
//
//        // When
//        jdbcReservationRepository.deleteByIdAndCountAffected(deleteId);
//
//        // Then
//        assertThat(jdbcReservationRepository.findAll()).isEqualTo(List.of(reservationExcludeIndex2));
//    }
//}
