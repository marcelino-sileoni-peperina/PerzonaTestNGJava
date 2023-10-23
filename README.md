# JAVA Scripts to Test Perzona App

### 20/10/23
In order to run these scripts :
- An instance of server Appium v2.x with UiAutomator2 driver installed.
- An Android Emulator according to the version defined in src/main/java/vaporstream.Perzona/data.properties file
- IOS scripts are not finished. The .app or .ipa file couldn't be generated.
- JWT con be regenerated with method getToken defined on class externalServices
- JWT must be saved at src/main/java/vaporstream.Perzona/resources/extServ.properties file. 
- The scripts run with version 0.67 of Perzona App Build on Test Platform
- The apk (Android) and .app/.ipa (iOS) file must be located at src/test/java/resources/Perzona_latest.apk and src/test/java/resources/Perzona_latest.app
- username clean up on database it should be the next step. The backend services are created and proved on postman. 
- The services used on backend are the following (phone numbers and usernames are examples):
  1. Ask for NEW OTP Generation <br>
`  curl --location 'https://api-personas-t.vaporstream.com/auth/login-tickets/phone' \
  --header 'Content-Type: application/json' \
  --data '{
  "phoneNumber": "+11234567890"
  }'`
  2. Query for OTP Value <br>
`  curl --location 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/otp' \
  --header 'accept: application/json' \
  --header 'content-type: application/json' \
  --header 'authorization: {{JWT}}' \
  --data '{
  "phoneNumber": "+11234567890"
  }'`
  3. Query to verify SignUp of Phone Number: if response not null (not valid to verify completeness of signup)<br>
`  curl --location 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/phonenumber/+11234567890' \
  --header 'Authorization: {{JWT}}' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json'`
  4. Delete a Phone Number registered<br>
`  curl --location --request DELETE 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/phonenumber/+12603923391' \
  --header 'Authorization: {{JWT}}' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json'`
  5. Query data registered on DB per Phone Number<br>
`  curl --location 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/get-profiles/+12603923391' \
  --header 'accept: application/json' \
  --header 'content-type: application/json' \
  --header 'authorization: {{JWT}}'`
  6. Delete profiles of a phone number (also delete them on neo4j)<br>
  Not used. Must be used in the evolution of this test.<br>
`  curl --location --request DELETE 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/delete-profiles/+12603923391' \
  --header 'accept: application/json' \
  --header 'content-type: application/json' \
  --header 'authorization: {{JWT}}'`
  7. Delete one specific profile from a phone number ((also delete it on neo4j))<br>
  Not used. Must be used in the evolution of this test.<br>
`  curl --location --request DELETE 'https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/delete-profile/+12603923391?username=jtravolta' \
  --header 'accept: application/json' \
  --header 'content-type: application/json' \
  --header 'authorization: {{JWT}}'`
  8. JWT value (first must be Ask for NEW OTP and Query OTP Value)<br>
`  curl --location 'https://api-personas-t.vaporstream.com/auth/login-refresh/phone' \
  --header 'Content-Type: application/json' \
  --data '{
  "code": 985002,
  "phoneNumber": "+11234567890"
  }'`

## External Resources:
### SignUp / SignIn Screens and Components Perzona v0.67
https://docs.google.com/presentation/d/1Zqdcggrvd2Snizs_B06enT8N20uV5bprRJcrkxWgaLE/edit?usp=sharing
### Perzona Automation (15/10/23)
https://docs.google.com/presentation/d/18rSpVIYGJTklYMzFHlygCpX-JkXv7FM1rXKQxuE1efo/edit?usp=sharing
