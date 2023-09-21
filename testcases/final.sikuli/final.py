wait(1)
popup("Going to do task, please don't move mouse and press keyboard, thank you!","Hello")

## Press WIN
type(Key.WIN)
wait(1)
## Open Chrome
type('Chrome')
type(Key.ENTER)
wait(1)
type('gogole.com')
wait(2)
type(Key.ENTER)
wait(8)
## Input keyword
type('USA CPI download')
wait(1)
type(Key.ENTER)
wait(3)
## Click 'Consumer Price Index (CPI) Databases : U.S'
click("cpi_link")
wait(5)
## Click tables
click("tables")
wait(2)
## Find all XLSX
matches = findAll("matches")
##Download 4 files
count = 0
for x in matches:
    count = count + 1
    click(x)
    wait(2)
    if count > 3 :
        break

## Open Outlook
type(Key.WIN)
wait(1)
type('Outlook')
wait(2)
type(Key.ENTER)
wait(8)
## Create new mail
type("n", Key.CTRL)
wait(2)
## Move to left
type(Key.LEFT, Key.WIN)
wait(2)
## Open File Explorer
type("e", Key.WIN)
wait(2)
## Open the folder 'Download'
click("download")
wait(2)
type(Key.RIGHT, Key.WIN)
wait(1)
## Select All files
type("a", Key.CTRL)
wait(1)
## Get source location
xfrom = find("excel")
xfrom_location = Location(xfrom.getX(), xfrom.getY())
## Get target location
xtarget = find("target")
xtarget_location = Location(xtarget.getX(), xtarget.getY())
## Drag and Drop
dragDrop(xfrom_location, xtarget_location)
##
wait(1)
## Input recipient
location = find("email_to").right(50)
click(location);
type("meadlai@qq.com")
type(Key.ENTER)
wait(1)
#absolution location in the screen
## Input subject
click(Location(245, 345))
wait(1)
type('Latest CPI Files')
wait(1)
## Input mail Body
click(Location(62, 512))
wait(1)
type('Hello Boss,\n\r Attached is the latest files of Consumer Price Index Supplemental Files. \n\r :)')
wait(1)
## Send email
click("send")
wait(1)
popup('All Set for you, have a good day!','Great')







