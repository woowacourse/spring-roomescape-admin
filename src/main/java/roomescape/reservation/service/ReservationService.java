package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.dao.ReservationTimeDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.dto.request.ReservationRequestDto;
import roomescape.reservation.dto.response.ReservationResponseDto;
import roomescape.reservation.dto.response.ReservationTimeResponseDto;
import roomescape.reservation.dto.response.ReservationsResponseDto;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationsResponseDto findAllReservation() {
        List<ReservationResponseDto> response = reservationDao.findAll().stream()
                .map(r -> new ReservationResponseDto(
                        r.getId(),
                        r.getName(),
                        r.getDate(),
                        new ReservationTimeResponseDto(r.getTime().getId(), r.getTime().getStartAt())))
                .toList();

        return new ReservationsResponseDto(response);
    }

    public ReservationResponseDto addReservation(final ReservationRequestDto request) {
        ReservationTime time = reservationTimeDao.findById(request.timeId());
        Reservation reservation = reservationDao.insert(new Reservation(request.name(), request.date(), time));

        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponseDto(time.getId(), time.getStartAt())
        );
    }

    public void removeReservationById(final Long id) {
        int updateCount = reservationDao.deleteById(id);
        if (updateCount == 0) {
            throw new IllegalArgumentException(String.format("ID %d - Reservation 정보가 존재하지 않습니다.", id));
        }
    }
}
