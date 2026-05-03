package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.repository.h2.H2ReservationRepository.reservationRowMapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class QueryExperiment {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ParameterizedTest
    @CsvSource({
            "1000,1000"
    })
    void test(int reservationCountPerTime, int timeCount) {
        insertReservation(reservationCountPerTime, timeCount);

        long subQuerySpendTime = spendTime(() -> selectWithSubQuery(1000));
        long joinSpendTime = spendTime(() -> selectWithJoin(1000));

        System.out.println("joinSpendTime: " + joinSpendTime);
        System.out.println("subQuerySpendTime: " + subQuerySpendTime);

        assertThat(joinSpendTime).isLessThan(subQuerySpendTime);
    }

    private long spendTime(Runnable runnable) {
        long totalTime = 0L;
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            for (int j = 0; j < 100; j++) {
                runnable.run();
            }
            long end = System.currentTimeMillis();
            totalTime += end - start;
        }
        return totalTime / 100;
    }

    private void selectWithSubQuery(int value) {
        String subQuerySql = """
                SELECT
                    r.id,
                    r.name,
                    r.date,
                    r.time_id,
                    (
                        SELECT t.start_at
                        FROM reservation_time t
                        WHERE t.id = r.time_id
                    ) AS start_at
                FROM reservation r
                WHERE r.id <= :id;
                """;
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", value);
        namedParameterJdbcTemplate.query(subQuerySql, sqlParameterSource, reservationRowMapper());
    }

    private void selectWithJoin(int value) {
        String joinQuerySql = """
                SELECT r.id, r.name, r.date, t.id as time_id, t.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                WHERE r.id <= :id
                """;
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", value);
        namedParameterJdbcTemplate.query(joinQuerySql, sqlParameterSource, reservationRowMapper());
    }

    private void insertReservation(int reservationCountPerTime, int timeCount) {
        for (int i = 0; i < timeCount; i++) {
            ReservationTime time = TestFixture.createTime();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES(:startAt)",
                    new MapSqlParameterSource("startAt", time.getStartAt()), keyHolder);
            long timeId = keyHolder.getKey().longValue();

            Reservation reservation = TestFixture.createReservation();
            String sql = "INSERT INTO reservation(name, date, time_id) VALUES (:name, :date, :timeId)";
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", reservation.getName())
                    .addValue("date", reservation.getDate())
                    .addValue("timeId", timeId);
            for (int j = 0; j < reservationCountPerTime; j++) {
                namedParameterJdbcTemplate.update(sql, params);
            }
        }
    }
}
