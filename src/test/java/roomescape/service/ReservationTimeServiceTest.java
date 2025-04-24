package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import roomescape.TestDao;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.repository.ReservationRepository;
import roomescape.repository.repository.ReservationTimeRepository;

class ReservationTimeServiceTest {

    private final ReservationRepository reservationRepository = new ReservationRepository(new TestDao());
    private final ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(new TestDao());
    private final ReservationTimeService service = new ReservationTimeService(reservationTimeRepository);

    @Test
    @DisplayName("모든 데이터를 조회한다")
    void getAll() {
        // given
        reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));

        // when
        List<ReservationTimeResponse> all = service.getAll();

        // then
        assertThat(all).size().isEqualTo(2);
    }

    @Test
    @DisplayName("데이터를 저장한다")
    void create() {
        // given
        ReservationTimeRequest request = new ReservationTimeRequest(null, LocalTime.now());

        // when
        service.create(request);

        // then
        assertThat(reservationTimeRepository.getAll()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("데이터를 제거한다")
    void remove() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));

        // when
        service.remove(time.id());

        // then
        assertThat(reservationTimeRepository.getAll()).size().isEqualTo(0);
    }

    // 실제 DB를 호출하지 않고 TestDao를 사용하는 현재 구조 상 테스트가 힘들어보인다.
/*    @Test
    @DisplayName("제거를 시도하는 데이터와 연관(FK)된 데이터가 존재할 경우 예외를 반환한다")
    void removeException() {
        // given
        ReservationTime time = reservationTimeRepository.save(new ReservationTime(null, LocalTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(null, "moko", LocalDate.now(), time));

        // when
        ThrowingCallable throwingCallable = () -> service.remove(time.id());

        // then
        assertThatThrownBy(throwingCallable)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 데이터 무결성을 위반했습니다.");
    }*/
}
