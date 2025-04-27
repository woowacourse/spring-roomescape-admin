package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityTest {

    @Test
    @DisplayName("모든 엔티티 객체는 기본적으로 id 컬럼명으로 \"id\"를 가진다")
    void idColumnName() {
        // given
        Reservation reservation = new Reservation(null, "moko", LocalDate.now(), null);

        // when
        var idFieldName = reservation.idColumnName();

        // then
        assertThat(idFieldName).isEqualTo("id");
        assertThat(Entity.DEFAULT_ID_COLUMN_NAME).isEqualTo("id");
    }
}
