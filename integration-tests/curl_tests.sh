#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"

# Install jq (a JSON parser)
sudo apt install -y jq &> /dev/null

echo "Creates book successfully"
curl --request POST "$base_url/bookInfo/aTitle?numberOfPage=256&author=John&isbn=123456789"
echo
echo "Fails to create a book with 0 pages"
curl --request POST "$base_url/bookInfo/anotherTitle?numberOfPage=0&author=John&isbn=987654321"
echo
# Add the tests to delete the book right after creating it

# Create librarian
echo "Creates librarian successfully"
curl --request POST "$base_url/librarian?fullName=Jocasta%20Nu,username=jocasta.nu,password=securepassword12345"
# TODO: expect 200 response
echo
echo "Fails to create a librarian with missing password"
curl --request POST "$base_url/librarian?fullName=Bilbo%20Baggins,username=bilbo.baggins"
# TODO: expect 400 response
echo

# Get librarian
echo "Gets librarian successfully"
curl --request GET "$base_url/librarian?username=jocasta.nu"
# TODO: expect 200 response
echo
echo "Fails to get librarian that does not exist"
curl --request GET "$base_url/librarian?username=nonexistent.librarian"
# TODO: expect 404 response
echo
echo "Gets all librarians successfully"
curl --request GET "$base_url/librarian/all"
# TODO: expect 200 response
echo

# Delete librarian
echo "Successfully deletes librarian by username"
curl --request DELETE "$base_url/librarian?username=jocasta.nu"
echo
echo "Fails to delete librarian that does not exist"
curl --request DELETE "$base_url/librarian?username=invalid.username"
echo

# Create room
echo "Creates room successfully"
response=$(curl -s --request POST "$base_url/room?capacity=10&name=A%20Nice%20Room")
echo "$response"
room_id=$(echo "$response" | jq -r '.["id"]')

echo "Fails to create room with missing capacity"
curl -s --request POST "$base_url/room?name=A%20Nice%20Room"
echo

# Get room by ID
echo "Gets room successfully"
curl -s --request GET "$base_url/room/$room_id"
echo
echo "Fails to get room with invalid ID"
curl -s --request GET "$base_url/room/999999"
echo

# Delete room
echo "Deletes room successfully (no output)"
curl -s --request DELETE "$base_url/room/$room_id"
echo
echo "Fails to delete room with invalid ID"
# Should not exist anymore
curl -s --request DELETE "$base_url/room/$room_id"
echo

# Create Holiday
echo "Creates holiday successfully"
response=$(curl -s --request POST "$base_url/holiday?name=Easter&startDate=2020-03-01&endDate=2020-03-02")
echo "$response"
holiday_id=$(echo "$response" | jq -r '.["id"]')
echo "Fails to create Holiday with missing name"
curl -s --request POST "$base_url/holiday?startDate=2020-03-01&endDate=2020-03-02"
echo 

# Get holiday by date
echo "Gets holiday by date successfully"
curl -s --request GET "$base_url/holiday/2020-03-01"
echo
echo "Fails to get holiday with invalid date"
curl -s --request GET "$base_url/holiday/2019-03-01"
echo

# Get holiday by startDate & endDate
echo "Gets holiday by date successfully"
curl -s --request GET "$base_url/holiday/2020-02-01/2020-04-01"
echo
echo "Fails to get holiday with inverdated startDate & endDate"
curl -s --request GET "$base_url/holiday/2019-04-01/2020-02-01"
echo

# Delete Holiday
echo "Deletes holiday successfully"
curl -s --request DELETE "$base_url/holiday/$holiday_id"
echo
echo "Fails to delete holiday with invalid ID"
curl -s --request DELETE "$base_url/holiday/$holiday_id"
echo

# Create Library Opening Hours
echo "Creates Library Opening Hours successfully"
response=$(curl -s --request POST "$base_url/libraryOpeningHours?date=2020-03-01&startTime=10:00:00&endTime=17:00:00")
echo "$response"
opening_hour_id=$(echo "$response" | jq -r '.["id"]')
echo "Fails to create Library Opening Hours with missing date"
curl -s --request POST "$base_url/libraryOpeningHours?startTime=10:00:00&endTime=17:00:00"
echo 

# Get Library Opening Hours by date
echo "Gets Library Opening Hours by date successfully"
curl -s --request GET "$base_url/libraryOpeningHours/2020-03-01"
echo
echo "Fails to get Library Opening Hours with invalid date"
curl -s --request GET "$base_url/libraryOpeningHours/2019-03-01"
echo

# Get Library Opening Hours by startDate & endDate
echo "Gets Library Opening Hours by date successfully"
curl -s --request GET "$base_url/libraryOpeningHours/2020-02-01/2020-04-01"
echo
echo "Fails to get Library Opening Hours with inverdated startDate & endDate"
curl -s --request GET "$base_url/libraryOpeningHours/2019-04-01/2020-02-01"
echo

# Delete Library Opening Hours
echo "Deletes Library Opening Hours successfully"
curl -s --request DELETE "$base_url/libraryOpeningHours/$opening_hour_id"
echo
echo "Fails to delete Library Opening Hours with invalid ID"
curl -s --request DELETE "$base_url/libraryOpeningHours/$opening_hour_id"
echo

# Create LibrarianShift
echo "Creates Librarian Shift successfully"

# Create Librarian
librarian=$(curl --request POST "$base_url/librarian?fullName=Jocasta%20Nu,username=jocasta.nu,password=securepassword12345")
librarian_id=$(echo "$response" | jq -r '.["id"]')

# Create LibrarianShift Succesfully
response=$(curl -s --request POST "$base_url/librarianShift?date=2020-03-01&startTime=10:00:00&endTime=17:00:00&librarianId=$librarian_id")
echo "$response"
librarianShift_id=$(echo "$response" | jq -r '.["id"]')

# Create LibrarianShift Unsuccessfully
echo "Fails to create Library Opening Hours with missing date"
curl -s --request POST "$base_url/librarianShift?startTime=10:00:00&endTime=17:00:00&librarianId=$librarian_id"
echo 

# Get LibrarianShift by date
echo "Gets LibrarianShift by date successfully"
curl -s --request GET "$base_url/librarianShift/2020-03-01"
echo
echo "Fails to get LibrarianShift Hours with invalid Date"
curl -s --request GET "$base_url/librarianShift/2019-04-01"
echo

# Get LibrarianShift by librarian ID
echo "Gets LibrarianShift by librarian ID successfully"
curl -s --request GET "$base_url/librarianShift/$librarian_id"
echo
echo "Fails to get LibrarianShift Hours with invalid librarian ID"
curl -s --request GET "$base_url/librarianShift/99999"
echo

# Get LibrarianShift by librarian ID and date
echo "Gets LibrarianShift by librarian ID and date successfully"
curl -s --request GET "$base_url/librarianShift/$librarian_id/2020-03-01"
echo
echo "Fails to get LibrarianShift Hours with invalid librarian ID"
curl -s --request GET "$base_url/librarianShift/99999/2020-03-01"
echo

# Delete Library Opening Hours
echo "Deletes LibrarianShift successfully"
curl -s --request DELETE "$base_url/librarianShift/$librarianShift_id"
echo
echo "Fails to delete LibrarianShift with invalid ID"
curl -s --request DELETE "$base_url/librarianShift/$librarianShift_id"
echo

# Delete leftover Librarian
curl --request DELETE "$base_url/librarian/$librarian_id"
