Use Case Name
-------------
Contestant Vote

XRef
----
[Requirement]()

Level
-----
User goal

Primary Actor
-------------
User (Voter)

Trigger
-------
The user wishes to vote.

Preconditions
-------------
* The user logged in to the system.
* The user came to the vote page.
* The user selected criterion to vote.

Postconditions
--------------
* The user is voted selected criterion.

Basic Flow
----------
1. System presents the criterion, and list of all contestants.
2. For each of contestants, User give vote score by using slider.
    * User can give vote score in range of 0 to 10 points. (Default is 0)
    * User need to give score to all contestants.
3. User select 'Submit' button to submit their vote.
4. System presents vote submited by user.

Alternate Flows
---------------
3a. User decided to cancel vote, User select 'Cancel' button to cancel the process.

1. System redirect user back to vote page.


Frequency of Occurrence
-----------------------
Very frequent.