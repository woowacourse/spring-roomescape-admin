package roomescape.repository;

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
import roomescape.dao.ReservationDao;
import roomescape.exception.ReservationNotFoundException;
import roomescape.model.Reservation;

class ReservationRepositoryTest {

    private static final String TEST_PROPERTIES = "application-test.properties";

    private ReservationRepository reservationRepository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        Properties properties = loadTestProperties();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(properties.getProperty("spring.datasource.url"));
        dataSource.setUsername(properties.getProperty("spring.datasource.username"));
        dataSource.setPassword(properties.getProperty("spring.datasource.password"));

        jdbcTemplate = new JdbcTemplate(dataSource);
        ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
        reservationRepository = new ReservationRepository(reservationDao);

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
    void 예약을_저장하면_1부터_시작하는_ID가_부여된다() {
        Reservation first = reservationRepository.save("브라운", "2023-08-05", "15:40");
        Reservation second = reservationRepository.save("코니", "2023-08-06", "16:00");

        assertThat(first.id()).isEqualTo(1L);
        assertThat(second.id()).isEqualTo(2L);
    }

    @Test
    void 저장된_예약을_전체_조회할_수_있다() {
        reservationRepository.save("브라운", "2023-08-05", "15:40");
        reservationRepository.save("코니", "2023-08-06", "16:00");

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .extracting(Reservation::name)
                .containsExactly("브라운", "코니");
    }

    @Test
    void 존재하는_ID로_예약을_삭제할_수_있다() {
        Reservation reservation = reservationRepository.save("브라운", "2023-08-05", "15:40");

        reservationRepository.delete(reservation.id());

        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_ID로_삭제하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationRepository.delete(1L))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessage("Reservation not found: 1");
    }
}
