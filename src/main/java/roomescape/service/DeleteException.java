package roomescape.service;

public class DeleteException extends RuntimeException {

    public DeleteException() {
        super("id에 매칭되는 예약이 없어 삭제할 수 없습니다.");
    }
}
