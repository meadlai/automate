#app_chrome = App('C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe')
#app_chrome.open(2)
## Open Start Menu
type(Key.WIN)
wait(1)
## Input application name outlook
type('Outlook')
wait(1)
## Press enter
type(Key.ENTER)
wait(5)
## Create a new email
type("n", Key.CTRL)
wait(2)
## Move this to left of the window
type(Key.LEFT, Key.WIN)
wait(2)
## Open a new windows explorer
type("e", Key.WIN)
wait(2)
## Go to the download folder with image "download_image_name"
click("1695174481325")
wait(2)
## Move this window to right side
type(Key.RIGHT, Key.WIN)
wait(1)
## select all files in this folder
type("a", Key.CTRL)
wait(1)
## get the location of "xfrom", set the variable name as xfrom of find result
## set the variable name as xfrom_location of the location, if the "xfrom" follows the Python name convention
## unless, set the variable name with prefix "var_"
xfrom = find("1695175577831")
xfrom_location = Location(xfrom.getX(), xfrom.getY())
## get the location of "target", set the variable name as target
## set the location vaiable name as target_location
var_target = find("target")
var_target_location = Location(var_target.getX(), var_target.getY())
## get the location of "30556", since the "30556" doesn't follow the  Python name convention
## it will set the variable name as var_30556 and set the location name as var_30556_location
## var_30556 = find("30556")
## var_30556_location = Location(var_30556.getX(), var_30556.getY())

## drag the item xfrom amd drop it to target
dragDrop(xfrom_location, var_target_location)
##
wait(1)

## click the right side of "to_image", if there is no number in the statement, it should return 50
location = find("email_to").right(50)
click(location);

## click the right side of "30557_image" with 80 offset to right
## since Python's variable can't start with number 30557_image, we add var_ as prefix
## var_30557_image_location = find("30557_image").right(80)
## click(var_30557_image_location);

## click the left side of "from_image" with 100 offset to right
##  from_image_location = find("from_image").right(80)
## click(from_image_location);

#absolution location in the screen
#click(Location(230, 268))
type("meadlai@qq.com")
type(Key.ENTER)
wait(1)
click(Location(245, 345))
wait(1)
type('Latest CPI Files')
wait(1)
click(Location(62, 512))
wait(1)
type('Hello Boss,\n\r Attached is the latest files of Consumer Price Index Supplemental Files. \n\r :)')
wait(1)
click("1695180849998")
wait(1)
popup('All Set for you, have a good day!','Great')




