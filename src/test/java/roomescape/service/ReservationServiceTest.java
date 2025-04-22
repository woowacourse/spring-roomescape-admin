package roomescape.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

class ReservationServiceTest {
/*
    private final ReservationRepository fakeReservationRepository = new FakeReservationRepository();
    private final ReservationService reservationService = new ReservationService(fakeReservationRepository);


    @DisplayName("존재하지 않는 아이디를 삭제시 예외 발생")
    void deleteException() {
        // given
        long invalidId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.delete(invalidId)).
                isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("날짜와 시간이 모두 중복되면 예외가 발생한다.")
    void whenDuplicateDateAndTimeThrowException(){
        // given
        ReservationRequest existedRequest = new ReservationRequest("lemon", LocalDate.of(2025, 4, 18), LocalTime.of(16, 53));
        ReservationRequest newRequest = new ReservationRequest("lemon", LocalDate.of(2025, 4, 18), LocalTime.of(16, 53));
        // when
        reservationService.createReservation(existedRequest);
        // then
        Assertions.assertThatThrownBy(() -> reservationService.createReservation(newRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static class FakeReservationRepository implements ReservationRepository {

        List<Reservation> reservations = new ArrayList<>();

        public FakeReservationRepository() {
            reservations.add(new Reservation("Lemon",LocalDate.of(2025,4,22),LocalTime.of(13,22)));
            reservations.add(new Reservation("DDingHwa",LocalDate.of(2025,4,22),LocalTime.of(16,15)));
        }

        @Override
        public Reservation findById(long id) {
            return null;
        }

        @Override
        public List<Reservation> findAll() {
            return reservations;
        }

        @Override
        public Reservation save(Reservation reservation) {
            return null;
        }

        @Override
        public void deleteById(long id) {

        }

        @Override
        public boolean selectByDateAndTime(LocalDate date, LocalTime time) {
            return false;
        }
    }*/
}