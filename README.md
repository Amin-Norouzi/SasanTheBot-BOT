## SasanTheBot - Core Bot
##### Uses Sasan private APIs (v1)
##### Connects to Sasan website and MovieStalker app

<br>
<br>

## Bot v1.0 is available now!

bot flow: v2.0
- home stage (/start | /start?1234)
  - checks if user is logged in or not 

    - 1- if not, sends the signup/login message to user
    - or else, sends the welcome message with main keyboard
    
  - if message contains download id, it will switch the stage to download
  

- search stage (any message besides /start or /dl_1234)
  - first sends a request to search service the returns the result to user as a message
  - user can use download command here


- download stage (/dl_1234)
  - sends a search info first then try to fetch download links an update the message 
    - 0- it constantly updates message to inform user about download progress 
    - 1- if user already has downloaded the search, it just will return the download link else it also will add the download to use account 
    - 2- it cannot find any download links and just updates the message with a proper info
  - user can add the search to his/her account (NOT IN THIS VERSION)
    - 1- if search was not already added to user's library, it will be added and bot just returns a notification
    - 2- or if the searches was already added to user's library then bot will return a error notification


- account/settings stage (callback-account)
  - returns user downloads in inline query mode
  - returns user searches in inline query mode
  - connects to moviestalker app (soon)


- contact stage (callback-contact)
  - simply just open a url to contact section in website