#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"
# Add the tests to delete the book right after creating it
# Create a book item
echo "Creates a book successfully"
curl --request POST "$base_url/book/1"
echo
echo "Fails to create a book when given an invalid id for the book info"
curl --request POST "$base_url/book/1000"
echo
# Delete a book item
echo "Deletes a book successfully, no output expected"
curl --request DELETE "$base_url/book/6"
echo
echo "Fails to delete a book when given the id of a unexisting book"
curl --request DELETE "$base_url/book/6"

# Create a movie
echo "Creates a movie successfully"
curl --request POST "$base_url/movie/2"
echo 
echo "Fails to create a movie when given an invalid id for the movie info"
curl --request POST "$base_url/movie/1000"
echo
# Delete movie
echo "Deletes a movie successfully, no output expected"
curl --request DELETE "$base_url/movie/7"
echo
echo "Fails to delete a movie when given the id of a unexisting movie"
curl --request DELETE "$base_url/movie/7"
echo 

# Create an album successfully
echo "Creates album successfully"
curl --request POST "$base_url/album/3"
echo 
echo "Fails to create an album when given an invalid id for the album info"
curl --request POST "$base_url/album/1000"
echo
# Delete an album successfully
echo "Deletes an album successfully, no output expected"
curl --request DELETE "$base_url/album/8"
echo
echo "Fails to delete an album when given the id of a unexisting movie"
curl --request DELETE "$base_url/album/8"
echo 

# Create a newspaper successfully
echo "Creates newspaper successfully"
curl --request POST "$base_url/newspaper/4"
echo 
echo "Fails to create a newspaper when given an invalid id for the newspaper info"
curl --request POST "$base_url/newspaper/1000"
echo
# Delete a newspaper successfully
echo "Deletes a newspaper successfully, no output expected"
curl --request DELETE "$base_url/newspaper/9"
echo
echo "Fails to delete a newspaper when given the id of a unexisting newspaper"
curl --request DELETE "$base_url/newspaper/9"
echo
 
# Create an archive successfully
echo "Creates archive successfully"
curl --request POST "$base_url/archive/5"
echo 
echo "Fails to create an archive when given an invalid id for the archive info"
curl --request POST "$base_url/archive/1000"
echo
# Delete an archive successfully
echo "Deletes an archive successfully, no output expected"
curl --request DELETE "$base_url/archive/10"
echo
echo "Fails to delete an archive when given the id of a unexisting archive"
curl --request DELETE "$base_url/archive/10"
echo 

# Create librarian
echo "Creates librarian successfully"
curl --request POST "$base_url/librarian/create?fullName=Jocasta%20Nu,username=jocasta.nu,password=securepassword12345"
echo
echo "Fails to create a librarian with empty full name, username, and password"
curl --request POST "$base_url/librarian/create"
echo
