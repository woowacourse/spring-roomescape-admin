package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import roomescape.RoomescapeApplication;
import roomescape.service.ReservationService;
import roomescape.service.command.ReservationCommand;

@SpringBootTest(classes = {
        RoomescapeApplication.class,
        ReservationServiceIntTest.class
})
@ActiveProfiles("test")
class ReservationServiceIntTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("TRUNCATE TABLE reservation_time");

        // H2 - id 초기화
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");

        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", LocalTime.of(11, 0));
    }

    @Test
    void 동시에_2명이_예약하면_1명만_성공해야_한다() throws InterruptedException {
        // given
        ReservationCommand command = new ReservationCommand("이프", LocalDate.now().plusDays(1), 1L);
        int threadCount = 2;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger errorCount = new AtomicInteger(0);

        // when
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> {
                    try {
                        reservationService.reserve(command);
                    } catch (DataIntegrityViolationException e) {
                        errorCount.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        latch.await();

        // then: DB에 예약이 딱 하나만 있어야 하고, DataIntergrityViolation 예외가 한 번 발생해야 됨.
        assertThat(reservationService.getAllReservations()).hasSize(1);
        assertThat(errorCount.get()).isEqualTo(1);
    }
}