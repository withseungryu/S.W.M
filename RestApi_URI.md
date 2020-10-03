# S. W. M



## REST API -  URI 정리 (baseUrl = http://localhost:8081)

| URI              | 내용                                     | method             |
| ---------------- | ---------------------------------------- | ------------------ |
| /api/boards      | board 정보 얻기 (get), board 추가 (post) | GET, POST          |
| /api/boards/{id} | board 개별 조회, 삭제, 업데이트          | GET, DELETE, PATCH |
| /api/users       | user 정보 얻기 (get), user 추가 (post)   | GET, POST          |
| /api/users/{id}  | user 개별 조회, 삭제, 업데이트           | GET, DELETE, PATCH |
| //생략....       | 위와 같이 bill, matched, bookmark 가능   |                    |
| /user/login      | 입력된 ID, PASSWORD(추가 예정) DB 조회   | POST               |
|                  |                                          |                    |
|                  |                                          |                    |
|                  |                                          |                    |

