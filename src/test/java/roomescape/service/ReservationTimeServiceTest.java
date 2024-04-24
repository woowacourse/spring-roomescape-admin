package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.reservationtime.ReservationTimeCreateRequestDto;
import roomescape.dto.reservationtime.ReservationTimeResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationTimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        reservationTimeDao.add(ReservationTimeCreateRequestDto.from("12:02"));
        reservationTimeDao.add(ReservationTimeCreateRequestDto.from("12:42"));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
    }

    @Test
    @DisplayName("모든 예약 시간 정보를 조회한다.")
    void findAll() {
        //when
        List<ReservationTimeResponseDto> results = reservationTimeService.findAll();
        ReservationTimeResponseDto firstResponse = results.get(0);

        //then
        assertAll(
                () -> assertThat(results).hasSize(2),
                () -> assertThat(firstResponse.getStartAt()).isEqualTo("12:02")
        );
    }

    @Test
    @DisplayName("예약 시간을 추가한다.")
    void add() {
        //given
        String givenStartAt = "10:52";
        ReservationTimeCreateRequestDto requestDto = ReservationTimeCreateRequestDto.from(givenStartAt);

        //when
        ReservationTimeResponseDto result = reservationTimeService.add(requestDto);

        //then
        assertAll(
                () -> assertThat(result.getStartAt()).isEqualTo(givenStartAt),
                () -> assertThat(reservationTimeService.findAll()).hasSize(3)
        );
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        //given
        long givenId = addAndGetId();

        //when
        reservationTimeService.delete(givenId);

        //then
        assertThat(reservationTimeService.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간 삭제시 아이디가 비어있으면 예외가 발생한다.")
    void deleteNullId() {
        //given
        Long givenId = null;

        //when //then
        assertThatThrownBy(() -> reservationTimeService.delete(givenId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간 삭제시 아이디가 존재하지 않는다면 예외가 발생한다.")
    void deleteNotExistId() {
        //given
        long givenId = addAndGetId() + 1;

        //when //then
        assertThatThrownBy(() -> reservationTimeService.delete(givenId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private long addAndGetId() {
        ReservationTimeCreateRequestDto requestDto = ReservationTimeCreateRequestDto.from("01:00");
        return reservationTimeDao.add(requestDto);
    }
}
