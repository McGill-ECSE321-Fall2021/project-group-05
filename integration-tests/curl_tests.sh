#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"

echo "Creates book successfully"
curl --request POST "$base_url/bookInfo/aTitle?numberOfPage=256&author=John&isbn=123456789"
echo
echo "Fails to create a book with 0 pages"
curl --request POST "$base_url/bookInfo/anotherTitle?numberOfPage=0&author=John&isbn=987654321"
echo
# Add the tests to delete the book right after creating it
