package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeQueryingRepository;
import roomescape.repository.ReservationTimeUpdatingRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTimeServiceTest {

    @Test
    @DisplayName("전체 예약 시간 조회 시 저장된 목록을 반환한다")
    void findAll() {
        ReservationTime stubTime = new ReservationTime(1L, LocalTime.of(10, 0));
        ReservationTimeQueryingRepository queryingRepo = new ReservationTimeQueryingRepository(null) {
            @Override
            public Optional<ReservationTime> findById(Long id) { return Optional.empty(); }
            @Override
            public List<ReservationTime> findAll() { return List.of(stubTime); }
            @Override
            public boolean existsByStartAt(LocalTime startAt) { return false; }
        };
        ReservationTimeService service = new ReservationTimeService(queryingRepo, new ReservationTimeUpdatingRepository(null) {
            @Override
            public Long insert(ReservationTime reservationTime) { return null; }
        });

        List<ReservationTime> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("존재하지 않는 시간 id 조회 시 예외가 발생한다")
    void findById_notFound() {
        ReservationTimeQueryingRepository queryingRepo = new ReservationTimeQueryingRepository(null) {
            @Override
            public Optional<ReservationTime> findById(Long id) { return Optional.empty(); }
            @Override
            public List<ReservationTime> findAll() { return List.of(); }
            @Override
            public boolean existsByStartAt(LocalTime startAt) { return false; }
        };
        ReservationTimeService service = new ReservationTimeService(queryingRepo, new ReservationTimeUpdatingRepository(null) {
            @Override
            public Long insert(ReservationTime reservationTime) { return null; }
        });

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 시간입니다.");
    }

    @Test
    @DisplayName("예약 시간을 정상적으로 저장하면 id가 포함된 객체를 반환한다")
    void save_success() {
        ReservationTimeQueryingRepository queryingRepo = new ReservationTimeQueryingRepository(null) {
            @Override
            public Optional<ReservationTime> findById(Long id) { return Optional.empty(); }
            @Override
            public List<ReservationTime> findAll() { return List.of(); }
            @Override
            public boolean existsByStartAt(LocalTime startAt) { return false; }
        };
        ReservationTimeUpdatingRepository updatingRepo = new ReservationTimeUpdatingRepository(null) {
            @Override
            public Long insert(ReservationTime reservationTime) { return 1L; }
        };
        ReservationTimeService service = new ReservationTimeService(queryingRepo, updatingRepo);

        ReservationTime saved = service.save(new ReservationTimeRequest("10:00"));

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("이미 존재하는 시간 슬롯 저장 시 예외가 발생한다")
    void save_duplicate() {
        ReservationTimeQueryingRepository queryingRepo = new ReservationTimeQueryingRepository(null) {
            @Override
            public Optional<ReservationTime> findById(Long id) { return Optional.empty(); }
            @Override
            public List<ReservationTime> findAll() { return List.of(); }
            @Override
            public boolean existsByStartAt(LocalTime startAt) { return true; }
        };
        ReservationTimeService service = new ReservationTimeService(queryingRepo, new ReservationTimeUpdatingRepository(null) {
            @Override
            public Long insert(ReservationTime reservationTime) { return null; }
        });

        assertThatThrownBy(() -> service.save(new ReservationTimeRequest("10:00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 예약 시간입니다.");
    }

    @Test
    @DisplayName("예약 시간을 정상적으로 삭제하면 예외가 발생하지 않는다")
    void delete_success() {
        ReservationTimeUpdatingRepository updatingRepo = new ReservationTimeUpdatingRepository(null) {
            @Override
            public void delete(Long id) {}
            @Override
            public Long insert(ReservationTime reservationTime) { return null; }
        };
        ReservationTimeService service = new ReservationTimeService(
                new ReservationTimeQueryingRepository(null) {
                    @Override
                    public Optional<ReservationTime> findById(Long id) { return Optional.empty(); }
                    @Override
                    public List<ReservationTime> findAll() { return List.of(); }
                    @Override
                    public boolean existsByStartAt(LocalTime startAt) { return false; }
                },
                updatingRepo
        );

        service.delete(1L);
    }
}