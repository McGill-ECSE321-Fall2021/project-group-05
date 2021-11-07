#!/usr/bin/env bash

base_url="https://onlinelibrary-backend-05a.herokuapp.com"
# Create a book info
echo "Creates book info successfully"
curl --request POST "$base_url/bookInfo/aTitle?numberOfPage=256&author=John&isbn=123456789"
echo
echo "Fails to create a book info with 0 pages"
curl --request POST "$base_url/bookInfo/anotherTitle?numberOfPage=0&author=John&isbn=987654321"
echo

# Create a movie info
echo "Creates a movie info successfully"
curl --request POST "$base_url/movieInfo/?genre=aGenre&description=aDescritption&director=Adirector&length=67"
echo 
echo "Fail to create a movie info with empty genre"
curl --request POST "$base_url/movieInfo/?genre=&description=aDescritption&director=Adirector&length=67"
echo

# Create an album info
echo "Creates an album info successfully"
curl --request POST "$base_url/albumInfo/title/?composerPerformer=aComposerPerformer&genre=aGenre"
echo 
echo "Fail to create an album info with no composerPerformer"
curl --request POST "$base_url/albumInfo/title/?composerPerformer=&genre=aGenre"
echo

# Create a newspaper info
echo "Creates a newspaper info successfully"
curl --request POST "$base_url/newsPaperInfo/?publication=2021-12-12&frequency=aFrequency&number=0"
echo
echo "Fail to create a newspaper info with no publication"
curl --request POST "$base_url/newsPaperInfo/?publication=&frequency=aFrequency&number=0"
echo

# Create an archive info
echo "Creates an archive info successfully"
curl --request POST "$base_url/archiveInfo/title/?description=aDescription&publicationDate=2021-12-12"
echo 
echo "Fail to create an archive info with no publicationDate"
curl --request POST "$base_url/archiveInfo/title/?description=aDescription&publicationDate="
echo
