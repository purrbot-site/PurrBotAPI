# PurrBotAPI
The PurrBotAPI is used through a basic HTTPS/REST API to receive the images.

> **Base URL**:  
> https://purrbot.site/api

## GET /quote
Returns a image that looks similar to a discord message of a user.

Field  | Type           | Description                                                                |
------ | -------------- | -------------------------------------------------------------------------- |
avatar | URL            | The avatar of the user                                                     |
color  | String/Integer | Color of name. Requires either `hex:rrggbb`, `rgb:r,g,b` or an inter value |
format | String         | Format for the date. Can be f.e. MM-dd-yyyy or dd MMM yyyy                 |
name   | String         | Name of the user                                                           |
text   | String         | The message of the user                                                    |
time   | Integer        | The time as echo time milis                                                |

## GET /status
Returns a image of an avatar with an status-icon.

Field  | Type           | Description                                                                  |
------ | -------------- | ---------------------------------------------------------------------------- |
avatar | URL            | The avatar of the user                                                       |
status | String         | The status for the icon. Can be online, idle, do_not_disturb, dnd or offline |

## GET /welcome
Returns a welcome image, which is normally used by \*Purr*

Field  | Type           | Description                                                                  |
------ | -------------- | ---------------------------------------------------------------------------- |
avatar | URL            | The avatar of the user                                                       |
color  | String/Integer | Text color. Needs to be either `hex:rrggbb`, `rgb:r,g,b` or an inter value   |
image  | String         | Type of the image. Check the [image-list] for a all available images         |
name   | String         | Name of the user                                                             |
size   | Integer        | Guild count                                                                  |

## Additional links
[image-list]: https://github.com/Andre601/PurrBot/wiki/Welcome-images
* [PurrBot](https://github.com/Andre601/PurrBot)
* [Wiki](https://github.com/Andre601/PurrBot/wiki)
