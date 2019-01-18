- RDManagementApp is basically a REST based Web Application developed using Spring Boot and Hibernate framework.

- A single dedicated platform is required for the proper management of all the RD Batch Students activities.
- This application currently provides APIs for registration ,login and profile of all the user roles in RD whihch are Students, Mentees, Mentors, Trainers, Admins, Superadmin.
- The application is secured using Spring Security Customized with JWT Token Authentication to achieve the Restfulness.
- Using the spring security, role based access is implemented in the application.
- It provides main endpoints of (POST, /register), (POST, /login) and (GET, /profile/{user-name}).

- POST (/register) API, takes JSON of email, firstName, lastName and password details in the request body.
- POST (/login) API, takes JSON of email and password details in the request body and return a JWT Token in the header after successful authentication.
- GET (/profile/{user-name}) API, provides profile data as a response in JSON form based upon the user role, information access in the profile is restricted.