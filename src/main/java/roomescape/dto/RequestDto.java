package roomescape.dto;

public class RequestDto {

    public static class ReservationCreateDto {
        private String name;
        private String date;
        private String time;

        public ReservationCreateDto() {
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }
}
