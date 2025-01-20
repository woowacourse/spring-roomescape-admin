package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.ReservationSlot;
import roomescape.fixture.ReservationSlotFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationSlotRepositoryTest {

    @Autowired
    private ReservationSlotRepository reservationSlotRepository;

    @Test
    @DisplayName("같은 날짜, 시간, 테마에 대해 중복슬롯을 생성하려 시도하면 예외가 발생한다.")
    void save() {
        // given
        ReservationSlot slot = ReservationSlotFixture.newSlotWithoutId();
        reservationSlotRepository.save(slot);
        ReservationSlot duplicatedSlot = ReservationSlotFixture.newSlotWithoutId();

        // when & then
        assertThatThrownBy(() -> reservationSlotRepository.save(duplicatedSlot))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("특정 날짜, 시간, 테마 슬롯을 찾아온다.")
    void findByDateAndTimeIdAndThemeId() {
        // given
        ReservationSlot slot = ReservationSlotFixture.slot1();

        // when
        ReservationSlot foundSlot = reservationSlotRepository.findByDateAndTimeIdAndThemeId(
                        slot.getDate(), slot.getTime().getId(), slot.getTheme().getId())
                .get();

        // then
        assertThat(foundSlot).isEqualTo(slot);
    }
}
