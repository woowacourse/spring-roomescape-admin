package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;


    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        validateNameFormat(name);
        this.name = name;
        validateDateFormat(date);
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, ReservationTime time) {
        this.id = 0L;
        validateNameFormat(name);
        this.name = name;
        validateDateFormat(date);
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    private void validateNameFormat(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("이름 형식은 2글자 이상 10글자 이하입니다");
        }

        if(name.length()<2|| name.length()>10){
            throw new IllegalArgumentException("이름 형식은 2글자 이상 10글자 이하입니다");
        }
    }

    private void validateDateFormat(String date) {
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException | NullPointerException e){
            throw new IllegalArgumentException("날짜 형식은 yyyy-MM-dd 입니다 예) 2023-04-13");
        }
    }
}
