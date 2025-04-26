package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void initialize() {
        reservationService = new ReservationService(new FakeReservationRepository(), new FakeReservationTimeRepository());
    }

    @Test
    void 예약시간이_성공적으로_생성되어_반환된다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);

        // When
        ReservationTime reservationTimeEntity = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));

        // Then
        assertThat(reservationTimeEntity.getId()).isEqualTo(1L);
        assertThat(reservationTimeEntity.getStartAt()).isEqualTo(startAt);
    }

    @Test
    void 중복된_예약시간은_생성할_수_없다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));

        // When & Then
        assertThatThrownBy(() -> reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 예약 시간입니다.");
    }

    @Test
    void 저장된_예약시간_객체들을_모두_반환한다() {
        // Given
        LocalTime startAt1 = LocalTime.of(10, 0);
        LocalTime startAt2 = LocalTime.of(11, 0);

        // When
        ReservationTime reservationTimeEntity1 = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt1));
        ReservationTime reservationTimeEntity2 = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt2));

        // Then
        assertThat(reservationService.getAllReservationTime()).containsExactlyInAnyOrder(reservationTimeEntity1, reservationTimeEntity2);
    }

    @Test
    void 주어진_id를_가진_예약시간을_삭제한다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));
        Long deleteId = 1L;

        // When
        reservationService.deleteReservationTimeById(deleteId);

        // Then
        assertThat(reservationService.getAllReservationTime()).isEqualTo(Collections.emptyList());
    }

    @Test
    void 존재하지_않는_예약시간_id로_삭제할_경우_예외가_발생한다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> reservationService.deleteReservationTimeById(5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약시간 id입니다.");
    }

    @Test
    void 예약이_성공적으로_생성되어_반환된다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTimeEntity = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        Long timeId = reservationTimeEntity.getId();

        // When
        Reservation reservationEntity = reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, timeId));

        // Then
        assertThat(reservationEntity.getId()).isEqualTo(1L);
        assertThat(reservationEntity.getName()).isEqualTo(name);
        assertThat(reservationEntity.getDate()).isEqualTo(date);
        assertThat(reservationEntity.getTime()).isEqualTo(reservationTimeEntity);
    }

    @Test
    void 중복된_날짜와_시간에는_예약을_생성할_수_없다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTimeEntity = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        Long timeId = reservationTimeEntity.getId();
        reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, timeId));

        // When & Then
        assertThatThrownBy(() -> reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, timeId)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 시간은 이미 예약되었습니다.");
    }

    @Test
    void 저장된_예약_객체들을_모두_반환한다() {
        // Given
        LocalTime startAt1 = LocalTime.of(10, 0);
        ReservationTime reservationTimeEntity1 = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt1));
        LocalTime startAt2 = LocalTime.of(11, 0);
        ReservationTime reservationTimeEntity2 = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt2));
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);

        // When
        Reservation reservationEntity1 = reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, reservationTimeEntity1.getId()));
        Reservation reservationEntity2 = reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, reservationTimeEntity2.getId()));

        // Then
        assertThat(reservationService.getAllReservation()).containsExactlyInAnyOrder(reservationEntity1, reservationEntity2);
    }

    @Test
    void 주어진_id를_가진_예약을_삭제한다() {
        // Given
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTimeEntity = reservationService.createNewReservationTime(new ReservationTimeCreateRequest(startAt));
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        Long timeId = reservationTimeEntity.getId();
        reservationService.createReservationAtNewDateTime(new ReservationCreateRequest(name, date, timeId));
        Long deleteId = 1L;

        // When
        reservationService.deleteReservationById(deleteId);

        // Then
        assertThat(reservationService.getAllReservation()).isEqualTo(Collections.emptyList());
    }

    @Test
    void 존재하지_않는_예약_id로_삭제할_경우_예외가_발생한다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> reservationService.deleteReservationById(5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }
}
