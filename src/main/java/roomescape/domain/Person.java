package roomescape.domain;

public class Person {
    private final int id;
    private final String nane;

    public Person(int id, String nane) {
        this.id = id;
        this.nane = nane;
    }

    public int getId() {
        return id;
    }

    public String getNane() {
        return nane;
    }
}
