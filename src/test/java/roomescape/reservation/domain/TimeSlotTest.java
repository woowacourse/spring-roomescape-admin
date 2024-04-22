package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeSlotTest {
    @DisplayName("동일한 id는 같은 예약 시간이다.")
    @Test
    void equals() {
        //given
        long id1 = 1;
        LocalTime localTime1 = LocalTime.MIDNIGHT;
        LocalTime localTime2 = LocalTime.MIDNIGHT;

        //when
        TimeSlot timeSlot1 = new TimeSlot(id1, localTime1);
        TimeSlot timeSlot2 = new TimeSlot(id1, localTime2);

        //then
        assertThat(timeSlot1).isEqualTo(timeSlot2);
    }
}
