package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.exception.ApiException;
import roomescape.exception.ErrorCode;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

class ReservationDaoTest {

    private static final String TEST_PROPERTIES = "application-test.properties";

    private ReservationDao reservationDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        Properties properties = loadTestProperties();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(properties.getProperty("spring.datasource.url"));
        dataSource.setUsername(properties.getProperty("spring.datasource.username"));
        dataSource.setPassword(properties.getProperty("spring.datasource.password"));

        jdbcTemplate = new JdbcTemplate(dataSource);
        reservationDao = new ReservationDao(jdbcTemplate);

        jdbcTemplate.execute("RUNSCRIPT FROM 'classpath:reset-test.sql'");
    }

    private Properties loadTestProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TEST_PROPERTIES)) {
            if (inputStream == null) {
                throw new IllegalStateException("Test properties not found: " + TEST_PROPERTIES);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test properties: " + TEST_PROPERTIES, e);
        }
    }

    @Test
    void 예약을_저장할_수_있다() {
        ReservationTime reservationTime = createReservationTime("15:40");
        Reservation saved = reservationDao.save("브라운", "2023-08-05", reservationTime);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("브라운");
        assertThat(saved.date()).isEqualTo("2023-08-05");
        assertThat(saved.time().startAt()).isEqualTo("15:40");
    }

    @Test
    void 저장된_예약을_전체_조회할_수_있다() {
        ReservationTime firstTime = createReservationTime("15:40");
        ReservationTime secondTime = createReservationTime("16:00");

        reservationDao.save("브라운", "2023-08-05", firstTime);
        reservationDao.save("코니", "2023-08-06", secondTime);

        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .extracting(Reservation::name)
                .containsExactly("브라운", "코니");
    }

    @Test
    void 존재하는_ID로_예약을_삭제할_수_있다() {
        ReservationTime reservationTime = createReservationTime("15:40");
        Reservation saved = reservationDao.save("브라운", "2023-08-05", reservationTime);

        reservationDao.delete(saved.id());

        assertThat(reservationDao.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_ID로_삭제하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationDao.delete(1L))
                .isInstanceOf(ApiException.class)
                .extracting(exception -> ((ApiException) exception).getErrorCode())
                .isEqualTo(ErrorCode.RESERVATION_NOT_FOUND);
    }

    private ReservationTime createReservationTime(String startAt) {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", startAt);
        Long id = jdbcTemplate.queryForObject(
                "SELECT id FROM reservation_time WHERE start_at = ? ORDER BY id DESC LIMIT 1",
                Long.class,
                startAt
        );
        return new ReservationTime(id, startAt);
    }
}
