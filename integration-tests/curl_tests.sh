#!/usr/bin/env bash
echo "Creates book successfully"
curl --request POST "http://localhost:9999/bookInfo/aTitle?numberOfPage=256&author=John&isbn=123456789"
echo
echo "Fails to create a book with 0 pages"
curl --request POST "http://localhost:9999/bookInfo/aTitle?numberOfPage=0&author=John&isbn=123456789"
echo
