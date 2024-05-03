package roomescape;

import java.time.LocalTime;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@SpringBootApplication
public class RoomescapeApplication {
    private final ReservationTimeRepository reservationTimeRepository;

    public RoomescapeApplication(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Bean
    public ApplicationRunner initData() {
        return args -> {
            System.out.println("init data");
            reservationTimeRepository.insert(new ReservationTime(LocalTime.of(9, 0)));
        };
    }
}
