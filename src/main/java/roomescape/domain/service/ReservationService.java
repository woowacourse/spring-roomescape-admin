package roomescape.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.dto.ReservationTimeResponse;
import roomescape.domain.repository.ReservationRepository;
import roomescape.domain.entity.Reservation;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        List<Reservation> reservations = reservationRepository.getAll();
        for (Reservation reservation : reservations) {
            if(reservation.getDate().equals(reservationRequest.date()) && Objects.equals(reservation.getTime().getId(), reservationRequest.timeId())){
                throw new IllegalArgumentException("예약이 마감된 일시입니다.");
            }
        }

        Reservation reservation = Reservation.create(
                null,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTimeRepository.getById(reservationRequest.timeId())
        );

        ReservationTime reservationTime = reservation.getTime();
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );

        return new ReservationResponse(
                reservationRepository.save(reservation),
                reservation.getName(),
                reservation.getDate(),
                reservationTimeResponse
        );
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll()
                .stream()
                .map(reservation -> {

                    ReservationTime reservationTime = reservation.getTime();
                    ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                            reservationTime.getId(),
                            reservationTime.getStartAt()
                    );

                    return new ReservationResponse(
                            reservation.getId(),
                            reservation.getName(),
                            reservation.getDate(),
                            reservationTimeResponse
                    );
                })
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
