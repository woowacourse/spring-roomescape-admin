package roomescape.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationJoinDto;
import roomescape.service.dto.ReservationCreateCommand;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final Clock clock;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao, Clock clock) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.clock = clock;
    }

    public List<Reservation> findAllReservations() {
        List<ReservationJoinDto> dtos = reservationDao.findAll();
        return dtos.stream()
                .map(this::toDomain)
                .toList();
    }

    @Transactional
    public Reservation createReservation(ReservationCreateCommand command) {
        ReservationTime time = reservationTimeDao.findById(command.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));

        LocalDate currentDate = LocalDate.now(clock);

        Reservation reservation = Reservation.createNew(
                command.name(),
                command.date(),
                time,
                currentDate
        );

        Long generatedId = reservationDao.save(reservation);
        return Reservation.from(generatedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Transactional
    public void deleteReservation(Long id) {
        int affectedRows = reservationDao.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 예약입니다.");
        }
    }

    private Reservation toDomain(ReservationJoinDto dto) {
        ReservationTime time = ReservationTime.from(dto.timeId(), dto.startAt());
        return Reservation.from(
                dto.reservationId(),
                dto.name(),
                dto.date(),
                time
        );
    }
}
