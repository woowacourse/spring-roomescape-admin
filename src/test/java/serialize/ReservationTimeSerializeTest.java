package serialize;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeSerializeTest {

    @DisplayName("빌드옵션 관계없이 기본생성자가 있는 객체는 역직렬화 할 수 있다.")
    @Test
    void basic() {
        JsonPath jsonPath = new JsonPath("""
        {
            "id": "1",
            "startAt": "10:10"
        }""");

        TestBasic testBasic = jsonPath.getObject(".", TestBasic.class);

        Assertions.assertThat(testBasic.getId()).isEqualTo(1);
        Assertions.assertThat(testBasic.getStartAt()).isEqualTo("10:10");
    }

    @DisplayName("Intellij로 빌드할 경우 기본생성자가 없는 객체는 역직렬화 할 수 없다.")
    @Test
    void nonBasic() {
        JsonPath jsonPath = new JsonPath("""
        {
            "id": "1",
            "startAt": "10:10"
        }""");

        Assertions.assertThatThrownBy(() -> jsonPath.getObject(".", TestNoneBasic.class))
                        .isInstanceOf(RuntimeException.class);
    }

    static class TestBasic {
        private Long id;
        private String startAt;

        protected TestBasic() {
        }

        public TestBasic(Long id, String startAt) {
            this.id = id;
            this.startAt = startAt;
        }

        public Long getId() {
            return id;
        }

        public String getStartAt() {
            return startAt;
        }
    }

    static class TestNoneBasic {
        private Long id;
        private String startAt;

        public TestNoneBasic(Long id, String startAt) {
            this.id = id;
            this.startAt = startAt;
        }

        public Long getId() {
            return id;
        }

        public String getStartAt() {
            return startAt;
        }
    }
}
