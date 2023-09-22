
## Open Start Menu
type(Key.WIN)
wait(1)
## Input application name Chrome
type('Chrome')
wait(1)
## Press enter
type(Key.ENTER)
wait(1)
## Input google
type('google.com')
wait(5)
## Press enter
type(Key.ENTER)
wait(3)
## Input Key word
type('USA CPI download')
wait(1)
## Confirm as enter key
type(Key.ENTER)
## Wait the page loading
wait(8)
#screen.findText('Consumer Price Index (CPI) Databases : U.S').click();
## Click the CPI link
click("1695115487813")
wait(5)
## Click the image table
click("1695115694999")
## Wait the page loading for 5 seconds
wait(5)
## Find all XLSX files
matches = findAll("matches")
## Download 3 items into local by clicking
count = 1
for x in matches:
    count = count + 1
    click(x)
    wait(2)
    if count > 3 :
        break




