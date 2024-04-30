package roomescape.repository;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRepositoryTimeDto;

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
        ReservationRepositoryTimeDto reservationRepositoryTimeDto = new ReservationRepositoryTimeDto(1L, "12:00");

        //when
        reservationTimeRepository.add(reservationRepositoryTimeDto);

        //then
        assertThat(reservationTimeRepository.findAll()).contains(reservationRepositoryTimeDto);
    }

    @Test
    void 특정_시간을_조회한다() {
        //given
        ReservationRepositoryTimeDto reservationRepositoryTimeDto = new ReservationRepositoryTimeDto(1L, "12:00");

        //when
        reservationTimeRepository.add(reservationRepositoryTimeDto);

        //then
        assertThat(reservationTimeRepository.findById(1L)).isEqualTo(reservationRepositoryTimeDto);
    }


    @Test
    void 모든_시간을_조회한다() {
        //given
        ReservationRepositoryTimeDto reservationRepositoryTimeDtoA = new ReservationRepositoryTimeDto(1L, "12:00");
        ReservationRepositoryTimeDto reservationRepositoryTimeDtoB = new ReservationRepositoryTimeDto(2L, "13:00");

        //when
        reservationTimeRepository.add(reservationRepositoryTimeDtoA);
        reservationTimeRepository.add(reservationRepositoryTimeDtoB);

        //then
        assertThat(reservationTimeRepository.findAll()).hasSize(2);
    }

    @Test
    void 시간을_삭제한다() {
        //given
        Long id = 1L;
        ReservationRepositoryTimeDto reservationRepositoryTimeDto = new ReservationRepositoryTimeDto(id, "00:00");

        //when
        reservationTimeRepository.add(reservationRepositoryTimeDto);
        reservationTimeRepository.remove(id);

        //then
        assertThat(reservationTimeRepository.findAll()).doesNotContain(reservationRepositoryTimeDto);
    }
}
