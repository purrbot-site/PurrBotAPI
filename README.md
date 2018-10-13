# PurrBotQuoter
**PurrBotQuoter** is a api made, to create images, that look like being directly taken from Discord itself!  
It honestly was meant as a "proof of concept" (Profing, that I don't completely suck at java xD)

You can simply connect to https://purrbot.site/api/quote and you'll see this image here as an example:

## Params
You can provide additional params through the link.  
The syntax is: `?param1=something&param2=something`.

The following params are available for use:

### text
**Syntax**: `text=<text text text ...>`  
**Requires**: String

The provided text will be used in the image as the message.  
It will be auto-split to a new line, if the message contains `\n` or just is longer than the width of the available space

### name
**Syntax**: `name=<text>`  
**Requires**: String

Will be used as the Name in the image.

### avatar
**Syntax**: `avatar=<https://link.com/image.png>`  
**Requires**: A valid image-link (Should end with .png, .jpeg, .svg, etc.)

Uses the provided link as avatar. The image will be resized to a size of 290x290 pixels.

### time
**Syntax**: `time=<time in milliseconds>`  
**Requires**: Long

Converts the given time (in milliseconds) to the actual date.  
Example: `1537286684064` would be `18. september 2018 16:04:44 UTC`

### format
**Syntax**: `format=<supported date-format>`  
**Requires**: Valid String (date-format)

Lets you change the format/syntax of the date.  
You can use `s` for seconds, `m` for minutes, `H` for hours, `d` for days, `M` for months, `y` for years and `z` for the timezone.
Depending on how many letters you use, can it also change the look of the date.  
Example: `dd. MMM yyyy` would return `18. Sep 2018` while `dd.MM.yyyy` would return `18.09.2018`

### color
**Syntax**: `color=<integer>`  
**Requires**: Valid integer-value

This lets you change the color of the Name.  
It requires a valid integer-value. (f.e. `1358564` for the hex-color `#14bae4`)

## How it can be used
**It can be used**:
- to download the image.
- to show a image-preview in Discord.

**It can NOT be used**:
- As a valid image-link (To show it in embeds f.e.)
