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
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.reservation.ReservationCreateRequestDto;
import roomescape.dto.reservation.ReservationResponseDto;
import roomescape.dto.reservationtime.ReservationTimeCreateRequestDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationService reservationService;
    private Long timeId;

    @BeforeEach
    void setUp() {
        timeId = reservationTimeDao.add(ReservationTimeCreateRequestDto.from("12:02"));
        reservationDao.add(ReservationCreateRequestDto.of("daon", "2024-04-23", timeId));
        reservationDao.add(ReservationCreateRequestDto.of("ikjo", "2022-02-22", timeId));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
    }

    @Test
    @DisplayName("모든 예약 정보를 조회한다.")
    void findAll() {
        //when
        List<ReservationResponseDto> results = reservationService.findAll();
        ReservationResponseDto firstResponse = results.get(0);

        //then
        assertAll(
                () -> assertThat(results).hasSize(2),
                () -> assertThat(firstResponse.getName()).isEqualTo("daon")
        );
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void add() {
        //given
        String givenName = "wooteco";
        String givenDate = "2024-04-23";
        ReservationCreateRequestDto requestDto = ReservationCreateRequestDto.of(givenName, givenDate, timeId);

        //when
        ReservationResponseDto result = reservationService.add(requestDto);

        //then
        assertAll(
                () -> assertThat(result.getName()).isEqualTo(givenName),
                () -> assertThat(result.getDate()).isEqualTo(givenDate),
                () -> assertThat(reservationService.findAll()).hasSize(3)
        );
    }

    @Test
    @DisplayName("존재하지 않는 시간 아이디로 예약 추가시 에외가 발생한다.")
    void addNotExistTimeId() {
        //given
        Long given = -1L;
        String givenName = "wooteco";
        String givenDate = "2024-04-23";
        ReservationCreateRequestDto requestDto = ReservationCreateRequestDto.of(givenName, givenDate, given);

        //when //then
        assertThatThrownBy(() -> reservationService.add(requestDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void delete() {
        //given
        long givenId = addAndGetId();

        //when
        reservationService.delete(givenId);

        //then
        assertThat(reservationService.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("예약 삭제시 아이디가 비어있으면 예외가 발생한다.")
    void deleteNullId() {
        //given
        Long givenId = null;

        //when //then
        assertThatThrownBy(() -> reservationService.delete(givenId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 삭제시 아이디가 존재하지 않는다면 예외가 발생한다.")
    void deleteNotExistId() {
        //given
        long givenId = addAndGetId() + 1;

        //when //then
        assertThatThrownBy(() -> reservationService.delete(givenId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private long addAndGetId() {
        ReservationCreateRequestDto requestDto = ReservationCreateRequestDto.of("3", "1999-09-19", timeId);
        return reservationDao.add(requestDto);
    }
}