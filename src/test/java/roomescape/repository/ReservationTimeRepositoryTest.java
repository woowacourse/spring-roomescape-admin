package roomescape.repository;

import java.time.LocalTime;
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
    void test(){
        // given
        ReservationTime time = new ReservationTime(LocalTime.of(10,0));
        // when
        ReservationTime reservationTime = reservationTimeRepository.save(time);
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservationTime.getId()).isEqualTo(1);
            softAssertions.assertThat(reservationTime.getTime()).isEqualTo(LocalTime.of(10,0));
        });
    }
}
