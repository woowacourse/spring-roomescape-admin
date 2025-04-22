package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "Lemon", "2025-04-22",
                "16:22");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "DDinghwa", "2025-06-03",
                "13:00");
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
            softAssertions.assertThat(reservation.getTime()).isEqualTo(LocalTime.of(16, 22));
        });
    }

    @Test
    @DisplayName("모든 에약을 가져온다.")
    void findALLReservations() {
        //given
        //when
        List<Reservation> reservations = reservationRepository.findAll();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservations).hasSize(2);
            softAssertions.assertThat(reservations.getFirst().getName()).isEqualTo("Lemon");
            softAssertions.assertThat(reservations.getFirst().getDate()).isEqualTo(LocalDate.of(2025, 4, 22));
            softAssertions.assertThat(reservations.getFirst().getTime()).isEqualTo(LocalTime.of(16, 22));
            softAssertions.assertThat(reservations.getLast().getName()).isEqualTo("DDinghwa");
            softAssertions.assertThat(reservations.getLast().getDate()).isEqualTo(LocalDate.of(2025, 6, 3));
            softAssertions.assertThat(reservations.getLast().getTime()).isEqualTo(LocalTime.of(13, 0));
        });
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void saveReservation(){
        // given
        Reservation reservation = new Reservation("myeonghwa", LocalDate.of(1998, 6, 3), LocalTime.of(10, 0));

        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(savedReservation.getId()).isEqualTo(3);
            softAssertions.assertThat(savedReservation.getName()).isEqualTo("myeonghwa");
            softAssertions.assertThat(savedReservation.getDate()).isEqualTo( LocalDate.of(1998, 6, 3));
            softAssertions.assertThat(savedReservation.getTime()).isEqualTo(LocalTime.of(10, 0));

        });
    }
}
