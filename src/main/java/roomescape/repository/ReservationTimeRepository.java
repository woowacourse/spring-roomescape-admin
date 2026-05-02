package roomescape.repository;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.ReservationTime;

@Validated
public interface ReservationTimeRepository {

    ReservationTime save(@NotNull(message = "저장할 예약 시간 정보는 null일 수 없습니다.") ReservationTime reservationTime);

    List<ReservationTime> findAll();

    Optional<ReservationTime> findById(long id);

    void deleteById(long id);

    boolean existsByStartAt(@NotNull(message = "예약 시간 정보는 필수 값입니다.") LocalTime time);
}