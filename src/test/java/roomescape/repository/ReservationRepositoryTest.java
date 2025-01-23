package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Reservation;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ReservationSlotFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @ParameterizedTest
    @CsvSource({"1, true", "4, false"})
    @DisplayName("특정 슬롯에 예약이 이미 존재하면 true를 반환한다.")
    void existsReservedReservationTrue(long slotId, boolean expected) {
        // when
        boolean result = reservationRepository.existsReservedReservation(slotId);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, true", "1, false"})
    @DisplayName("회원이 특정 슬롯에 이미 예약 또는 예약 대기를 신청했는지 확인한다.")
    void existsByMemberIdAndSlotId(long memberId, boolean expected) {
        // when
        boolean result = reservationRepository.existsByMemberIdAndSlotId(memberId, ReservationSlotFixture.SLOT_2_ID);

        // then
        assertThat(result).isEqualTo(expected);
    }

    // TODO: "취소" 상태의 예약은 순번을 계산할 때 포함되지 않는 것도 테스트 필요
    @Test
    @DisplayName("내 예약 조회 시 대기 순번까지 함께 조회한다.")
    void findReservationWithRankByMemberId() {
        // given

        // when
        List<ReservationWithRank> result = reservationRepository.findReservationWithRankByMemberId(
                MemberFixture.MEMBER_3_ID);

        // then
        Reservation reservedReservation = ReservationFixture.reservation3();
        Reservation waitingReservation = ReservationFixture.reservation5();
        ReservationWithRank expected1 = new ReservationWithRank(reservedReservation, 0);
        ReservationWithRank expected2 = new ReservationWithRank(waitingReservation, 2);
        assertThat(result).containsExactlyInAnyOrder(expected1, expected2);
    }
}
