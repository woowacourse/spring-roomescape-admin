// let으로 선언한 변수는 값을 재할당할 수 있지만, const는 값을 재할당할 수 없다.
let isEditing = false;
const RESERVATION_API_ENDPOINT = '/reservations';

// HTML 문서가 모두 로드된 후 실행되는 이벤트 리스너 등록
// document는 js에서 제공하는 전역객체로, 현재 로드된 HTML문서를 나타낸다.
// Document Object Model이 load되면 아래 이벤트리스터가 동작한다.
document.addEventListener('DOMContentLoaded', () => {
    // 'add-button' ID를 가진 버튼에 클릭 이벤트를 추가
    document.getElementById('add-button').addEventListener('click', addInputRow);

    // 서버에서 예약 데이터를 가져와 화면에 표시
    requestRead(RESERVATION_API_ENDPOINT)
        .then(render) // 데이터를 성공적으로 가져왔을 때 render 함수 실행
        .catch(error => console.error('Error fetching reservations:', error)); // 실패 시 에러 출력
});

// "예약 추가" 버튼 클릭 시 새 입력 행을 추가하는 함수
function addInputRow() {
    if (isEditing) return; // 이미 편집 중이면 아무 작업도 하지 않음

    const tableBody = document.getElementById('table-body'); // 테이블 본문 요소 가져오기
    const row = tableBody.insertRow(); // 새 행 추가
    isEditing = true; // 현재 편집 중 상태로 설정

    // 입력 필드를 생성
    const nameInput = createInput('text'); // 텍스트 입력 필드 (예약자 이름)
    const dateInput = createInput('date'); // 날짜 입력 필드
    const timeInput = createInput('time'); // 시간 입력 필드

    // 각 입력 필드를 셀에 추가
    const cellFieldsToCreate = ['', nameInput, dateInput, timeInput]; // 데이터와 입력 필드 목록

    cellFieldsToCreate.forEach((field, index) => {
        const cell = row.insertCell(index); // 각 셀을 생성
        if (typeof field === 'string') { // 빈 문자열일 경우
            cell.textContent = field;
        } else { // 입력 필드일 경우
            cell.appendChild(field);
        }
    });

    // 마지막 셀에 확인 및 취소 버튼 추가
    const actionCell = row.insertCell(row.cells.length);
    actionCell.appendChild(createActionButton('확인', 'btn-custom', saveRow)); // "확인" 버튼 추가
    actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => { // "취소" 버튼 추가
        row.remove(); // 행 삭제
        isEditing = false; // 편집 상태 해제
    }));
}

// HTML 입력 필드를 생성하는 함수
function createInput(type) {
    const input = document.createElement('input'); // 입력 필드 생성
    input.type = type; // 입력 필드 유형 설정 (text, date, time 등)
    input.className = 'form-control'; // 스타일 적용을 위한 클래스 추가
    return input; // 생성된 입력 필드 반환
}

// 새 예약을 저장하는 함수
function saveRow(event) {
    event.stopPropagation(); // 이벤트 전파를 막음

    const row = event.target.parentNode.parentNode; // 버튼이 포함된 행을 가져옴
    const nameInput = row.querySelector('input[type="text"]'); // 이름 입력 필드 가져오기
    const dateInput = row.querySelector('input[type="date"]'); // 날짜 입력 필드 가져오기
    const timeInput = row.querySelector('input[type="time"]'); // 시간 입력 필드 가져오기

    // 입력 데이터를 객체로 변환
    const reservation = {
        name: nameInput.value,
        date: dateInput.value,
        time: timeInput.value
    };

    // 서버에 새 예약 데이터 저장 요청
    requestCreate(reservation)
        .then(() => {
            location.reload(); // 페이지를 새로고침하여 데이터를 다시 로드
        })
        .catch(error => console.error('Error:', error));

    isEditing = false; // 편집 상태 해제
}

// 새 예약 데이터를 서버에 저장 요청
function requestCreate(reservation) {
    const requestOptions = {
        method: 'POST', // HTTP POST 요청
        headers: {'Content-Type': 'application/json'}, // JSON 데이터 전송 헤더
        body: JSON.stringify(reservation) // 예약 데이터를 JSON으로 변환
    };

    return fetch(RESERVATION_API_ENDPOINT, requestOptions) // fetch를 이용해 서버 요청
        .then(response => {
            if (response.status === 200) return response.json(); // 성공 시 JSON 응답 반환
            throw new Error('Create failed'); // 실패 시 에러 발생
        });
}

// 예약 데이터를 서버에서 가져오는 요청
function requestRead(endpoint) {
    return fetch(endpoint) // HTTP GET 요청
        .then(response => {
            if (response.status === 200) return response.json(); // 성공 시 JSON 응답 반환
            throw new Error('Read failed'); // 실패 시 에러 발생
        });
}

// 데이터를 받아 테이블에 표시하는 함수
function render(data) {
    const tableBody = document.getElementById('table-body'); // 테이블의 본문 요소를 가져옴
    tableBody.innerHTML = ''; // 기존 데이터를 초기화 (Java의 `List.clear()`와 유사)

    // 예약 데이터를 하나씩 테이블에 추가
    data.forEach(item => {
        const row = tableBody.insertRow(); // 새 행(row)을 추가

        // 예약 번호, 이름, 날짜, 시간을 각각의 셀에 추가
        row.insertCell(0).textContent = item.id;
        row.insertCell(1).textContent = item.name;
        row.insertCell(2).textContent = item.date;
        row.insertCell(3).textContent = item.time;

        // 삭제 버튼을 생성하여 마지막 셀에 추가
        const actionCell = row.insertCell(row.cells.length); // 마지막 셀 추가
        actionCell.appendChild(createActionButton('삭제', 'btn-danger', deleteRow)); // 삭제 버튼 생성 및 추가
    });
}

// 버튼을 생성하고 이벤트 리스너를 연결하는 함수
function createActionButton(label, className, eventListener) {
    const button = document.createElement('button'); // HTML 버튼 생성
    button.textContent = label; // 버튼의 텍스트 설정
    button.classList.add('btn', className, 'mr-2'); // 버튼에 CSS 클래스 추가
    button.addEventListener('click', eventListener); // 클릭 이벤트 리스너 연결
    return button; // 생성된 버튼 반환
}

// 예약 데이터를 삭제하는 함수
function deleteRow(event) {
    const row = event.target.closest('tr'); // 클릭된 버튼이 속한 행을 가져옴
    const reservationId = row.cells[0].textContent; // 첫 번째 셀(예약 ID)에서 ID 값 가져오기

    // 서버에 삭제 요청
    requestDelete(reservationId)
        .then(() => row.remove()) // 성공 시 행 삭제
        .catch(error => console.error('Error:', error)); // 실패 시 에러 출력
}

// 특정 ID의 예약 데이터를 삭제 요청
function requestDelete(id) {
    const requestOptions = {
        method: 'DELETE', // HTTP DELETE 요청
    };

    return fetch(`${RESERVATION_API_ENDPOINT}/${id}`, requestOptions) // ID를 포함한 URL로 요청
        .then(response => {
            if (response.status !== 200) throw new Error('Delete failed'); // 실패 시 에러 발생
        });
}
