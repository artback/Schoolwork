# TravelPlanner
###### Group E2

## Background

TravelPlanner is a project from the course `Programvarudesign(PA1415)` at
BTH. The purpose of the project is to learn how to work through all the
diffrent steps in making a product as team of 4-5 people.

TravelPlanner is an application where poeple can search for an book flights. As
an admin, you're able to add, remove and edit flights.

We chose to implement our version of TravelPlanner in Java.

## Installation
1. Extract the files from the zip file.

    * The zip file contains two folders. One with all the raw, uncompiled
       sources files and one folder with just a .jar-file and the database.

2. Make sure the database-file is located in the same folder as the executable
   before you run the jar-file.

3. If you'd like to compile the application yourself, simply run the [file]
   located in `/src`

## Use cases
We have two use cases; one as a customer and one as an admin.

#### Use case: Book and Pay
1. When the application is launched you start at the start screen. In the upper
right corner you see "Login" and "Register". If you don't already have an
account, make sure you register one.

    *Register*

    1. Fill out all the fields. There are no restrictions for any field.

    2. Press "Register" and your account will be registered and activated. You
       should be presented with a popup window, telling you if the account was
       successfully created or not. In the background, you're being redirected
       to the start page if your account was created.

    3. Depending on your account was successfully created or not, you should now
       be able to log in. Press the "Login" button in the top right corner.

    *Login*

    1. Fill in the fields displayed.

    2. Press the "Login" button. You should be presented with a popup window,
       telling you whether you're logged in or not. If you were successfully
       signed in, you'll be redirected to the start page. You are now able to
       book flights.

2. Enter a country to depart from and another country to arrive to. Fill in the
   date you'd like to depart(either manually filling out the field or use the
   calendar that pops up when you press the icon next to the input field).
   *We've added a complete dump of the database in a separate file to make it
    easier to know which countries and dates you're able to search for.* Press
    "Search". If the information you entered is valid, you'll be redirected to
    the book page where your search results are presented in a table.

3. Select the flight you'd like to book and press the "Book" button. You'll be
   redirected to the "Verify" screen.

4. Now that you're at the "Verify screen", fill out the amount of tickets you'd
   like to book. Please note that the maximum amount of tickets that you're able
   to book is either seven or the amount of empty seats in the flights,
   whichever is less.

5. You should now be at the "Pay" screen. Fill out a valid credit card number
   and press "Pay". **Please note that the credit card number is not saved
   anywhere**. You could also just enter any number that pass the Luhn algorithm.
   A popup window should let you know if the payment went through or not. If it
   did, you'll be redirected to the start page.

#### Use case: Admin
1. Follow the login procedure found in step one in the guide above for the
   other use case. Use the credentials admin/pwd to sign in as admin.

2. You can see that you're signed in as an admin in the header, top right, in
   the application. It should say "Welcome Admin".

3. You are now presented with the admin view. All flights are listed by default,
   but you can also make searches to filter away unwanted flights. To the right
   are three buttons. With these, you can either add, edit or remove flights.

    *Add*

    * When you add a flight you're presented with a new view. Here you get to
       enter all the information for a new flight. Fill out all fields and press
       "Submit" to enter the new flight to the database. A popup window will let
       you know if the flight has been added or not. You'll be redirected back
       to the list.

    *Edit*

    * Select a flight in the list and click "Edit". You're redirected to a new
       page where you get to see all the information for the flight in the
       database. Here you can edit all the information. Press "Submit" once
       you're happy with your changes. A popup should appear, letting you know
       if your changes were successfull or not. You'll be redirected back to the
       list.

    *Remove*

    * First, select a flight in the list by clicking on it. Next, you press the
       "Remove" button and the flight should disappear from the list.
