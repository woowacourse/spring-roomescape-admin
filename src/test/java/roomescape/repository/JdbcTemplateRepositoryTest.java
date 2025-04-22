package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.Reservation;

class JdbcTemplateRepositoryTest {

    private static EmbeddedDatabase db;
    private JdbcTemplateRepository repository;

    @BeforeAll
    static void initDatabase() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .build();
    }

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateRepository(db);
    }

    @AfterEach
    void cleanUp() {
        new JdbcTemplate(db).execute("DELETE FROM reservation");
    }

    @AfterAll
    static void shutdownDatabase() {
        db.shutdown();
    }

    @Test
    void 예약이_올바르게_생성된다() {
        // given
        String name = "레포지토리테스트";
        LocalDate date = LocalDate.of(2025, 7, 1);
        LocalTime time = LocalTime.of(9, 30);

        // when
        var saved = repository.save(name, date, time);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo(name);
    }

    @Test
    void 기존에_날짜와_시간이_같은_예약이_있는지_확인() {
        // given
        String existedName = "레포지토리테스트";
        LocalDate existedDate = LocalDate.of(2025, 7, 1);
        LocalTime existedTime = LocalTime.of(9, 30);
        LocalDate date = LocalDate.of(2026, 1, 1);
        LocalTime time = LocalTime.of(14, 1);

        repository.save(existedName, existedDate, existedTime);

        // when
        // then
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(repository.existsByDateAndTime(existedDate, existedTime)).isTrue();
            soft.assertThat(repository.existsByDateAndTime(date, time)).isFalse();
        });
    }

    @Test
    void 모든_예약_조회() {
        // given
        repository.save("테스트1", LocalDate.of(2025, 11, 12), LocalTime.of(13, 1));
        repository.save("테스트2", LocalDate.of(2025, 11, 12), LocalTime.of(13, 1));

        // when
        final List<Reservation> reservations = repository.findAll();

        // then
        assertThat(reservations).hasSize(2);
    }
}
