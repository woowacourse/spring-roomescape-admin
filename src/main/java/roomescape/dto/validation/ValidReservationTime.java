package roomescape.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationValidator.class)
public @interface ValidReservationTime {
    String message() default "유효하지 않은 시간입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
