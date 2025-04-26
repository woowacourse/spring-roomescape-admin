package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

public class ReservationServiceTest {

    private final ReservationService reservationService;
    private final FakeReservationDao fakeReservationDao;

    public ReservationServiceTest(){
        fakeReservationDao = new FakeReservationDao();
        reservationService = new ReservationService(fakeReservationDao);
    }

    @BeforeEach
    void setUp() {
        fakeReservationDao.clear();
    }


    @DisplayName("reservation request를 생성하면 response 값을 반환한다.")
    @Test
    void create() {
        // given
        final ReservationRequest request = new ReservationRequest("조앤", LocalDate.of(2025, 04, 21), 1L);

        // when
        final ReservationResponse response = reservationService.createReservation(request);

        // then
        assertSoftly(s -> {
            s.assertThat(response.id()).isNotNull();
            s.assertThat(response.name()).isEqualTo(request.name());
            s.assertThat(response.date()).isEqualTo(request.date());
            s.assertThat(response.time().id()).isEqualTo(1L);
        });
    }

    @DisplayName("reservation들을 모두 조회한다.")
    @Test
    void findAll() {
        // given & when
        final List<ReservationResponse> allReservation = reservationService.findAllReservation();

        // then
        assertThat(allReservation).hasSize(0);
    }

    @DisplayName("주어진 id에 해당하는 reservation 삭제한다.")
    @Test
    void delete() {
        // given
        final Long id = 1L;

        // when
        reservationService.deleteReservationById(id);

        // then
        assertThat(fakeReservationDao.isInvokeDeleteById(id)).isTrue();
    }

}
