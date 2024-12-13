package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Profile("local")
    @Bean
    public LocalDataInit localDataInit(ReservationRepository reservationRepository,
                                       ReservationTimeRepository timeRepository) {
        return new LocalDataInit(reservationRepository, timeRepository);
    }
}
