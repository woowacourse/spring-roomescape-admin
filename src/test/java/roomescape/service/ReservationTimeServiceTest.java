package roomescape.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class ReservationTimeServiceTest {

    FakeReservationTimeRepository fakeReservationTimeRepository = new FakeReservationTimeRepository();
    ReservationTimeService reservationTimeService = new ReservationTimeService(fakeReservationTimeRepository);

    @Test
    @DisplayName("시간을 저장한다.")
    void createReservationTime(){
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.of(10, 0));
        // when
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.createReservationTime(reservationTimeRequest);

        // then
        Assertions.assertThat(reservationTimeResponse.startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("전체 시간 목록을 가져온다")
    void getAllReservationTime(){

        // given
        // when
        List<ReservationTimeResponse> reservationTimeResponses  = reservationTimeService.getAllReservationTime();
        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservationTimeResponses).hasSize(1);
            softAssertions.assertThat(reservationTimeResponses.getFirst().startAt()).isEqualTo(LocalTime.of(22,0));
        });
    }


    static class FakeReservationTimeRepository implements ReservationTimeRepository {

        List<ReservationTime> reservationTimes = new ArrayList<>();

        public FakeReservationTimeRepository() {
            reservationTimes.add(new ReservationTime(LocalTime.of(22, 0)));
        }

        @Override
        public ReservationTime save(ReservationTime reservationTime) {
            reservationTimes.add(reservationTime);
            return reservationTime;
        }

        @Override
        public List<ReservationTime> findAll() {
            return reservationTimes;
        }
    }
}
