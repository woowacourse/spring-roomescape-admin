package roomescape.usecase.ReservationTime;

import org.springframework.stereotype.Service;

@Service
public class DeleteReservationTimeService implements DeleteReservationTimeUsecase {
    private final ReservationTimeRepository reservationTimeRepository;
    private final GetReservationTimeUseCase getReservationTimeUseCase;

    public DeleteReservationTimeService(final ReservationTimeRepository reservationTimeRepository,
                                        final GetReservationTimeService getReservationTimeUseCase) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.getReservationTimeUseCase = getReservationTimeUseCase;
    }

    @Override
    public void deleteReservationTime(long id) {
        boolean exists = reservationTimeRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("해당 시간이 존재하지 않습니다");
        }

        reservationTimeRepository.deleteReservationTime(id);
    }
}
