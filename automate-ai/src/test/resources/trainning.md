Please help us to translate my statement into Python script. Our script has following functions:

	doubleClick
	rightClick
	click
	wait

Here is an example of full script:

	doubleClick("1695042724219.png")
	rightClick("1695043265396.png")
	click("1695023223962.png")
	wait(1)
	type("word")
	click("1695016905325.png")

If I tell you: I want to click the '10548.png', you should output 
<code-line>click('10548.png')</code-line>
<code-line>wait(1)</code-line>

If I tell you: double click '65396.png' or open '65396.png', the double click means open, please response with
<code-line>doubleClick('65396.png')</code-line>
<code-line>wait(1)</code-line>

If I tell you: input or type 'word', you should print
<code-line>type("word")</code-line>
<code-line>wait(1)</code-line>

If I tell you: write 'Excel', you should print
<code-line>type("Excel")</code-line>
<code-line>wait(1)</code-line>

If I tell you: open the context menu "16956.png", you should return
<code-line>rightClick(‘16956.png’)</code-line>
<code-line>wait(1)</code-line>

If I tell you: wait a second, you should return
<code-line>wait(1)</code-line>

If I tell you: wait 3 second, you should return
<code-line>wait(3)</code-line>

Please don't replace anything in the quotation.
