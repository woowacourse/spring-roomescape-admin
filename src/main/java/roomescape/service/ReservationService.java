package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.ReservationRegisterDto;
import roomescape.service.dto.ReservationResponseDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao,
                              ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Long saveReservation(final ReservationRegisterDto reservationRegisterDto) {
        ReservationTime reservationTime = findReservationTime(reservationRegisterDto.timeId());
        Reservation reservation = reservationRegisterDto.toReservation(reservationTime);

        return reservationDao.save(reservation);
    }

    public ReservationResponseDto findReservationById(final Long id) {
        Reservation foundReservation = findReservationWithId(id);
        return new ReservationResponseDto(foundReservation);
    }

    public List<ReservationResponseDto> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public void deleteReservationById(final Long id) {
        findReservationWithId(id);
        reservationDao.deleteById(id);
    }

    private Reservation findReservationWithId(Long id) {
        Optional<Reservation> foundReservation = reservationDao.findById(id);

        if (foundReservation.isEmpty()) {
            throw new IllegalArgumentException("해당 id 와 일치하는 예약 내역이 존재하지 않습니다.");
        }
        return foundReservation.get();
    }

    private ReservationTime findReservationTime(final Long id) {
        Optional<ReservationTime> foundReservationTime = reservationTimeDao.findById(id);

        if (foundReservationTime.isEmpty()) {
            throw new IllegalArgumentException("해당 id 와 일치하는 예약 시각이 존재하지 않습니다.");
        }
        return foundReservationTime.get();
    }
}
