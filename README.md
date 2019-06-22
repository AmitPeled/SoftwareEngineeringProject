# GCM - Global City Map
Software engineering course final project.

# Checklist
## Requirements
### Search
- [x] Search by city name
- [x] Search by description
- [ ] Showing One-time purchase only for guests
- [ ] Showing Purchase options for customers
### Editing
#### Approval Requests - City
- [x] DAO methods
- [x] GUI
- [x] Server side methods 
- [x] Client-Server communication
#### Approval Requests - Sites
- [x] DAO methods
- [x] GUI
- [ ] Server side methods - **Does not show Site applications**
- [ ] Client-Server communication - **Does not show Site applications**
#### Approval Requests - Tours
- [x] DAO methods
- [x] GUI
- [x] Server side methods 
- [x] Client-Server communication
#### Approval Requests - Map Reports
- [x] DAO methods
- [x] GUI
- [x] Server side methods 
- [x] Client-Server communication
#### Map object
- [x] **Map object doesn't contain all it's sites and tours (works when fetching them through other DAO methods)**
#### Editing Maps
- [ ] GUI screen to add a map
#### Creating a city
- [x] DAO methods
- [x] GUI
- [x] Client-Server communication
- [x] Creates an approval request
#### Adding maps
- [x] GUI
- [ ] Link to mapviewer
- [x] DAO methods
- [x] Client-Server communication
- [x] Creates an approval request
#### Adding sites
- [x] DAO methods
- [x] GUI
- [x] Client-Server communication
- [x] Creates an approval request
#### Deleting sites
- [x] GUI
- [x] DAO methods - **There's no method to delete a site completely**
- [x] Client-Server communication - **No method to delete site completely**
- [x] Creates an approval request - **There are methods to approve deletion, but none to create a removal request**
#### Tours
##### Creating a new tour
- [x] GUI
- [x] DAO methods 
- [x] Client-Server communication
- [x] Creates an approval request
- [x] Appear in Map object - **Not all tours appear in Map object**
##### Adding sites to tour (editing an existing tour)
- [x] GUI
- [x] DAO methods
- [x] Client-Server communication
- [x] Creates an approval request
### Purchasing maps
** Note - purchase is for **cities**, and not for maps. 
if one-time purchase, the user gets all the city's maps at once of their current version (and is not allowed to redownload the maps again). **

#### One-time purchase
- [x] GUI controls that offer single-purchase
- [ ] Updates the database
- [ ] Allows users to download maps only after subscription has been purchased
#### Full subscription
- [ ] GUI controls that offer single-purchase
- [ ] Updates the database
- [ ] Allows users to download maps only after subscription has been purchased
### Prices
- [ ] GUI - **Need to edit prices for all subscription/purchase types**
- [x] DAO methods - **Exists, but only with one price without specifications**
- [x] Client-Server communication
- [x] Creates an approval request
- [ ] only privileged users can edit price
### Managers Reports
- [ ] Report on a single city works with real DB data
- [ ] Report on all cities works with real DB data
- [ ] Only admins can view reports
### Customer Reports
- [x] GUI
- [x] DAO methods
- [x] Server methods
- [x] Client-Server communication
### Notifications
- [x] Create a notification thread on the server that sends messages to customers

## Features
### Guests
- [x] Allow guests to use the system

# Tasks
## Database/DAO
- **Fix `getToursAddEdits` returning null references** // amit's comment: not relevant anymore, replaced by getTourSubmissions
- [x] **Fix `getUserDetails` throwing null reference exception**
- [x] Verify that no other `get*Edits` returns null references // amit's comment: applied for get*Submissions
- [x] Fix `Map` objects not having all the sites/tours (compared to what's printed on the map/fetched by `getSitesByMapId`) // amit's comment: no such method `getSitesByMapId` exists, though fetching by map works
- [x] Add option to delete sites
- [x] Add methods to change prices per purchase type
- [x] (If we have time) Change the way reports are stored to support date-range selection
## Client
- [ ] Push MapDownloader class to the master
- [ ] Hide menu options from non-privileged users
- [x] Build customer report screen
- [ ] Fetch real sites/tours to the map viewer
- [ ] Load real maps from the database
- [x] Allow login in as guests
- [ ] Fix exception in site submission approval 
- [ ] GUI window notifying the emails sent by server after map submission approval
- [ ] Allow user to edit its personal details

## Server
- [x] Build the notifications thread 
- [ ] Prevent multiple user sessions at once

## Database
- [ ] Edit user personal details functionality
