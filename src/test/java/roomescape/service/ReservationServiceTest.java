package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationQueryingRepository;
import roomescape.repository.ReservationUpdatingRepository;
import roomescape.repository.TimeQueryingRepository;
import roomescape.repository.TimeUpdatingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationServiceTest {

    private final ReservationTime stubTime = new ReservationTime(1L, LocalTime.of(10, 0));

    private ReservationService buildService(
            List<Reservation> allReservations,
            int deleteResult,
            Long insertResult,
            ReservationTime timeForFindById
    ) {
        ReservationTimeService timeService = new ReservationTimeService(
                new TimeQueryingRepository(null) {
                    @Override
                    public Optional<ReservationTime> findById(Long id) {
                        return Optional.ofNullable(timeForFindById);
                    }
                    @Override
                    public List<ReservationTime> findAll() { return List.of(); }
                    @Override
                    public boolean existsByStartAt(LocalTime startAt) { return false; }
                },
                new TimeUpdatingRepository(null) {
                    @Override
                    public Long insert(ReservationTime reservationTime) { return null; }
                }
        );
        return new ReservationService(
                new ReservationQueryingRepository(null) {
                    @Override
                    public List<Reservation> findAll() { return allReservations; }
                },
                new ReservationUpdatingRepository(null) {
                    @Override
                    public int delete(Long id) { return deleteResult; }
                    @Override
                    public Long insert(Reservation reservation) { return insertResult; }
                },
                timeService
        );
    }

    @Test
    @DisplayName("전체 예약 조회 시 저장된 목록을 반환한다")
    void findAll() {
        Reservation reservation = new Reservation(1L, "홍길동", LocalDate.now().plusDays(1), stubTime);
        ReservationService service = buildService(List.of(reservation), 0, null, stubTime);

        List<Reservation> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("예약을 정상적으로 저장하면 id가 포함된 객체를 반환한다")
    void save_success() {
        ReservationService service = buildService(List.of(), 0, 1L, stubTime);

        String futureDate = LocalDate.now().plusDays(1).toString();
        Reservation saved = service.save(new ReservationRequest("홍길동", futureDate, 1L));

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("홍길동");
        assertThat(saved.getTime()).isEqualTo(stubTime);
    }

    @Test
    @DisplayName("존재하지 않는 time_id로 예약 저장 시 예외가 발생한다")
    void save_timeNotFound() {
        ReservationService service = buildService(List.of(), 0, null, null);

        String futureDate = LocalDate.now().plusDays(1).toString();
        assertThatThrownBy(() -> service.save(new ReservationRequest("홍길동", futureDate, 99L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    @Test
    @DisplayName("예약을 정상적으로 삭제하면 예외가 발생하지 않는다")
    void delete_success() {
        ReservationService service = buildService(List.of(), 1, null, stubTime);

        service.delete(1L);
    }

    @Test
    @DisplayName("존재하지 않는 예약 삭제 시 예외가 발생한다")
    void delete_notFound() {
        ReservationService service = buildService(List.of(), 0, null, stubTime);

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 예약입니다.");
    }
}