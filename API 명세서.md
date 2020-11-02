# API 명세서



```http
GET /api/boards
```



#### Parameter

| Name     | Type   | Description       | Required |
| :------- | ------ | ----------------- | -------- |
| page     | int    | 페이지 번호       | X        |
| size     | int    | 한 페이지 당 개수 | X        |
| location | String | 위치 정보         | X        |
| num_type | String | 미팅 N:N 타입     | X        |
| age      | String | 나이              | X        |



#### Response

![image-20201022192429708](C:\Users\alstm\AppData\Roaming\Typora\typora-user-images\image-20201022192429708.png)

content ==> 배열로



---





```http
POST /api/boards
```



#### Parameter

| Name     | Type      | Description       | Required |
| :------- | --------- | ----------------- | -------- |
| img1     | multipart | 게시판 대표 이미지 1 | O    |
| img2     | multipart | 게시판 대표 이미지 2 | O       |
| img3     | multipart | 게시판 대표 이미지 3 | O       |
| title    | String    | 게시판 제목 | O       |
| location | String    | 미팅 위치    | O       |
| num_type | String | 미팅 타입 | O |
| gender   | String | 게시판 생성자 성별 | O |
| tag1     | String | 태그 1 | O |
| tag2     | String | 태그 2 | O |
| tag3     | String | 태그 3 | O |
| age     | String | 게시판 생성자 나이 | O |
| user     | String | 유저 인덱스 | O |



#### Response

| Name     | Type      | Description       | Required |
| :------- | --------- | ----------------- | -------- |
| code | int | code (200 = 성공, 400 = 실패) | O    |
| msg  | String | msg (Success = 성공, fail = 실패) | O      |



---



```http
POST api/users/login
```



#### Parameter
| Name     | Type      | Description       | Required |
| :------- | --------- | ----------------- | -------- |
| email | String | 유저 이메일 | O    |



#### Response

| Name     | Type    | Description                        | Required |
| :------- | ------- | ---------------------------------- | -------- |
| code     | int     | code (200 = 성공, 400 = 실패)      | O        |
| msg      | String  | msg (Success = 성공, Fail = 실패)  | O        |
| _checked | boolean | true : 가입된 O , false : 가입된 X | O        |

#### Sample

![image-20201023093105646](C:\Users\alstm\AppData\Roaming\Typora\typora-user-images\image-20201023093105646.png)

---

```http
POST api/users
```



#### Parameter

| Name     | Type      | Description      | Required |
| :------- | --------- | ---------------- | -------- |
| email    | String    | 유저 이메일      | O        |
| img      | Multipart | 유저 프로필 사진 | O        |
| nickName | String    | 유저 닉네임      | O        |
| age      | String    | 유저 나이        | O        |



#### Response

| Name | Type   | Description                       | Required |
| :--- | ------ | --------------------------------- | -------- |
| code | int    | code (200 = 성공, 400 = 실패)     | O        |
| msg  | String | msg (Success = 성공, Fail = 실패) | O        |
