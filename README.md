# OnlineLibrary
[![Main CI push](https://github.com/McGill-ECSE321-Fall2021/project-group-05/actions/workflows/main-push-backend.yml/badge.svg?branch=main)](https://github.com/McGill-ECSE321-Fall2021/project-group-05/actions/workflows/main-push-backend.yml)

## Overview
This online library system serves a library in a small town. It assists librarians in day-to-day operations like managing the library catalog, checking items in and out, and registering new users. It also provides access to the library catalog and reservation system for online users. The backend is deployed [here](https://onlinelibrary-backend-05a.herokuapp.com/test) and the web frontend is deployed [here](https://onlinelibrary-frontend-05a.herokuapp.com). Deployment instructions are available [here](https://github.com/McGill-ECSE321-Fall2021/project-group-05/wiki/Deployments).

Further documentation may be accessed via the [wiki](https://github.com/McGill-ECSE321-Fall2021/project-group-05/wiki).

## Deliverable 1
<table>
  <tbody>
    <tr>
      <th>Team Member</th>
      <th>Responsibilities</th>
      <th>Hours (incl. meetings)</th>
    </tr>
    <tr>
      <td>Gabriel Lacroix</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Design domain model</li>
          <li>Review requirements and domain model</li>
          <li>Document the rationale for key decisions for the domain model in the wiki</li>
          <li>Create use-case diagrams for all 15 most important requirements</li>
          <li>Develop and test the database ORM layer for classes LibraryItem, ReservableItem, Book, Movie, Album, Newspaper, Archive, and Loan</li>
          <li>Review other teammates' code and PRs</li>
          <li>Help teammates set up their development environment</li>
          <li>Document code conventions for the project</li>
          <li>Write a detailed use-case specification for the online account registration use-case</li>
        </ul>
      </td>
      <td>20 hours</td>
    </tr>
    <tr>
      <td>Guillaume Delmas-Frenette</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Design domain model</li>
          <li>Review requirements and domain model</li>
          <li>Creation of class Loan, LibraryItem, ReservableItem </li> 
          <li>Creation of class Book, Movie, Album, Newspaper, Archive </li>
          <li>Write a detailed use-case specification for reserving a library item</li>
        </ul>
      </td>
      <td> 13 hours </td>
    </tr>
    <tr>
      <td>Kirollos Roufail</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Design domain model</li>
          <li>Review requirements and domain model</li>
          <li>Debug and modify the "info" classes</li>
          <li>Write the testers for the "info" classes</li>
          <li>Write Use case success scenario #2</li>
          <li>Adjust the code according to the reviews of my team mates</li>
        </ul>
      </td>
      <td>23 hours</td>
    </tr>
    <tr>
      <td>Louis Hildebrand</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Write final draft of requirements</li>
          <li>Review requirements and domain model</li>
          <li>Set up Spring project</li>
          <li>Write detailed use case specification 5 (returning items)</li>
          <li>Create models, repositories, and tests for Member, OnlineAccount, Librarian, and LibrarianShift</li>
        </ul>
      </td>
      <td>34.5 hours</td>
    </tr>
    <tr>
      <td>Mircea Gosman</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Write final draft of requirements</li>
          <li>Review requirements and domain model</li>
          <li>Creation of LibraryOpeningHours, Holiday, RoomBooking and Room class files, repositories and their corresponding persistence unit tests</li>	
          <li>Addition of issue-linking CI tool for better organization of dependent issues</li>
          <li>Review Pull Requests</li>
          <li>Write a detailed use-case specification for the opening hours setup use-case</li>
        </ul>
      </td>
      <td>14 hours</td>
    </tr>
    <tr>
      <td>Sebastian Ionascu</td>
      <td>
        <ul>
          <li>Write rough draft of requirements</li>
          <li>Write final draft of requirements</li>
          <li>Review requirements and domain model</li>
          <li>Creation of class BookInfo, MovieInfo, AlbumInfo, NewsPaperInfo, ArchiveInfo, ReservableItemInfo, LibraryItemInfo</li>
          <li>Creation of use-case specification page in wiki and assignation of each use-case specifications to teammates</li>
          <li>Help teammates test the code, make their success spectrum and make their use-case specification</li>
          <li>Write a detailed use-case specification for the checkout items use-case</li>
          <li>Review all the use-case specifications</li>
        </ul>
      </td>
      <td>16 hours</td>
    </tr>
  </tbody>
</table>

## Deliverable 2
<table>
  <tbody>
    <tr>
      <th>Team Member</th>
      <th>Responsibilities</th>
      <th>Hours (incl. meetings)</th>
    </tr>
    <tr>
      <td>Gabriel Lacroix</td>
      <td>
        <li>Add test coverage verification rule to CI</li>
        <li>Refactor the CI process</li>
        <li>Create a simple bash script integrated into the CI pipeline to run integration tests (later replaced by Postman)</li>
        <li>Review pull requests</li>
        <li>Make necessary changes to model, DTOs, service and controller classes, write unit tests for service methods, write integration tests for endpoints using cURL and Postman, and document those endpoints in the wiki for the following use-cases:
          <ul>
            <li>Collect registration fees and confirm that an address is within city bounds (activating a member's account)</li>
            <li>Create and delete rooms</li>
            <li>Create and delete room bookings</li>
            <li>Query all rooms</li>
            <li>Query all room bookings</li>
            <li>Checkout and return items</li>
            <li>Get all copies of a library item info</li>
            <li>Get the item info associated with a library item</li>
            <li>Get the details about a library item</li>
            <li>Get the loan associated with a library item (if there is one)</li>
          </ul>
        </li>
      </td>
      <td>40 hours</td>
    </tr>
    <tr>
      <td>Guillaume Delmas-Frenette</td>
      <td>
        <ul>
          <li> Write all the itemInfos DTOs </li>
          <li> Write all the items DTOs </li>
          <li> Write all the creating itemInfos service and controller methods </li>
          <li> Write all the creating items service and controller methods </li>
          <li> Write all the delete items service and controller methods </li>
          <li> Write all the update itemInfos service and controller methods </li>
          <li> Write the documentation for the RESTful services aforementioned
          <li> Write integration and unit tests for all the controller and service methods aforementioned</li>
          <li> Review pull request </li>
        </ul>
      </td>
      <td> 32 hours </td>
    </tr>
    <tr>
      <td>Kirollos Roufail</td>
      <td>
      <ul> 
        <li> Write the service and controller for browse all LibraryItemInfos </li>
        <li> Write the service and controller for update Librarian </li>
        <li> Write the service and controller for update Member </li>
        <li> Write unit tests for the service methods that I wrote </li>
        <li> Write integration tests for the controllers that I wrote </li>
        <li> Write convert to dto methods in the model </li>
        </ul>
        </td>
      <td> 25 hours </td>
    </tr>
    <tr>
      <td>Louis Hildebrand</td>
      <td>
        <ul>
          <li>Create controller and service layers for creating, getting, and deleting members (with or without online accounts) and librarians</li>
          <li>Write unit tests for member and librarian service layers</li>
          <li>Write integration tests for member and librarian controllers</li>
          <li>Write the test plan</li>
        </ul>
      </td>
      <td>41.5 hours</td>
    </tr>
    <tr>
      <td>Mircea Gosman</td>
      <td>
        <ul>
          <li> Write Library OpeningHours DTO, Controller, Service, Service unit tests & Controller integration tests.</li>
          <li> Write Librarian Shift DTO, Controller, Service, Service unit tests & Controller integration tests.</li>
          <li> Write Holiday DTO, Controller, Service, Service unit tests & Controller integration tests.</li>
          <li> Adjusted Persistence layer & its tests for Library Opening Hours, Librarian Shift and Holiday ressources.</li>
          <li> Documented endpoint usage for Library Opening Hours, Librarian Shift and Holiday ressources on the wiki.</li>
          <li> Review Pull Requests.</li>
        </ul>
      </td>
      <td> 37 hours </td>
    </tr>
    <tr>
      <td>Sebastian Ionascu</td>
      <td>
        <ul>
          <li> Creation of Reservation class and its corresponding methods.</li>
          <li> Creation of persistence unit tests for Reservation class.</li>
          <li> Creation of Reservation DTO class and its corresponding methods.</li>
          <li> Write create Reservation services and controller methods.</li>
          <li> Write get Reservation services and controller methods in three different ways (by Reservation ID, by Member ID and by Reservable Item Info ID).</li>
          <li> Write delete Reservation services and controller methods.</li>
          <li> Write units tests for all the Reservation services.</li>
          <li> Write the documentation for the RESTful services of Reservation.</li>
          <li> Review Pull Requests.</li>
        </ul>
      </td>
      <td> 35 hours </td>
    </tr>
  </tbody>
</table>

## Deliverable 3
<table>
  <tbody>
    <tr>
      <th>Team Member</th>
      <th>Responsibilities</th>
      <th>Hours (incl. meetings)</th>
    </tr>
    <tr>
      <td>Gabriel Lacroix</td>
      <td>
        <ul>
          <li>Set up the frontend project and CI</li>
          <li>Implement "Browse rooms" and individual room pages</li>
          <li>Write a guide to start implementation and set coding conventions</li>
          <li>Implement logout</li>
          <li>Implement 404 Not Found page with redirection</li>
          <li>Add page to browse all available library items</li>
          <li>Implement pages to view individual item details</li>
          <li>Implement global navigation header for all pages</li>
          <li>Add global styling, make everything prettier, add images</li>
          <li>Modify the newspaper and archive models and services</li>
          <li>Add a reserve button for members</li>
          <li>Add a search bar to search all library items</li>
          <li>Add a form to allow a librarian to checkout an item for a member</li>
          <li>Add a form to allow a librarian to return an item</li>
          <li>Add a table listing all copies of an item and their status (on-premise only, available or checked out)</li>
          <li>Add a button to add a new item copy to the library</li>
          <li>Add a form to delete an item copy</li>
          <li>Add a form to reserve an item for a member as a librarian</li>
          <li>Participate in meetings</li>
          <li>Review pull requests</li>
          <li>Discuss project and help with problem solving</li>
        </ul>
      </td>
      <td>45 hours</td>
    </tr>
    <tr>
      <td>Guillaume Delmas-Frenette</td>
      <td>
        <ul>
          <li> Created the member home page and added all of it's components</li>
          <li> Added a few service in order to retrieve all the data necessary for the member home page </li>
          <li> System architecture </li>
          <li> Particate in meetings </li>
          <li> Code review </li>
        </ul>
      </td>
      <td> 20 hours </td>
    </tr>
    <tr>
      <td>Kirollos Roufail</td>
      <td> 
        <ul>
          <li> Page for update librarian login info with validation form, submission messages and buttons</li>
          <li> Page for update member personal info with data validation and error handling</li>
          <li> Prepare presentation slides and practice for presentation</li>
          <li>Participate in meetings</li>
        </ul>
      </td>
      <td> 25 hours</td>
    </tr>
    <tr>
      <td>Louis Hildebrand</td>
      <td>
        <ul>
          <li>Create components:</li>
          <ul>
            <li>Login</li>
            <li>Signup</li>
            <li>LibrarianManageMembers</li>
            <li>LibrarianManageLibrarians</li>
          </ul>
          <li>Create login endpoint in backend</li>
          <li>Write navigation guards to authorizing members, librarians, or the head librarian</li>
        </ul>
      </td>
      <td>38 hours</td>
    </tr>
    <tr>
      <td>Mircea Gosman</td>
      <td>
        <ul>
            <li>Reviewed Pull Requests</li>
            <li>Added Vuetify & Vue-Debounce libaries, solving dependency issues along the way.</li>
            <li>Resolved some date libraries related bugs on the backend. (FYI: Spring controllers need @DateParam to accept specific date formats, and they also do not work with Java.SQL.date/time. They require Java.time.localdate/localtime instead. It would be a neat addition to the tutorials.)</li>
            <li>Added Scheduler component & connected it to the backend. It can be accessed via the <i>Schedule</i> and <i>Room Booking</i> tabs in the header. This component is modular across different user roles, and is responsible for CRUD operations related to: 
              <ul>
                <li>Holidays</li>
                <li>Opening Hours</li>
                <li>Librarian Shifts</li>
                <li>Room Bookings</li>
                <li>[It also makes use of member, librarian and room related endpoints.]</li>
              </ul
            </li>
          </ul>
      </td>
      <td>79 hours</td>
    </tr>
    <tr>
      <td>Sebastian Ionascu</td>
      <td>
        <ul>
          <li> Created and modified the librarian home page and arranged it with its services</li>
          <li> Added the service where librarians can add book infos using a form with a button</li>
          <li> Added the service where librarians can add movie infos using a form with a button</li>
          <li> Added the service where librarians can add archive infos using a form with a button</li>
          <li> Added the service where librarians can add album infos using a form with a button</li>
          <li> Added the service where librarians can add newspaper infos using a form with a button</li>
          <li>Resolved some date libraries related bugs on the backend. (FYI: Spring controllers need @DateParam to accept specific date formats, and they also do not work with Java.SQL.date/time. They require Java.time.localdate instead. It would be a neat addition to the tutorials.)</li>
          <li> Modified service and controller methods for archive and newspaper info due to the change in date format forced by the frontend</li>
          <li> Modified unit tests of the service and controller methods for archive and newspaper info due to the change in date format forced by the frontend</li>
          <li> Help other teammates with their work and getting them started</li>
          <li> Participating in meetings </li>
          <li> Reviewed Pull Requests </li>
        </ul>
      </td>
      <td>38 hours</td>
    </tr>
  </tbody>
</table>

## Deliverable 4
<table>
  <tbody>
    <tr>
      <th>Team Member</th>
      <th>Responsibilities</th>
      <th>Hours (incl. meetings)</th>
    </tr>
    <tr>
      <td>Gabriel Lacroix</td>
      <td>
        <ul>
          <li>Participated in meetings and discussions</li>
          <li>Modified the following elements to make them responsive: the header bar, the item browsing, some home page elements, and a few other things.</li>
          <li>Tried to add PWA functionality to the web app</li>
          <li>Fix history mode</li>
          <li>Documented next steps and created issues to make the entire website usable on mobile</li>
          <li>Reviewed code</li>
        </ul>
      </td>
      <td>16 hours</td>
    </tr>
    <tr>
      <td>Guillaume Delmas-Frenette</td>
      <td>
        <ul>
          <li> Participated in meetings</li>
          <li> Wrote the code to make our web page installable on a mobile app </li>
          <li> Modified the member home page to conditionnaly render only some columns when on small devices </li>
          <li> Created issue to make sure everything on the marking scheme is taking care of </li>
          <li> Code review </li>
        </ul>
      </td>
      <td>10 hours</td>
    </tr>
    <tr>
      <td>Kirollos Roufail</td>
      <td>
        <ul>
          <li>Attended meetings</li>
          <li>Added confirm password in the update librarian page</li>
          <li>Made sure librarians and members cannot input blank fields</li>
          <li>Changed error messages</li>
          <li>Added a confirmation message to the member page</li>
        </ul>
      </td>
      <td>15 hours</td>
    </tr>
    <tr>
      <td>Louis Hildebrand</td>
      <td>
        <ul>
          <li>Participate in meetings and discussions</li>
          <li>Add comments and perform minor refactoring in member and librarian services</li>
          <li>Document mobile app building and testing</li>
          <li>Fix error when accessing frontend over HTTPS</li>
          <li>Miscellaneous aesthetic improvements and cleanup in the frontend</li>
        </ul>
      </td>
      <td>10 hours</td>
    </tr>
    <tr>
      <td>Mircea Gosman</td>
      <td>
        <ul>
          <li>Looked at Vue's PWA integration with our project & its NPM conflicts.</li>
          <li>Proposed Android Webview solution to simplify the port to mobile.</li>
          <li>Resolved bugs with app header & logout behavior.</li>
        </ul>
      </td>
      <td>3 hours</td>
    </tr>
    <tr>
      <td>Sebastian Ionascu</td>
      <td>
        <ul>
          <li>Completed the user documentation in the wiki</li>
          <li>Looked at Vue's PWA integration with our project</li>
          <li>Assigned issues to teammates</li>
          <li>Helped teammates resolve bugs</li>
          <li>Participate in meetings and discussions</li>
          <li>Review code and pull requests</li>
        </ul>
      </td>
      <td>7 hours</td>
    </tr>
  </tbody>
</table>
