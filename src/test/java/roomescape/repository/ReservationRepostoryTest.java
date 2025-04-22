package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

@SpringBootTest
public class ReservationRepostoryTest {

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "Lemon", "2025-04-22", "16:22");
    }

    @Test
    @DisplayName("아이디를 통해 예약을 가져온다.")
    void findRerservationById() {
        //given
        Long id = 1L;

        //when
        Reservation reservation = reservationRepository.findById(id);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservation.getId()).isEqualTo(1);
            softAssertions.assertThat(reservation.getName()).isEqualTo("Lemon");
            softAssertions.assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2025, 4, 22));
            softAssertions.assertThat(reservation.getTime()).isEqualTo(LocalTime.of( 16, 22));
        });
    }
}
