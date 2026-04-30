package roomescape.console;

import roomescape.controller.dto.ReservationRequest;

import java.lang.reflect.Field;

class ConsoleReservationRequest extends ReservationRequest {

    ConsoleReservationRequest(String name, String date, Long timeId) {
        try {
            setField("name", name);
            setField("date", date);
            setField("timeId", timeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = ReservationRequest.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(this, value);
    }
}
