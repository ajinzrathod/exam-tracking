# Jai Swaminarayan !!

> java 13.0.2

**Database Name:** exam_management

1. Edit database details in database.properties
1. Edit server details in server.properties

 
### Free Ports Required:
* 8989
* 7000

> Please import Database named "exam_management.sql" available at root folder.

### Steps:
* Turn on MySQL at Port 3306
* Server Start:
1. src/server/ChatServer.java
2. src/server/FileRecieve.java

* Client Login:
1. src/client/LoginScreen.java

----
## Features

### Chat System

- Teacher
  * Teachers Can chat with each student privately.
  * Teachers Can chat with every student at once.

- Student
  * Students can chat with teacher privately.

### Timer Based Exam
- Exam will be completed once the specified time is over

### Screenshot Tracker
- Screenshot of each and every student will be taken once the exam Starts.
- Screenshots will be taken every 8 seconds and sent to the teacher

### Keylogger
- Keystrokes will be recorded and Sent to Teacher

### Camera based Exam on Client Side
- The frames of Camera will be shown to Student

----

## Upcoming Updates
- Camera of each student will be visible to Teacher.
- Audio Support
