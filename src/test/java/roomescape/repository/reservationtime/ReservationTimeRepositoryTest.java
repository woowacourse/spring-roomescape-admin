package roomescape.repository.reservationtime;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeRepositoryTest {
//
//    private final ReservationTimeRepository timeRepository;
//
//    public ReservationTimeRepositoryTest(ReservationTimeRepository timeRepository) {
//        this.timeRepository = timeRepository;
//    }
//
//    @Test
//    @DisplayName("모든 ReservationTime을 찾는다.")
//    void findAll() {
//        timeRepository.add(new ReservationTime(null, LocalTime.of(12, 0)));
//        timeRepository.add(new ReservationTime(null, LocalTime.of(11, 0)));
//
//        List<ReservationTime> found = timeRepository.findAll();
//
//        assertThat(found).hasSize(2);
//    }
//
//    @Test
//    @DisplayName("id에 맞는 ReservationTime을 찾는다.")
//    void findBy() {
//        ReservationTime reservationTimeWithoutId = new ReservationTime(null, LocalTime.of(12, 0));
//        ReservationTime reservationTime = timeRepository.add(reservationTimeWithoutId);
//
//        ReservationTime found = timeRepository.findBy(1L);
//
//        assertThat(found).isEqualTo(reservationTime);
//    }
}
