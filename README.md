# PurrBotAPI
The **PurrBotAPI** provides different kinds of image-apis that can be used for different kinds of things.  
There are currently 3 API-endpoints available, that can be used.
All three APIs can be accessed by connecting to `https://purrbot.site/api/<endpoint>` and do also support parameters in the link.

The parameter-syntax is always `https://purrbot.site/api/<endpoint>?param=<value>&param=<value>&param=<value>&...`

You can connect to https://purrbot.site/api to see all available endpoints with all available parameters.

**Notes**: This API isn't perfect and was more meant as a "proof of concept"... to proof I'm not a completely bad developer xD

## Quote
**Link**: https://purrbot.site/api/quote

This endpoints allows you to create a image, that - more or less - looks like a message from discord.  
You can provide different params to change the image.

The following params can be used:

### avatar
**Syntax**: `avatar=<https://link.com/image.png>`
**Requires**: A valid image-link (Should end with .png, .jpeg, .svg, etc.)

Uses the provided link as avatar. The image will be resized to a size of 217x217 pixels.

### color
**Syntax**: `color=<rgb:r,g,b|hex:rrggbb|integer>`
**Requires**: Valid rgb, hex or integer value

This lets you change the color of the Name.
You can use a hex, rgb or integer-value to define it.
Hex and rgb colors need to be prefixed with either `hex:` or `rgb:`, followed by the value.
* `hex` is a hexadecimal value (without the #). Example: `hex:ff0000`
* `rgb` is a comma-seperated value. Example: `rgb:255,0,0`
* The integer is the "raw" color value (calculated in java). Example: `1358564` for the hex-color `#14bae4`

### format
**Syntax**: `format=<supported date-format>`
**Requires**: Valid String (date-format)

Lets you change the format/syntax of the date.
You can use `s` for seconds, `m` for minutes, `H` for hours, `d` for days, `M` for months, `y` for years and `z` for the timezone.
Depending on how many letters you use, can it also change the look of the date.
Example: `dd. MMM yyyy` would return `18. Sep 2018` while `dd.MM.yyyy` would return `18.09.2018`

### name
**Syntax**: `name=<text>`
**Requires**: String

Will be used as the Name in the image.

### text
**Syntax**: `text=<text text text ...>`  
**Requires**: String

The provided text will be used in the image as the message.  
It will be auto-split to a new line, if the message contains `\n` or just is longer than the width of the available space.

### time
**Syntax**: `time=<time in milliseconds>`  
**Requires**: Long

Converts the given time (in milliseconds) to the actual date.  
Example: `1537286684064` would be `18. september 2018 16:04:44 UTC`

----
## Status
**Link**: https://purrbot.site/api/status

This returns an image of the avatar, with a status-icon.  
The default status is offline and the image width and height are 950px.

Following params are supported:

### avatar
**Syntax**: `avatar=<https://link.com/image.png>`  
**Requires**: A valid image-link (Should end with .png, .jpeg, .svg, etc.)

Change the image that is used as avatar.  
The image will be resized to 950x950px.

### status
**Syntax**: `status=<String>`  
**Requires**: Valid online-status (As String)

Changes, what status-icon is shown on the bottom right of the image.  
Supported statuses are: `ONLINE`, `IDLE`, `DO_NOT_DISTURB`/`DND` or `OFFLINE`.
If you set a invalid status (or none at all), then it will default to `OFFLINE`.

----
## Welcome
**Link**: https://purrbot.site/api/welcome

Returns a welcome image with avatar, username, guildcount and a background.
Default is `purr` as background.

### avatar
**Syntax**: `avatar=<https://link.com/image.png>`
**Requires**: A valid image-link (Should end with .png, .jpeg, .svg, etc.)

Change the image that is used as avatar.
The image will be resized to 290x290px.

### color
**Syntax**: `color=<rgb:r,g,b|hex:rrggbb|integer>`
**Requires**: Valid rgb, hex or integer value

Changes the text color.
You can use a hex, rgb or integer-value to define it.
Hex and rgb colors need to be prefixed with either `hex:` or `rgb:`, followed by the value.
* `hex` is a hexadecimal value (without the #). Example: `hex:ff0000`
* `rgb` is a comma-seperated value. Example: `rgb:255,0,0`
* The integer is the "raw" color value (calculated in java). Example: `1358564` for the hex-color `#14bae4`

### image
**Syntax**: `image=<string>`
**Requires**: Valid String (image-name)

Changes the background of the image.
A list of available images can be found [here](https://github.com/Andre601/PurrBot/wiki/Welcome-images).

### name
**Syntax**: `name=<text>`
**Requires**: String

Will be used as the Name in the image.

### size
**Syntax**: `size=<number>`
**Requires**: Integer

Changes the number in `You're user #<number>`

----
## How the links can be used
**It can be used**:
- to download the image.
- to show a image-preview in Discord.

**It can NOT be used**:
- As a valid image-link (At least not directly... There are ways, to f.e. use it in embeds, but it can be complicated.)
