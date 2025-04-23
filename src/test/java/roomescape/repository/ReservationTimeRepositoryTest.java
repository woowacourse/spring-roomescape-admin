package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.ReservationTime;

@SpringBootTest
public class ReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;


    @Test
    @DisplayName("시간을 추가한다.")
    void addReservationTimeTest(){
        // given
        ReservationTime time = new ReservationTime(LocalTime.of(10,0));
        // when
        ReservationTime reservationTime = reservationTimeRepository.save(time);
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservationTime.getId()).isEqualTo(1);
            softAssertions.assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10,0));
        });
    }

    @Test
    @DisplayName("전체 시간 목록을 가져온다.")
    void getAllReservationTimesTest(){

        //beforeEach
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        //given
        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservationTimes).hasSize(1);
            softAssertions.assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo(LocalTime.of(10, 0));
        });

        //afterEach
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
    }

}
