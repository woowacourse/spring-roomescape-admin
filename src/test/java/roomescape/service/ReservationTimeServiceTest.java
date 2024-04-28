package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    ReservationTimeService reservationTimeService;

    @DisplayName("시간 저장")
    @Test
    void saveTime() {
        //given
        final List<ReservationTimeResponse> beforeSaving = reservationTimeService.findAll();
        final CreateReservationTimeRequest request = new CreateReservationTimeRequest("10:00");

        //when
        reservationTimeService.save(request); // TODO save 리턴값으로 검증하는 것이 나을지, 저장 전후의 size를 비교하는게 나을지, 아니면 다해야할지
        final List<ReservationTimeResponse> afterSaving = reservationTimeService.findAll();

        //then
        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1)
        );
    }

    @DisplayName("시간 삭제")
    @Test
    @Sql("/createTime.sql")
    void removeTime() {
        final List<ReservationTimeResponse> beforeRemoving = reservationTimeService.findAll();
        reservationTimeService.remove(1L);
        final List<ReservationTimeResponse> afterRemoving = reservationTimeService.findAll();

        assertAll(
                () -> assertThat(beforeRemoving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty()
        );
    }

    @DisplayName("전체 조회")
    @Sql("/createTimes.sql")
    @Test
    void findAll() {
        //given
        final List<ReservationTimeResponse> expected = List.of(
                new ReservationTimeResponse(1L, "12:00"),
                new ReservationTimeResponse(2L, "13:00"),
                new ReservationTimeResponse(3L, "14:00"));

        //when
        final List<ReservationTimeResponse> findAll = reservationTimeService.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }

    @DisplayName("존재하지 않은 시간 삭제시 예외")
    @Test
    void removeNonExistentReservationTime() {
        assertThatThrownBy(() -> reservationTimeService.remove(100L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
