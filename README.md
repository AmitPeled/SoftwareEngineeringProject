# GCM - Global City Map
Software engineering course final project.

# Checklist
## Requirements
### Search
- [x] Search by city name
- [x] Search by description
### Editing
#### Approval Requests - **Need to implement for tours/sites as well**
- [x] DAO methods
- [x] GUI
- [x] Server side methods - **Returns "null" references instead of actual Tours** 
- [x] Client-Server communication
#### Map object
- [x] **Map object doesn't contain all it's sites and tours (works when fetching them through other DAO methods)**
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
#### One-time purchase
- [ ] GUI controls that offer single-purchase
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
- [ ] GUI
- [x] DAO methods - `getUserDetails` throws exception
- [ ] Server methods
- [ ] Client-Server communication
### Notifications
- [ ] Create a notification thread on the server that sends messages to customers

## Features
### Guests
- [ ] Allow guests to use the system

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
- [ ] Build customer report screen
- [ ] Fetch real sites/tours to the map viewer
- [ ] Load real maps from the database
- [ ] Allow login in as guests
## Server
- [ ] Build the notifications thread

