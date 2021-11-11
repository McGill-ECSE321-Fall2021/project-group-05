#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"
# base_url="http://localhost:9999"

# Install jq (a JSON parser)
sudo apt install -y jq &> /dev/null

echo "Creates book successfully"
curl -s --request POST "$base_url/bookInfo/aTitle?numberOfPage=256&author=John&isbn=123456789"
echo
echo "Fails to create a book with 0 pages"
curl -s --request POST "$base_url/bookInfo/anotherTitle?numberOfPage=0&author=John&isbn=987654321"
echo
# Add the tests to delete the book right after creating it

# Create librarian
echo "Creates librarian successfully"
curl -s --request POST "$base_url/librarian?fullName=Jocasta%20Nu&username=jocasta.nu&password=securepassword12345"
# TODO: expect 200 response
echo
echo "Fails to create a librarian with missing password"
curl -s --request POST "$base_url/librarian?fullName=Bilbo%20Baggins&username=bilbo.baggins"
# TODO: expect 400 response
echo

# Get librarian
echo "Gets librarian successfully"
curl -s --request GET "$base_url/librarian?username=jocasta.nu"
# TODO: expect 200 response
echo
echo "Fails to get librarian that does not exist"
curl -s --request GET "$base_url/librarian?username=nonexistent.librarian"
# TODO: expect 404 response
echo
echo "Gets all librarians successfully"
curl -s --request GET "$base_url/librarian/all"
# TODO: expect 200 response
echo

# Delete librarian
echo "Successfully deletes librarian by username"
curl -s --request DELETE "$base_url/librarian?username=jocasta.nu"
echo
echo "Fails to delete librarian that does not exist"
curl -s --request DELETE "$base_url/librarian?username=invalid.username"
echo

# Create member
echo "Successfully create member without online account"
response=$(curl -s --request POST -H "Content-Type: application/json" "$base_url/member/" --data '
{
	"fullName": "Obi-Wan Kenobi",
	"address": "212 McGill Street"
}')
# TODO: Expect 200 response
echo "$response"
member_id=$(echo "$response" | jq -r '.["id"]')
echo
echo "Fails to create member with missing full name"
curl -s --request POST -H "Content-Type: application/json" "$base_url/member/"  --data '{"address": "212 McGill Street"}'
# TODO: Expect 400 response
echo

# Create online account
echo "Successfully create online account"
curl -s --request POST -H "Content-Type: application/json" "$base_url/member/$member_id/onlineAccount/" --data '
{
	"username": "ben-kenobi",
	"emailAddress": "obi-wan.kenobi@mail.mcgill.ca",
	"password": "thehighground123"
}'
# TODO: Expect 200 response
echo
echo "Fails to create online account for member that already has an account"
curl -s --request POST -H "Content-Type: application/json" "$base_url/member/$member_id/onlineAccount/" --data '
{
	"username": "ben-kenobi",
	"emailAddress": "obi-wan.kenobi@mail.mcgill.ca",
	"password": "thehighground123"
}'
# TODO: Expect 400 response
echo

# Get member by ID
echo "Successfully get member by ID"
curl -s --request GET "$base_url/member/$member_id/"
# TODO Expect 200 response
echo
echo "Fail to get nonexistent member"
curl -s --request GET "$base_url/member/9999999999/"
# TODO: Expect 404 response
echo

# Get all members
echo "Successfully get all members (there should be at least one)"
curl -s --request GET "$base_url/member/all/"
# TODO: Expect 200 response
echo

# Activate account
echo "Successfully activates account"
curl -s --request POST "$base_url/member/$member_id/activate"
# TODO: Expect 200 response
echo
echo "Fail to activate already active account"
curl -s --request POST "$base_url/member/$member_id/activate"
# TODO: Expect 400 response
echo

# Apply status penalty
echo "Successfully apply penalty to account"
curl -s --request POST "$base_url/member/$member_id/applyPenalty"
# TODO: Expect 200 response
echo
echo "Fail to apply penalty to nonexistent member"
curl -s --request POST "$base_url/member/9999999999/applyPenalty"
# TODO: Expect 404 response
echo

# Remove status penalty
echo "Successfully remove penalty from account"
curl -s --request POST "$base_url/member/$member_id/removePenalty"
# TODO: Expect 200 response
echo
echo "Fail to remove penalty from nonexistent member"
curl -s --request POST "$base_url/member/9999999999/removePenalty"
# TODO: Expect 404 response
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

# Get all rooms
echo "Gets all rooms successfully"
curl -s --request GET "$base_url/room/"
echo
# Get all room bookings
echo "Gets all room bookings successfully"
curl -s --request GET "$base_url/roomBooking/"

# Delete room
echo "Deletes room successfully (no output)"
curl -s --request DELETE "$base_url/room/$room_id"
echo
echo "Fails to delete room with invalid ID"
# Should not exist anymore
curl -s --request DELETE "$base_url/room/$room_id"
echo
