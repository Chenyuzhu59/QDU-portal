#QDU_tools_backend
此项目是小程序的后端

# 根路径
根路径为 https://api.zhpjy.cc

# 请求验证码

### Request
- **Method**: GET
- **URL**: /jw/captcha

### Response
- **code**: base64转化后的验证码图片
- **cookie**: cookie
- **status**: 200(正常), 404(教务验证码访问失败), 500(其他错误)
``` json
{
    "code": "data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAZAFADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1Px1ef2b4G167F19lkjsJvKm8zYVkKEJtbs24gDHOSMc15J8L7G0/4TaIWdrPo8+l6FFbX9lcxiOe5uHfe8m1ssU5U7sqR+7H3eK9T+IfgmHx34dXSpL17N47hbiKZUDgMAVwVyMjDN0I5we2DUsPBqx67qet61dJqd/fwC0KiFkt4rfAzGsTO/DEFjknJPAGTkA4DxnrGt65rGh2ljeXmm6ZfakILZbJiLi9hX/W3IdchYlBBUc7gS54Aw/xb4jtrbxg0t3d31x4fs7B0nGlXLDyLvzOkxicMpIUIoc43HjHzEbeo/C+yufFtlq0KaPBp1rx/Zq6QmyUEYYuwYbm/ukjC4HB5ys/gu+htdfsbDV7aKy1meeeVZ7JpZI2mUK4VhKoxxkZXjvmgCnpNrrz+DdHe+1xkljhaa6mtIluZZ0wTGFbDAkKVyQrFiODzkp4R1O81XTr57ybzvs9/PbwyOoSVo1Py+YgA2vyeCqnGDjnJ2pdGvbPTtMs9H1P7MljGsJW4t1mWZAm0b8FSCMA5VgOuQe1TQtB/sK2vFe4+0T3l5LeTuE2Lvc8hVySAAB1J780AeZ67o86a5rGta/4SlvrNpwRcQXwXy7dMKGEancTtAY5IH+6Aa7rSbXT7XRbWPSk22DJ5kAyx+Vvmz83POc81njwfqxU2V74qvLvSGZg9s8KiWRCSdjTZ3Edj0yMjgHjoPJjgiSKKNY40UKiIMBQOAAOwoApSCqkgq/IKpyCgD2yQVUlWr0tU5KAKMgqnIKvS1UloAoyCqcgq9JVOSgClIKpyrV6WqclAFKQVTkFXpKpy0Af/9k=",
    "cookie": "JSESSIONID=166D405014F75963E3C786B3554D2478.TA7",
    "status":200
}
```

# 登录

### Request
- **Method**: POST
- **URL**: /jw/login
- **Body**: 
```json
{
	"username": "201540704429",
	"password": "37012",
	"captcha": "5758",
	"cookie": "JSESSIONID=36095B88B17B1275D0320B34EF1EE1A3.TA6"
}
```

### Response
- **msg**: 登录后需要提示的消息（登录失败后的错误原因，登录成功后提示某个功能暂时不能用）
- **isLoginSuccess**: 登录是否成功
##### 失败
```json
{
    "msg": "账号或密码错误或验证码错误",
    "isLoginSuccess": false
}
```
##### 成功
成功会返回一个新的cookie，此cookie用于后续的查成绩查课表等功能
```json
{
    "msg": "欢迎登录 张鹏(201540704430)",
    "isLoginSuccess": true,
    "cookie": "JSESSIONID=6F4E580D5B6BAF2A7BEB74F952AD8EE2.TA6"
}
```

# 查成绩

### Request
- **Method**: POST
- **URL**: /jw/score
- **Body**: 
```json
{
	"cookie": "JSESSIONID=F822A439158152ECADFFDD58BA517D99.TA8"
}
```

### Response
- **year**: 学年
- **term**:学期
- **course**: 课程名
- **score**: 成绩
- **assess**: 考查形式
- **credit**: 学分
```json
[
    {
        "year": "2018",
        "term": "春",
        "course": "Linux操作系统基础（英文）",
        "score": "90",
        "assess": "考试",
        "credit": "2"
    },
    {
        "year": "2017",
        "term": "春",
        "course": "使用HTML5和Jquery进行自适应网页设计（英文）",
        "score": "90",
        "assess": "考试",
        "credit": "2"
    },
    {
        "year": "2016",
        "term": "春",
        "course": "形势与政策Ⅲ",
        "score": "75",
        "assess": "考查",
        "credit": "0.5"
    },
    {
        "year": "2015",
        "term": "春",
        "course": "大学生职业发展与就业指导",
        "score": "90",
        "assess": "考查",
        "credit": "1"
    },
    {
        "year": "2017",
        "term": "夏",
        "course": "工程设计与管理",
        "score": "87",
        "assess": "考查",
        "credit": "4"
    }
]
```

# 查课程表

### Request
- **Method**: POST
- **URL**: /jw/course
- **Body**: 
```json
{
  "cookie": "JSESSIONID=3A131F056908488E741BE5AF92BDE6B5.TA1",
  "year":"2018",
  "term":"1"
}
```
- **term**: 1春季学期，2秋季学期，3夏季学期
### Response
- **credit**: 学分
- **assess**: 考察形式
- **weeks**: 上课周
- **weekday**:星期几，周一到周日对应数字1至7
- **start**: 课程从第几节课开始
- **end**: 课程在第几节课结束
- **place**: 上课地点
```json
[
    {
        "name": "计算机网络基础",
        "teacher": "刘虹",
        "credit": "4.5",
        "assess": "考试",
        "timeAndPlace": [
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 4,
                "start": 1,
                "end": 2,
                "place": "博文楼219"
            },
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 2,
                "start": 1,
                "end": 2,
                "place": "博文楼219"
            }
        ]
    },
    {
        "name": "软件工程导论Ⅱ",
        "teacher": "万志波",
        "credit": "3.5",
        "assess": "考试",
        "timeAndPlace": [
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 3,
                "start": 3,
                "end": 4,
                "place": "东12教507"
            },
            {
                "weeks": [
                    1,
                    3,
                    5,
                    7,
                    9,
                    11,
                    13,
                    15
                ],
                "weekday": 1,
                "start": 3,
                "end": 4,
                "place": "博学楼102"
            }
        ]
    },
    {
        "name": "NIIT学期项目IV",
        "teacher": "Gaurav verma",
        "credit": "1",
        "assess": "考查",
        "timeAndPlace": [
            {
                "weeks": [
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 3,
                "start": 1,
                "end": 2,
                "place": "博逸楼501"
            }
        ]
    },
    {
        "name": "MySQL数据库（英文）",
        "teacher": "Gaurav verma",
        "credit": "1.5",
        "assess": "考试",
        "timeAndPlace": [
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8
                ],
                "weekday": 3,
                "start": 5,
                "end": 6,
                "place": "博逸楼514"
            },
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8
                ],
                "weekday": 2,
                "start": 5,
                "end": 6,
                "place": "博逸楼514"
            }
        ]
    },
    {
        "name": "用PHP开发Web应用（英文）",
        "teacher": "Gaurav verma",
        "credit": "3",
        "assess": "考试",
        "timeAndPlace": [
            {
                "weeks": [
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 2,
                "start": 3,
                "end": 4,
                "place": "静思楼3-201"
            },
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8
                ],
                "weekday": 1,
                "start": 7,
                "end": 8,
                "place": "静思楼3-201"
            },
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8
                ],
                "weekday": 2,
                "start": 3,
                "end": 4,
                "place": "静思楼3-201"
            },
            {
                "weeks": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8
                ],
                "weekday": 5,
                "start": 1,
                "end": 2,
                "place": "博逸楼510"
            }
        ]
    },
    {
        "name": "用PHP开发安全和分布式应用（英文）",
        "teacher": "Gaurav verma",
        "credit": "1.5",
        "assess": "考试",
        "timeAndPlace": [
            {
                "weeks": [
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 1,
                "start": 5,
                "end": 6,
                "place": "静思楼3-403"
            },
            {
                "weeks": [
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16
                ],
                "weekday": 5,
                "start": 7,
                "end": 8,
                "place": "静思楼3-403"
            }
        ]
    }
]
```

# 获取当前周

### Request
- **Method**: GET
- **URL**: /jw/week

### Response

```json
{
    "number": 7
}
```

# 检查cookie是否有效

### Request
- **Method**: POST
- **URL**: /jw/cookie
- **Body**:
```json
{
  "cookie": "JSESSIONID=0A631DF7ED717752827B653F409D28E2.TA4"
}
``` 

### Response
```json
{
    "valid": true
}
```

# 查空教室

### Request
- **Method**: POST
- **URL**: /jw/classroom
- **Body**: 
    - **area**:校区名称(fushan，jinjialing，songshan）
    - **start**:从第几节课开始
    - **end**:到第几节课结束
    - 按上午4节课，下午4节课，晚上3节课来算
```json
{
  "cookie": "JSESSIONID=3A131F056908488E741BE5AF92BDE6B5.TA1",
  "area":"fushan",
  "start":1,
  "end":2
}
```

### Response
```json
{
    "results": ["博学203","博学404","东12教404","博远501","博知302"]
}
```

# 搜索书籍

### Request
- **Method**: POST
- **URL**: /library/search
- **Body**: 
```json
//搜书名
{
    "type": "name",
    "text": "三体",
    "page":1
}
//搜作者
{
    "type": "author",
    "text": "鲁迅",
    "page":2
}
```

### Response
- **count**:页码总数
```json
{
    "results": [
        {
            "name": "花边文学",
            "author": "鲁迅",
            "press": "人民文学出版社",
            "id":123456
        },
        {
            "name": "呐喊",
            "author": "鲁迅",
            "press": "人民文学出版社",
            "id":123467
        },
        {
            "name": "华盖集",
            "author": "鲁迅",
            "press": "人民文学出版社",
            "id":123478
        }
    ],
    "count": 1
}
```

# 查询书籍详细信息

### Request
- **Method**: POST
- **URL**: /library/detail
- **Body**: 
```json
{
    "id":123456
}
```

### Response
- **index**:索引号
- **available**:ture表示入藏，false表示借出
```json
{
    "name": "呐喊",
    "author": "鲁迅",
    "press": "人民文学出版社",
    "pageCount": "302页",
    "price": "CNY23.00",
    "sort": "文学",
    "index": "I210.6",
    "ISBNISSN": "978-7-5366-9293-0",
    "results": [
        {
            "library": "青大图书馆",
            "place": "北院书库",
            "code": "201165531",
            "available": true
        },
        {
            "library": "青大图书馆",
            "place": "东校2(原高职）",
            "code": "00021977",
            "available": true
        },
        {
            "library": "青大图书馆",
            "place": "北院书库",
            "code": "201165532",
            "available": false
        },
        {
            "library": "青大图书馆",
            "place": "北院书库",
            "code": "201165533",
            "available": true
        },
        {
            "library": "青大图书馆",
            "place": "北院书库",
            "code": "201165534",
            "available": true
        }
    ]
}
```