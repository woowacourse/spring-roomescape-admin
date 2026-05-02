package roomescape.repository;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.Reservation;

@Validated
public interface ReservationRepository {

    Reservation save(@NotNull(message = "저장할 예약 정보는 null일 수 없습니다.") Reservation reservation);

    List<Reservation> findAll();

    void delete(long id);

    boolean existByDateAndTimeId(@NotNull(message = "예약 날짜 정보는 null일 수 없습니다.") LocalDate date, long timeId);
}
