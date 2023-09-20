app_chrome = App('C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe')
app_files = App('C:\\Windows\\explorer.exe')
#app_chrome.open(2)
#popup("Hello World!\nHave fun with Automate tool!")

openApp('Word')

type(Key.WIN)
wait(1)
type('Chrome')
wait(1)
type(Key.ENTER)
wait(1)
type('gogole.com')
wait(5)
type(Key.ENTER)
wait(3)
type('USA CPI download')
wait(1)
type(Key.ENTER)
wait(3)
#screen.findText('Consumer Price Index (CPI) Databases : U.S').click();
click("1695115487813")
wait(5)
click("1695115694999")
wait(2)
matches = findAll("matches")
##print(matches)
count = 0
for x in matches:
    count = count + 1
    click(x)
    wait(2)
    if count > 3 :
        break




