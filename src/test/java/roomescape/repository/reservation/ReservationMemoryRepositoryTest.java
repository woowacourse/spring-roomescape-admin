package roomescape.repository.reservation;

//import roomescape.repository.reservation.ReservationMemoryRepository;

class ReservationMemoryRepositoryTest {
//
//    private final ReservationMemoryRepository reservationMemoryRepository = new ReservationMemoryRepository();
//
//    @Test
//    @DisplayName("예약을 추가한다.")
//    void add() {
//        Reservation reservation = new Reservation(
//                null,
//                "kargo",
//                LocalDate.of(2024, 4, 1),
//                LocalTime.of(14, 40)
//        );
//        Reservation expected = new Reservation(
//                1L,
//                "kargo",
//                LocalDate.of(2024, 4, 1),
//                LocalTime.of(14, 40)
//        );
//
//        reservationMemoryRepository.add(reservation);
//
//        assertThat(reservationMemoryRepository.findAll()).containsExactly(expected);
//    }
//
//    @Test
//    @DisplayName("id와 매칭되는 예약을 삭제한다.")
//    void remove() {
//        //given
//        Reservation reservation1 = new Reservation(
//                null,
//                "brown",
//                LocalDate.of(2024, 4, 1),
//                LocalTime.of(14, 30)
//        );
//        Reservation reservation2 = new Reservation(
//                null,
//                "neo",
//                LocalDate.of(2023, 12, 15),
//                LocalTime.of(1, 0)
//        );
//        Reservation reservation3 = new Reservation(
//                null,
//                "solar",
//                LocalDate.of(2024, 4, 15),
//                LocalTime.of(17, 13)
//        );
//
//        reservationMemoryRepository.add(reservation1);
//        reservationMemoryRepository.add(reservation2);
//        reservationMemoryRepository.add(reservation3);
//
//        //when
//        reservationMemoryRepository.remove(2L);
//        List<Reservation> reservations = reservationMemoryRepository.findAll();
//
//        //then
//        assertAll(
//                () -> assertThat(reservations).contains(Reservation.of(1L, reservation1)),
//                () -> assertThat(reservations).doesNotContain(Reservation.of(2L, reservation2)),
//                () -> assertThat(reservations).contains(Reservation.of(3L, reservation3))
//        );
//    }
//
//    @Test
//    @DisplayName("모든 예약을 반환한다.")
//    void findAllWithId() {
//        //given
//        Reservation reservation1 = new Reservation(
//                null,
//                "brown",
//                LocalDate.of(2024, 4, 1),
//                LocalTime.of(14, 30)
//        );
//        Reservation reservation2 = new Reservation(
//                null,
//                "neo",
//                LocalDate.of(2023, 12, 15),
//                LocalTime.of(1, 0)
//        );
//        Reservation reservation3 = new Reservation(
//                null,
//                "solar",
//                LocalDate.of(2024, 4, 15),
//                LocalTime.of(17, 13)
//        );
//
//        reservationMemoryRepository.add(reservation1);
//        reservationMemoryRepository.add(reservation2);
//        reservationMemoryRepository.add(reservation3);
//
//        //when
//        List<Reservation> reservations = reservationMemoryRepository.findAll();
//
//        //then
//        assertThat(reservations).containsExactly(
//                Reservation.of(1L, reservation1),
//                Reservation.of(2L, reservation2),
//                Reservation.of(3L, reservation3)
//        );
//    }
}
