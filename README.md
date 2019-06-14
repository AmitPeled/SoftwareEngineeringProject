# GCM - Global City Map
Software engineering course final project.

# Tasks
## Database
### Editions and approvals (Amit)
- [x] Add "Status" columns to every modifyable table
- [ ] Implement the status column on the server side
- [ ] Create queries to get all unpublished rows
- [ ] Create datatypes for the queries
### Subscription (Eli)
- [ ] Store in the database subscription information 
- [ ] Implement relevant queries on server side
- [ ] Implement DAO methods
### Purchase And Downloads (Eli)
- [ ] Store purchase history in the database
- [ ] Store maps download history 
- [ ] Build queries for purchase history
- [ ] Build queries for download statistics (support date ranges!)

## Server
### Approvals (Amit)
- [ ] Support price changes (Content manager proposes, general manager approves)
### Sites 
- [ ] Support editing sites
- [ ] Support adding sites
- [ ] Support removing sites
### Cities 
- [ ] Support editing cities
- [ ] Support adding cities
- [ ] Support removing cities
### Tours
- [ ] Support editing tours
- [ ] Support adding tours
- [ ] Support removing tours
### Maps
- [ ] Support editing maps
- [ ] Support adding maps
- [ ] Support removing maps

# Checklist
## Search 
- [x] Searching maps by City (Gavri)
- [x] Searching maps by Sites (Gavri])
- [ ] Searching maps by both (Gavri)

## Editing map (asaf)
### Adding a city
- [x] UI controls
- [x] Database communication
- [ ] Including in map display scene
### Adding a map
- [x] UI controls
- [x] Database communication
- [ ] Including in map display scene
### Adding a site
- [x] UI controls
- [x] Database communication
- [ ] Including in map display scene 
### Editing a site
- [x] UI controls
- [x] Database communication
- [ ] Including in map display scene 
### Adding a tour
- [x] UI controls
- [ ] Database communication
- [ ] Including in map display scene 

## Registration (Done)
- [x] Registring a new user
- [x] Login to the system using an existing username and password

## Purchasing maps
- [ ] Purchasing maps (Eli and Amit sync on a DAO method)
- [ ] Downloading purchased maps (?)
- [ ] Purchasing a subscription (?)

## Notification (Asaf will take)
- [ ] System sends a notification to users when subscription runs out (Asaf)
- [ ] Sending a notification to users when a map change has been approved 

## Prices
- [ ] Changing map prices (Gavri to push)

## Reports (Scenes ready, need server communication)
### Selecting a time frame
- [x] GUI
- [ ] Server side implementation
### City report
- [x] GUI
- [ ] Server side implementation
### All cities report
- [x] GUI
- [ ] Server side implementation
### Customer personal information
- [x] GUI
- [ ] Server side implementation
### Manager sees customer information
- [x] GUI
- [ ] Server side implementation

# Intro
This document will be used by us to keep us all on the same page as to what we're doing and what this project is about.
In the first part I'll overview the design and go over the components of the system along with the different data types. In the second part I'll write some standards that I think we must keep in our codebase, and leave some useful tips and references to some useful lectures about how to write good, clean and robust code, which I invite you all to use. Keep in mind that everything I write here is due to good and bad experience, and I believe we can make our job 5 times easier if we stick to these standards.

# Components
### Client application
The client application is used by the customers to purchase maps, and by the admins/editors to edit the maps and approve content changes.
We'll go over how the client application system will work in later time

### Server Side - Database
The database side is completely seperate from the client side. It will host all the data regarding users and all the maps with their additional information.
We'll go over how the server side will work in later time

# Data types
The 2 main data types we're dealing with in our code are maps and users. The users data type is quite simple and is found in many systems. Users have username, encrypted passwords, ID number and privileges. The maps are a bit more complicated and I believe I have a good solution on how to store them. 

### Users
It was heavily implied that we should probably have different classes for different types of different users with different privileges and bla bla bla and it's all stupid. 

here's how we do it:
##### User class
Users include the customers, content editors, content admins, admins, etc... Every person that gets past the login screen is a **user** of the system. A user class has these properties:

![User](https://i.ibb.co/6nPFQQV/image.png)

Notice that there is a private `Privileges` field, which brings me to my next point:
##### Privileges
In the project requirements we were asked to implement some privilege system which heavily implied that we shuold use inheritance. There's a much simpler and better solution, and that's using an enum:

![Privileges](https://i.ibb.co/60KWZHq/image.png)

The user class has two public methods: `IsAllowedTo` and `CanApprove` which as you guessed, takes a privilege and returns a true or false if the user is allowed to or can approve the action in the parameter. 

I have some more complex solutions for cases such as wanting to remove/add some privilege from all content-admin/editors/whatever, but we'll leave that for later.

### Maps
Maps are a bit more complicated, but here's my solution:

![Maps](https://i.ibb.co/pKssrYv/image.png)

There are many ways to complicate this, so this is the most simple solution. Every map has a unique ID. The name of the image file of the map will have the same idea and will be stored seperately. Because there can be different maps for every city and these maps can overlap/not overlap one another, every map will also have an offset, which means what coordinate is the top left part of the map. The width and height are not the width and height in pixels, but rather real-world size the the map covers.

Now, building a class to display maps is much simpler. You take the ID, look for an image file with this ID, go over all the sites* in the city and check if they're within the boundries of the maps (using offset, width and height), display them on top of the image, etc...
We can extend this functionality to show only some sites on some maps if we really want but this is the base of it all.

\* Notice that in some earlier UMLs I called "site" "PlaceOfInterest". Site is simpler.

# Standards
Everything I'm going to write here is stuff that I've learned the hard way. If you don't want to scartch your head and rewrite 1000 lines of code that you just finished writing, or deleting 10 files of classes that are no longer relevant because you guessed the inheritance hierchy wrong, I suggest you spend some time to read this. Having said that, you can write whatever code you wish and there are very few rules here.

### Working with GitHub
**If something isn't clear or if you have any problems when working, feel free to contact me at any time for questions**
I'm not gonna write a full git tutorial here, so if some term I mention isn't clear please use google. 

The best way by far to work with github and with a central repository in general is:
* ***REBASE FROM MASTER BEFORE YOU START WORKING!!!***
  By far the most important thing is that before you start working, you switch to your master branch, rebase from origin (GitHub), and then take all your feature branches and merge/rebase them from/on top of master. Not following this rule will cause merge conflicts that are either unsolveable or will be so fucking long that you would wish you were never born. Trust me.
* **Keep one brance (master) on GitHub**
  In my work we have a huge software with probably over 100k lines of code, and we only use one branch. We don't need to complicate things by adding remote branches. 
* **Use a different branch when working locally (on your computer)**
  Locally, don't work on top of the master branch. 
  Create a new local branch (Also called **feature branch**) and work on it. 
  Do as many commits as you want locally, and work however you want on your local branch. 
* **Create a pull request when you want to push your changes**
  You need to push the local branch you creates, go to GitHub and create a pull request on your branch. Than ask for someone else to approve it and it will merge your branch to the master branch.


### Code Standards
- ##### Documentation
  There's a standard in Java called JavaDoc that's used to document your classes. There are some examples here:
  https://www.tutorialspoint.com/java/java_documentation.htm .
  It's part of the project requirements but it's also very important.
  
- ##### No inheritance
  Inheritance is a dumb idea that never works and always leads to madness. Don't use it. If you want to use it please talk to me and we'll find a better solution. I'll give an example from this project:
In this project we had some requirements around user privileges. They heavily implied that we do this:

![Inheritance](https://i.ibb.co/rHpbKs7/image.png)

And it makes sense, until you need to change something. Some day some time you're gonna want to break this hierchy in some way. You'll want to have something that's exclusive to content editors, but don't want content admins to have it, or maybe you want admins and content editors to have it but not content admins. Now what do you do? You have several options and they all suck. You either:
  - Split everything into different classes with different privileges and repeat a lot of code
  - Make some new abstract classes and find a smart way to use Java single inheritance to acheive what you want, probably by inheriting a lot of functions that you don't need and overriding them. You'll eventually have a "Gorilla code" where you'll have 4 functions that you need and 10 functions that look like this:
   ```java
   public override bool SomeFunctionThatNoOneWants(Something something)
   {
       return false;
   }
   ```
   And it's no coincidence. Inheritance also did something in this solution that shouldn't have happened. We now have dependnacy between all these different types of users who have really nothing to do with each other. Why does the admin need to be affected by what the content editor does? Why does the content admin needs to know what the user class has? every time you change something in one class, you have to think about what happens in the 4190 other classes in the inheritance chain. 
   So we don't use inheritance, but what do we do instead? Use interfaces
   
- ##### Use interfaces between logic classes
  Interfaces are like abstract classes, but with no implementations. They are basically a list of public function signatures. Unlike normal inheritance, a class can `implement` (not inherit, although it's similar) as many interfaces as you want. When a class inherits an interface, you can reference the class by it's interface type without knowing it's actual type. When you implement an interface you have to implement all the function signatures.
  Unless it's classes that are just holding data (data types like `User` and other examples from above), **the interactions between classes shuold always be through an interface**. It's also good because it's easier to mock and test your classes. Instead of constructing 5 classes because you have a dependency in those classes, you simply mock an interface.
  Here's an example of inetrface usage: https://www.w3schools.com/java/java_interface.asp

Everytime you're not sure about anything, feel free to contact me and I'd be more than happy to help.
