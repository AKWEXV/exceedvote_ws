Use Case Name
-------------
Criterion Vote

XRef
----
[Requirement](https://github.com/AKWEXV/exceedvote_ws/wiki/Requirement)

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
1. System presents the criterion, and select list of all contestants.
2. User give vote by select contestant from select list.
    * User can give 1 vote for 1 contestant.
    * User have different numbers of vote depend on user role.
3. User select 'Submit' button to submit their vote.
4. System presents vote submited by user.

Alternate Flows
---------------
3a. User decided to cancel vote, User select 'Cancel' button to cancel the process.

1. System redirect user back to vote page.


Frequency of Occurrence
-----------------------
Very frequent.