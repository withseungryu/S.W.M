# S. W. M



## REST API -  URI 정리 (baseUrl = 

## https://shallwemeet.co.kr:8080

## http://shallwemeet.co.kr:8081 )

| URI                | 내용                                                         | method             |
| ------------------ | ------------------------------------------------------------ | ------------------ |
| /api/boards        | board 정보 얻기 (get), board 추가 (post)                     | GET, POST          |
| /api/boards/{id}   | board 개별 조회, 삭제, 업데이트                              | GET, DELETE, PATCH |
| /api/users         | user 정보 얻기 (get), user 추가 (post)                       | GET, POST          |
| /api/users/{id}    | user 개별 조회, 삭제, 업데이트                               | GET, DELETE, PATCH |
| //생략....         | 위와 같이 bill, matched, bookmark 가능                       |                    |
| /api/user/login    | 입력된 ID, PASSWORD(추가 예정) DB 조회                       | POST               |
| /api/users/upload  | 유저의 프사, 닉네임 업로드                                   | POST               |
| /api/filter        | 게시판 필더 적용쿼리(location, num_type,age)가능요 (나중에 Board uri랑 합칠 예정)유저의 초기 내용 업로드 | GET                |
| /api/boards/upload | 게시판 생성 ,  사진 3개에 다른 정보들 주삼                   | POST               |

