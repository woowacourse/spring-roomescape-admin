package roomescape;

public class Reservation {
    private final String name;
    private final String date;
    private final String time;

    public Reservation(String name, String date, String time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.name = name;
        this.date = date;
        this.time = time;
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 필수 값입니다.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름 형식이 올바르지 않습니다.");
        }
    }

    private static void validateDate(String date) {
        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 필수 값입니다.");
        }

        if (date.isBlank() || !date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 올바르지 않습니다.");
        }
    }

    private static void validateTime(String time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 필수 값입니다.");
        }

        if (time.isBlank() || !time.matches("^\\d{2}:\\d{2}$")) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다.");
        }
    }
}
