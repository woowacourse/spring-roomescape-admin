package roomescape.business.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Nested
    class 생성_테스트 {

        @Test
        void 예약자명으로_생성할_수_있다() {
            final String name = "dompoo";

            final Customer customer = new Customer(name);

            assertThat(customer.name()).isEqualTo("dompoo");
        }

        @Test
        void 예약자명이_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Customer(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 예약자명이_비어_있으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Customer(""))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 예약자명이_10자가_넘어가면_예외가_발생한다() {
            assertThatThrownBy(() -> new Customer("12345678901"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
