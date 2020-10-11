# Assessment-3-Database-and-Multithread-Programming

## Task 1: Database Application (15 marks)

For this program, you are to write a Java GUI-based program that allows the user to view, insert and update information within a MySQL database. 

Your database will contain only one table, called Staff, with the following fields: An ID (the primary key), last name, first name, middle initial (MI), address, city, state (ACT, NSW, NT, QLD, SA, TAS, VIC, WA), and a telephone number. 

Your program should allow the user to view a staff record with a specified ID, to insert a new record into the table, to update any field (except the ID) of an existing staff member record, and to clear all fields from the display. Your GUI design should be similar to the image below. In addition, make sure you include the following features: 

* Display an appropriate message indicating the success or failure of the View/Insert/Update operations. For example, "Record found/record not found" when the user selects View, "Record Inserted/failed to insert" when the user selects Insert, and "Record updated/failed to update" when the user selects Update.
* When no record is displayed (e.g., when the program first starts), or when the user selects Clear, display a message inviting the user to view or insert a new record. 
* When inserting a new record, the ID field should be generated automatically so that it is unique for the table. 
* The ID field should only be editable when the user chooses to search for a new record to View. IDs returned from the database through the View operation, or generated as part of the Insert operation, should not be editable. 
* When Inserting or Updating a record, ensure all fields (e.g., Last name, first name, etc.) are not left blank, and that the telephone number contains only digits. Ensure the state is valid.

![alt text](https://imgur.com/EgFGWcA.jpg "Task ") 

## Task 2: Data Visualisation  (15 marks)
Using the same database as in Task 1 above, write a Java GUI application that connects to your database and displays a pie chart representation for the percentage of staff in the Staff table that reside in each state. Your display should look similar to the image below. Only display output for the states where staff reside. In addition to the pie chart graphic, make sure you report the written percentage for each state in a legend that appears at the bottom of the window. 

![alt text](https://imgur.com/VhNLOVq.jpg "Task 2") 

## Task 3: Multi-threaded Animation (10 marks)
The program described in listing 15.12 of your textbook displays a bouncing ball, as shown below: 

![alt text](https://imgur.com/vQ1O5ID.jpg "Task 3")

Modify this program so that:
* it has a label which displays the current speed of the ball
* users can increase and decrease the speed of the ball by using an up or down arrow key 
* it uses a thread to animate the bouncing ball movements
Acknowledgment: Task 3 is based on Exercise 32.6 from the textbook.
