package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

//    @Profile("local")
//    @Bean
//    public LocalDataInit localDataInit(ReservationRepository reservationRepository,
//                                       ReservationTimeRepository timeRepository) {
//        return new LocalDataInit(reservationRepository, timeRepository);
//    }
}
