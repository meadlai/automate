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

If I tell you: input or type 'word', you should return
<code-line>type("word")</code-line>
<code-line>wait(1)</code-line>

If I tell you: write 'Excel', you should return
<code-line>type("Excel")</code-line>
<code-line>wait(1)</code-line>

If I tell you: open the context menu "16956.png", you should return
<code-line>rightClick(‘16956.png’)</code-line>
<code-line>wait(1)</code-line>

If I tell you: wait a second, you should return
<code-line>wait(1)</code-line>

If I tell you: wait 3 second, you should return
<code-line>wait(3)</code-line>

Here's a Python script that implements the desired functionality:\n\n```python\ndef doubleClick(image):\n    print(f'doubleClick(\"{image}\")')\n    print('wait(1)')\n\ndef rightClick(image):\n    print(f'rightClick(\"{image}\")')\n    print('wait(1)')\n\ndef click(image):\n    print(f'click(\"{image}\")')\n    print('wait(1)')\n\ndef wait(seconds):\n    print(f'wait({seconds})')\n\ndef type(text):\n    print(f'type(\"{text}\")')\n    print('wait(1)')\n\ndef translate_statement(statement):\n    if statement.startswith('I want to click'):\n        image = statement.split(\"'\")[1]\n        print(f'click(\"{image}\")')\n        print('wait(1)')\n    elif 'double click' in statement or 'open' in statement:\n        image = statement.split(\"'\")[1]\n        print(f'doubleClick(\"{image}\")')\n        print('wait(1)')\n    elif statement.startswith('input') or statement.startswith('type'):\n        text = statement.split(\"'\")[1]\n        print(f'type(\"{text}\")')\n        print('wait(1)')\n    elif statement.startswith('write'):\n        text = statement.split(\"'\")[1]\n        print(f'type(\"{text}\")')\n        print('wait(1)')\n    elif statement.startswith('open the context menu'):\n        image = statement.split(\"'\")[1]\n        print(f'rightClick(\"{image}\")')\n        print('wait(1)')\n    elif statement.startswith('wait'):\n        seconds = statement.split('wait')[-1].strip().split()[0]\n        print(f'wait({seconds})')\n\n# Example usage\ntranslate_statement(\"I want to click '10548.png'\")\ntranslate_statement(\"double click '65396.png'\")\ntranslate_statement(\"open '65396.png'\")\ntranslate_statement(\"input 'word'\")\ntranslate_statement(\"write 'Excel'\")\ntranslate_statement(\"open the context menu '16956.png'\")\ntranslate_statement(\"wait a second\")\ntranslate_statement(\"wait 3 second\")\n```\n\nThis script defines the functions `doubleClick`, `rightClick`, `click`, `wait`, and `type` to print the desired function calls and waits. The `translate_statement` function takes a statement as input and translates it into the corresponding function calls and waits. The example usage at the end demonstrates how to use the `translate_statement` function with different statements.

If I tell you: open '65396.png', you should return
  <code-line>doubleClick(\"65396.png\")</code-line>
  <code-line>wait(1)</code-line>
  
If I tell you: right click the 'mead.png', you should return
  <code-line>rightClick(\"mead.png\")</code-line>
  wait(1)</code-line>
  
If I tell you: i want to open Word, you should return
  <code-line>doubleClick(\"Word\")</code-line>
  <code-line>wait(1)</code-line>
  
If I tell you: open chrome, you should return
  <code-line>doubleClick(\"chrome\")</code-line>
  <code-line>wait(1)</code-line>

Please don't replace anything in the quotation.

Everything you return, should be wrapped with xml tag <code-line>
