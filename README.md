# PurrBotQuoter
**PurrBotQuoter** is a api made, to create images, that look like being directly taken from Discord itself!  
To use it, simply connect to https://purrbot.site/api/quote to see an example-image.

## Params
You can provide additional params through the link.  
The syntax is: `?param1=something&param2=something`.

The following params are available for use:

### Text
**Syntax**: `text=<text text text ...>`  
**Requires**: String

The provided text will be used in the image as the message.  
It will be auto-split to a new line, if the message contains `\n` or just is longer than the width of the available space

### Name
**Syntax**: `name=<text>`  
**Requires**: String

Will be used as the Name in the image.

### Avatar
**Syntax**: `avatar=<https://link.com/image.png>`  
**Requires**: A valid image-link (Should end with .png, .jpeg, .svg, etc.)

Uses the provided link as avatar. The image will be resized to a size of 290x290 pixels.

### time
**Syntax**: `time=<time in milliseconds>`  
**Requires**: Long

Converts the given time (in milliseconds) to the actual date.  
Example: `1537286684064` would be `18. september 2018 16:04:44 UTC`

### Format
**Syntax**: `format=<supported date-format>`  
**Requires**: Valid String (date-format)

Lets you change the format/syntax of the date.  
You can use `s` for seconds, `m` for minutes, `H` for hours, `d` for days, `M` for months and `y` for years.  
Depending on how many letters you use, can it also change the look of the date.  
Example: `dd. MMM yyyy` would return `18. Sep 2018` while `dd.MM.yyyy` would return `18.09.2018`

### Color
**Syntax**: `color=<#rrggbb>`
**Requires**: Valid hex-color

With that can you change the color of the displayed name.
The color-format is in hexadecimal (#rrggbb). The # can be optional.

## How it can be used
The link can NOT be used as a direct image-link.  
You can however use it, to download the image and then f.e. upload it in a chat.  
Support as direct-image may be added in the future, but I do not promise anything...
