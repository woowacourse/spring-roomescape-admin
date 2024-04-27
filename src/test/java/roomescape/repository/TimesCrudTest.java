package roomescape.repository;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TimesCrudTest {

    @Autowired
    private DbReservationTimeRepository reservationTimeRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    void 시간을_추가한다() {
        //given
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(1L, "12:00");

        //when
        reservationTimeRepository.add(reservationTimeDto);

        //then
        assertThat(reservationTimeRepository.findAll()).contains(reservationTimeDto);
    }

    @Test
    void 특정_시간을_조회한다() {
        //given
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(1L, "12:00");

        //when
        reservationTimeRepository.add(reservationTimeDto);

        //then
        assertThat(reservationTimeRepository.findById(1L)).isEqualTo(reservationTimeDto);
    }


    @Test
    void 모든_시간을_조회한다() {
        //given
        ReservationTimeDto reservationTimeDtoA = new ReservationTimeDto(1L, "12:00");
        ReservationTimeDto reservationTimeDtoB = new ReservationTimeDto(2L, "13:00");

        //when
        reservationTimeRepository.add(reservationTimeDtoA);
        reservationTimeRepository.add(reservationTimeDtoB);

        //then
        assertThat(reservationTimeRepository.findAll()).hasSize(2);
    }

    @Test
    void 시간을_삭제한다() {
        //given
        Long id = 1L;
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(id, "00:00");

        //when
        reservationTimeRepository.add(reservationTimeDto);
        reservationTimeRepository.remove(id);

        //then
        assertThat(reservationTimeRepository.findAll()).doesNotContain(reservationTimeDto);
    }
}
