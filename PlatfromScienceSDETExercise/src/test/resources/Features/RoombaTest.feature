Feature: Robot Hoovering Test Cases
Description: Robot Hoovering API test cases


Scenario Outline: Roomba 200 Response Code
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response code is success.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessions|[7,7]|[1,1]|[2,3]|SNNEEE|


@test
Scenario Outline: Roomba Response Schema Validations
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response schema is valid "<expectedCoords>","<expectedPatches>".

Examples:
|apiURL|roomSize|coords|patches|instructions|expectedCoords|expectedPatches|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[0,0]|[[1,0],[2,2],[2,3]]|EEE|[3,0]|1|
#|http://localhost:8080/v1/cleaning-sessions|[5,5]|[0,0]|[[2,4],[2,2],[2,3]]|E|
#|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,3]|[[2.5,1.5]]|ENN|
#|http://localhost:8080/v1/cleaning-sessions|[5,5]|[0,0]|[[1,4]]|ENN|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,2]|[[1,0],[2,2],[2,3]]|NNESEESWNWW|[1,3]|1|
#|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,1]|[[2,2]]||
#|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,1]|[[3,3]]|S|


Scenario Outline: Roomba Response Time
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response time is valid.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,2]|[1,0],[2,2],[2,3]|NNESEESWNWW|


Scenario Outline: Roomba Response Header Validation
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the all headers are valid.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[1,2]|[1,0],[2,2],[2,3]|NNESEESWNWW|


Scenario Outline: Roomba 404 Error Response Code
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response code is for url not found.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessio|[5,5]|[1,2]|[1,0],[2,2],[2,3]|NNESEESWNWW|

Scenario Outline: Roomba 400 Response Code
Given Establish connection with Roomba "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response code is Bad Request.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[0,0]|[[1,0],[2,2],[2,3]]|AAAAA|
|http://localhost:8080/v1/cleaning-sessions|1|[0,0]|[[1,0],[2,2],[2,3]]|SSSSSS|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|1|[[1,0],[2,2],[2,3]]|SSSSSS|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[0,0]|[1]|SSSSSS|
|http://localhost:8080/v1/cleaning-sessions|1|[1]|[1]|MMM|


Scenario Outline: Roomba 415 Response Code
Given Establish connection with Roomba with no headers "<apiURL>","<roomSize>","<coords>","<patches>","<instructions>".
Then Validate if the response code is Unsupported Media Type.

Examples:
|apiURL|roomSize|coords|patches|instructions|
|http://localhost:8080/v1/cleaning-sessions|[5,5]|[6,6]|[[1,0],[2,2],[2,3]]|SSSSSS|
