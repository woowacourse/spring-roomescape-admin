package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.ReservationFixture.INITIAL_RESERVATION_SIZE;
import static roomescape.fixture.ReservationFixture.RESERVATION_1_ID;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Member;
import roomescape.exception.BadRequestException;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void create() {
        // given
        ReservationRequest request = ReservationFixture.newRequest();
        Member member = MemberFixture.member1();

        // when
        ReservationResponse result = reservationService.create(request, member);

        // then
        assertThat(result).isEqualTo(ReservationFixture.newResponse());
    }

    @Test
    @DisplayName("중복된 예약을 요청하면 예외가 발생한다.")
    void createFail() {
        // given
        ReservationRequest request = ReservationFixture.newRequest();
        Member member = MemberFixture.member1();
        reservationService.create(request, member);

        // when & then
        assertThatThrownBy(() -> reservationService.create(request, member))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("같은 시간에 이미 예약이 존재합니다.");
    }

    @Test
    @DisplayName("현재 시간보다 이전 시간의 예약을 시도하면 예외가 발생한다.")
    void createLate() {
        // given
        ReservationRequest request = ReservationFixture.badRequest();
        Member member = MemberFixture.member1();

        // when & then
        assertThatThrownBy(() -> reservationService.create(request, member))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("현재 시각보다 이전의 예약은 불가능합니다.");
    }

    @Test
    void findAll() {
        // when
        List<ReservationResponse> result = reservationService.findAll();

        // then
        assertThat(result.size()).isEqualTo(INITIAL_RESERVATION_SIZE);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void remove() {
        // given
        assertThat(reservationRepository.findById(RESERVATION_1_ID)).isNotEmpty();

        // when
        reservationService.remove(RESERVATION_1_ID);

        // then
        assertThat(reservationRepository.findById(RESERVATION_1_ID)).isEmpty();
    }
}
