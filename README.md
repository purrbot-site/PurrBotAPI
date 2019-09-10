# PurrBotAPI
The PurrBotAPI is used through a basic POST requests.

## Important!
* The request body has to be `application/json`
* All fields need to be set, even if you don't use it.  
Just set the value to `""` for default.

> **Base URL**:  
> https://purrbot.site/api

## POST /quote
Returns a image that looks similar to a discord message of a user.

Field  | Description                                                                |
------ | -------------------------------------------------------------------------- |
avatar | The avatar of the user                                                     |
color  | Color of name. Requires either `hex:rrggbb`, `rgb:r,g,b` or an inter value |
format | Format for the date. Can be f.e. MM-dd-yyyy or dd MMM yyyy                 |
name   | Name of the user                                                           |
text   | The message of the user                                                    |
time   | The time as echo time milis                                                |

## POST /status
Returns a image of an avatar with an status-icon.

Field  | Type           | Description                                                                  |
------ | -------------- | ---------------------------------------------------------------------------- |
avatar | URL            | The avatar of the user                                                       |
status | String         | The status for the icon. Can be online, idle, do_not_disturb, dnd or offline |                                                                  |

## Additional links
* [PurrBot](https://github.com/Andre601/PurrBot)
* [Wiki](https://github.com/Andre601/PurrBot/wiki)
