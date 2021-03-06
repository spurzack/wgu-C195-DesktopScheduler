# Desktop Scheduler

## Project Completed 9/2020
> The submission includes an application that correctly logs in the user and provides functionality to create, update and delete customer and appointment records. The application includes a scheduling interface application. The application includes the required exception controls and the appointment times automatically adjust to time zones. The application correctly displays a reminder for upcoming appointments.

## Scenario
You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified.

The organization outlined specific business requirements that must be included as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

## Requirements

1. Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
2. Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
3. Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.
4. Provide the ability to view the calendar by month and by week.
5. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.
6. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least  two different mechanisms of exception control.
   - scheduling an appointment outside business hours
   - scheduling overlapping appointments
   - entering nonexistent or invalid customer data
   - entering an incorrect username and password
7. Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
8. Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
9. Provide the ability to generate each  of the following reports:
   - number of appointment types by month
   - the schedule for each consultant
   - one additional report of your choice
10. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
11. Demonstrate professional communication in the content and presentation of your submission.
