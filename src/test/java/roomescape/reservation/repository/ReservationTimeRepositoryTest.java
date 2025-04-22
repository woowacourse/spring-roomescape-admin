package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.common.exception.AlreadyInUseException;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.config.TestConfig;
import roomescape.reservation.entity.ReservationTime;

class ReservationTimeRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void init() {
        jdbcTemplate = TestConfig.getJdbcTemplate();

        reservationTimeRepository = new ReservationTimeRepositoryImpl(jdbcTemplate);
    }

    @DisplayName("id에 따라 예약 시간을 반환한다.")
    @Test
    void test1() {
        // given
        Long id = 1L;
        LocalTime now = LocalTime.now();
        saveReservationTime(id, now);

        // when
        ReservationTime result = reservationTimeRepository.findById(id).get();

        // then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getStartAt()).isEqualTo(now);
    }

    @DisplayName("해당 id가 없다면 예외를 반환한다.")
    @Test
    void test2() {
        assertThatThrownBy(() -> reservationTimeRepository.deleteById(1L))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @DisplayName("해당 ID가 DB에 없다면 예외를 반환한다.")
    @Test
    void test7() {
        assertThatThrownBy(() -> reservationTimeRepository.deleteById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    /**
     *    외래키 제약 조건에 의해 truncate를 사용할 수 없다. 제약을 거는게 맞을까?
     *    코드 레벨에서 처리 방법은? 물론, 데이터베이스 제약에 의해 문제를 인지할 수 있다는 장점은 있다.
     *    유연성을 준다면, 제약을 코드 레벨에서 하는 방법도 좋다고 생각한다.
     */
    @AfterEach
    void cleanUp() {
        jdbcTemplate.update("truncate table reservation");
        jdbcTemplate.update("delete from reservation_time");
    }

    private void saveReservationTime(Long id, LocalTime startAt) {
        String sql = "insert into reservation_time (id, start_at) values (?, ?)";
        jdbcTemplate.update(sql, id, startAt);
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Nested
    class delete {

        @DisplayName("해당 ID를 삭제한다.")
        @Test
        void test1() {
            // given
            Long id = 1L;
            LocalTime now = LocalTime.of(9, 0);
            String sql = "insert into reservation_time(id, start_at) values(?, ?)";
            jdbcTemplate.update(sql, id, now);

            // when
            assertThatCode(() -> reservationTimeRepository.deleteById(id))
                    .doesNotThrowAnyException();
        }

        @DisplayName("Reservation 테이블에서 사용 중이라면 AlreadyUseException 예외를 반환한다.")
        @Test
        void test2(){
            // given
            Long id = 1L;
            LocalTime now = LocalTime.of(9, 0);
            String sql = "insert into reservation_time(id, start_at) values(?, ?)";
            jdbcTemplate.update(sql, id, now);

            jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)", "꾹", LocalTime.now(), id);

            // when
            assertThatThrownBy(() -> reservationTimeRepository.deleteById(id))
                    .isInstanceOf(AlreadyInUseException.class);
        }
    }

    @DisplayName("예약 시간을 저장한다.")
    @Nested
    class save {

        @DisplayName("성공 테스트")
        @Test
        void test1() {
            // given
            LocalTime now = LocalTime.now();
            ReservationTime time = ReservationTime.withoutId(now);

            // when
            ReservationTime saved = reservationTimeRepository.save(time);

            // then
            /**
             * 가져온 ID값에 대하여 SELECT로 재검증을 해야하는가?
             * 해당 영역은 findById로 테스트할 수 있다고 생각한다. Null 이 아닌 것에 대해서만 검증하면 된다고 느낀다.
             */
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getStartAt()).isEqualTo(now);
        }

        @DisplayName("ID가 있다면 해당 ID로 데이터를 업데이트한다.")
        @Test
        void test5() {
            // given
            Long id = 1L;
            LocalTime now = LocalTime.of(9, 0);
            String sql = "insert into reservation_time(id, start_at) values(?, ?)";
            jdbcTemplate.update(sql, id, now);

            // when
            LocalTime changeTime = LocalTime.of(10, 0);
            ReservationTime reservationTime = new ReservationTime(id, changeTime);
            ReservationTime result = reservationTimeRepository.save(reservationTime);

            // then

            SoftAssertions softly = new SoftAssertions();

            softly.assertThat(result.getId()).isEqualTo(id);
            softly.assertThat(result.getStartAt()).isEqualTo(changeTime);

            softly.assertAll();
        }

        @DisplayName("DB에 해당 ID가 없고, 객체에 ID가 존재하는데 저장 시 예외를 반환한다.")
        @Test
        void test6() {
            // given
            Long id = 1L;
            LocalTime now = LocalTime.of(9, 0);
            ReservationTime reservationTime = new ReservationTime(id, now);

            // when & then
            assertThatThrownBy(() -> reservationTimeRepository.save(reservationTime))
                    .isInstanceOf(EntityNotFoundException.class);
        }
    }

}
