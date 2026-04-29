    package roomescape.service;

    import java.util.List;
    import org.springframework.stereotype.Service;
    import roomescape.domain.Reservation;
    import roomescape.domain.ReservationTime;
    import roomescape.repository.ReservationRepository;
    import roomescape.repository.ReservationTimeRepository;
    import roomescape.service.command.ReservationCreateCommand;

    @Service
    public class ReservationService {

        private final ReservationRepository reservationRepository;
        private final ReservationTimeRepository timeRepository;

        public ReservationService(
                ReservationRepository reservationRepository,
                ReservationTimeRepository timeRepository
        ) {
            this.reservationRepository = reservationRepository;
            this.timeRepository = timeRepository;
        }

        public Reservation create(
                ReservationCreateCommand createCommand
        ) {
            ReservationTime time = timeRepository.findById(createCommand.timeId());
            Reservation reservation = Reservation.create(
                    createCommand.name(),
                    createCommand.date(),
                    time
            );

            return reservationRepository.create(reservation);
        }

        public List<Reservation> findAll() {
            return reservationRepository.findAll();
        }

        public void delete(long id) {
            reservationRepository.delete(id);
        }
    }
