package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomescapeTest {

    private final PlayingTime playingTime = PlayingTime.toDefaultPlayingTime();
    private String customerName = "이프";
    private LocalDateTime fixedTime = LocalDateTime.of(2026, 4, 29, 17, 0, 0, 0);

    @Test
    void 일정이_빈_방탈출에서_예약시간과_예약자가_주어지면_방탈출_예약을_할_수_있다() {
        // given
        Reservations emptySchedule = new Reservations(new ArrayList<>());
        Roomescape roomescape = new Roomescape(emptySchedule);

        // when
        Reservation reservation = roomescape.reserve(customerName, fixedTime);

        // then
        ReservationTime expectedTime = playingTime.calculateReservationTime(fixedTime);
        assertThat(reservation)
                .extracting(Reservation::getCustomerName, Reservation::getReservationTime)
                .containsExactly(customerName, expectedTime);

    }

    @ParameterizedTest
    @ValueSource(longs = {0, 59})
    void 한시간짜리_방탈출_예약시_예약시간과_겹치는_일정이_있다면_예외가_발생한다(long minuteDuration) {
        // given: 17:00 ~ 18:00에 예약된 방탈출 생성
        ReservationTime alreadyTime = playingTime.calculateReservationTime(fixedTime);
        Reservation alreadyReservation = new Reservation(customerName, alreadyTime);

        Reservations conflictSchedule = new Reservations(new ArrayList<>(List.of(alreadyReservation)));
        Roomescape roomescape = new Roomescape(conflictSchedule);

        // when & then
        LocalDateTime newStartTime = fixedTime.plusMinutes(minuteDuration);
        assertThatThrownBy(() -> roomescape.reserve(customerName, newStartTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 시간에 이미 예약된 정보가 있습니다.");
    }

    @Test
    void 한시간짜리_방탈출_예약시_기존_예약_일정에서_한시간이_지났다면_예약할_수_있다() {
        // given: 17:00 ~ 18:00에 예약된 방탈출 생성
        ReservationTime alreadyTime = playingTime.calculateReservationTime(fixedTime);
        Reservation alreadyReservation = new Reservation(customerName, alreadyTime);

        Reservations schedule = new Reservations(new ArrayList<>(List.of(alreadyReservation)));
        Roomescape roomescape = new Roomescape(schedule);

        // when: 18시 정각에 예약 시도
        LocalDateTime nextStartTime = fixedTime.plusMinutes(60);
        Reservation reservation = roomescape.reserve(customerName, nextStartTime);

        // then
        ReservationTime expectedTime = playingTime.calculateReservationTime(nextStartTime);
        assertThat(reservation.getReservationTime()).isEqualTo(expectedTime);
    }

    @Test
    void 일정이_빈_방탈출에서_예약을_취소하면_예외가_발생한다() {
        // given
        Roomescape roomescape = new Roomescape(new Reservations(new ArrayList<>()));

        // when & then
        assertThatThrownBy(() -> roomescape.cancelReservation(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 예약 정보입니다");
    }

    @Test
    void 방탈출에_예약된_정보가있다면_취소_할_수_있다() {
        // given: 17:00 ~ 18:00에 예약된 방탈출 생성
        ReservationTime alreadyTime = playingTime.calculateReservationTime(fixedTime);
        Reservation alreadyReservation = new Reservation(1L, customerName, alreadyTime);
        Roomescape roomescape = new Roomescape(new Reservations(List.of(alreadyReservation)));

        // when: 취소
        roomescape.cancelReservation(1L);

        // then: 동일 시간 재예약 가능
        Reservation reservation = roomescape.reserve(customerName, fixedTime);
        assertThat(reservation.getReservationTime()).isEqualTo(alreadyTime);
    }
}