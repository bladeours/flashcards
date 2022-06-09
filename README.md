# Flashcard
Simple app that helps you study languages.
## Table of Contents
* [General Info](#general-info)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Database](#database)
* [First Steps](#first-steps)

## General Info
Flashcard is a university project but also helps me to better understand Spring, Hibernate and client-server architecture.
I think everybody knows paper flashcard, but I think better option it to use technology. With this app you 
can easily create new set, change languages, check your score and personalize your set with colors.

## Technologies Used
* Java 17
* JavaFX 17
* CSS
* PostgreSQL
* Hibernate
* Spring
* Gson

## Setup
**You can [download it here](https://github.com/bladeours/homeBudget/releases/tag/v0.0.1)**
<br>
After downloading and unzip you will see two folders `client` and `server`.
<br>
All you have to do is open terminal, go to server folder and run `java -jar serverFlashcard.jar`. After that you can go
to client folder and run `Flashcard.exe`.

## Database
In this project I'm using postgreSQL as my database, you can [download](https://github.com/bladeours/homeBudget/releases/tag/v0.0.1) script to create all tables.
Also, here is database structure:
<br>
![database.png](img/database.png)
## First steps

**Rules:**
<br>
After clicking on set and choosing language you will see rectangle with sentence. You can check translate by
clicking on rectangle then you can choose if you knew it or not. Sets are shuffled and sentence where you choose
`correct` will not be visible until you reset score in `Score` view.



**Create new set:**
<br>
Just click `Create new`, under set name you can pick color of your set.
<br>
No field can be empty and set name has to be unique.


**Reset score:**
<br>
You can reset score for language in `Score` view then by clicking `X` button next to
your score.











