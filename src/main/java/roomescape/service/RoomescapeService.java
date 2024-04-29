package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRepositoryDto;
import roomescape.dto.ReservationRepositoryTimeDto;
import roomescape.dto.request.ReservationsRequest;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.ReservationsResponse;
import roomescape.dto.response.TimesResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class RoomescapeService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public RoomescapeService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationsResponse addReservation(ReservationsRequest reservationsRequest) {
        ReservationRepositoryDto reservationRepositoryDto = new ReservationRepositoryDto(null, reservationsRequest.name(), reservationsRequest.date(), reservationsRequest.timeId());
        ReservationRepositoryDto addedReservationRepositoryDto = reservationRepository.add(reservationRepositoryDto);
        ReservationRepositoryTimeDto addedTimeDto = reservationTimeRepository.findById(reservationsRequest.timeId());
        Reservation addedReservation = convertToReservationWithDtos(addedReservationRepositoryDto, addedTimeDto);
        return new ReservationsResponse(addedReservation);
    }

    public List<ReservationsResponse> finaAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationRepositoryDto -> {
                    ReservationRepositoryTimeDto addedTimeDto = reservationTimeRepository.findById(reservationRepositoryDto.timeId());
                    return convertToReservationWithDtos(reservationRepositoryDto, addedTimeDto);
                })
                .map(ReservationsResponse::new)
                .toList();
    }

    public boolean removeReservation(Long id) {
        return reservationRepository.remove(id);
    }
    
    public TimesResponse addTime(TimesRequest timesRequest) {
        ReservationRepositoryTimeDto reservationRepositoryTimeDto = new ReservationRepositoryTimeDto(null, timesRequest.startAt());
        ReservationRepositoryTimeDto addedReservationRepositoryTimeDto = reservationTimeRepository.add(reservationRepositoryTimeDto);
        ReservationTime addedTime = addedReservationRepositoryTimeDto.toDomain();
        return new TimesResponse(addedTime);
    }
    
    public List<TimesResponse> findAllTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationRepositoryTimeDto::toDomain)
                .map(TimesResponse::new)
                .toList();
    }

    public boolean removeTime(Long id) {
        return reservationTimeRepository.remove(id);
    }
    
    private Reservation convertToReservationWithDtos(ReservationRepositoryDto repositoryDto, ReservationRepositoryTimeDto reservationRepositoryTimeDto) {
        return new Reservation(repositoryDto.id(), repositoryDto.name(), repositoryDto.date(), reservationRepositoryTimeDto.toDomain());
    }
}
