package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestDto {

    public static class ReservationCreateDto {
        private String name;
        private LocalDate date;
        private LocalTime time;

        public ReservationCreateDto() {
        }

        public String getName() {
            return name;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTime() {
            return time;
        }
    }
}
