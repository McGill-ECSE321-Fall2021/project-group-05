#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"
# base_url="http://localhost:9999"

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

# Create member
echo "Successfully create member without online account"
response=$(curl --request POST "$base_url/member/" --data '
{
	"fullName": "Obi-Wan Kenobi",
	"address": "212 McGill Street"
}')
# TODO: Expect 200 response
echo "$response"
member_id=$(echo "$response" | jq -r '.["id"]')
echo
echo "Fails to create member with missing full name"
curl --request POST "$base_url/member/" --data '{"address": "212 McGill Street"}'
# TODO: Expect 400 response
echo

# Create online account
echo "Successfully create online account"
curl --request POST "$base_url/member/$member_id/onlineAccount/" --data '
{
	"username": "ben-kenobi",
	"emailAddress": "obi-wan.kenobi@mail.mcgill.ca",
	"password": "thehighground123"
}'
# TODO: Expect 200 response
echo
echo "Fails to create online account for member that already has an account"
curl --request POST "$base_url/member/$member_id/onlineAccount/" --data '
{
	"username": "ben-kenobi",
	"emailAddress": "obi-wan.kenobi@mail.mcgill.ca",
	"password": "thehighground123"
}'
# TODO: Expect 400 response
echo

# Get member by ID
echo "Successfully get member by ID"
curl --request GET "$base_url/member/$member_id/"
# TODO Expect 200 response
echo
echo "Fail to get nonexistent member"
curl --request GET "$base_url/member/9999999999/"
# TODO: Expect 404 response
echo

# Get all members
echo "Successfully get all members (there should be at least one)"
curl --request GET "$base_url/member/all/"
# TODO: Expect 200 response
echo

# Activate account
echo "Successfully activates account"
curl --request POST "$base_url/activate/$member_id/"
# TODO: Expect 200 response
echo
echo "Fail to activate already active account"
curl --request POST "$base_url/activate/$member_id/"
# TODO: Expect 400 response
echo

# Apply status penalty
echo "Successfully apply penalty to account"
curl --request POST "$base_url/member/$member_id/applyPenalty"
# TODO: Expect 200 response
echo
echo "Fail to apply penalty to nonexistent member"
curl --request POST "$base_url/member/9999999999/applyPenalty"
# TODO: Expect 404 response
echo

# Remove status penalty
echo "Successfully remove penalty from account"
curl --request POST "$base_url/member/$member_id/removeenalty"
# TODO: Expect 200 response
echo
echo "Fail to remove penalty from nonexistent member"
curl --request POST "$base_url/member/9999999999/removePenalty"
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

# Delete room
echo "Deletes room successfully (no output)"
curl -s --request DELETE "$base_url/room/$room_id"
echo
echo "Fails to delete room with invalid ID"
# Should not exist anymore
curl -s --request DELETE "$base_url/room/$room_id"
echo
