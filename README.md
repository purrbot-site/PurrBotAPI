# PurrBotAPI
The PurrBotAPI is an API to create dynamically generated images based on provided values.

## Notes
- Requests need to be set as `application/json` and have to contain a JSON-body (even if empty).
- Leave a field away to use default values.

> **Base URL**:  
> https://purrbot.site/api

## POST /quote
Returns a image that looks similar to a discord message of a user.

| Field      | Type   | Description                                                                 | Default                                         |
| ---------- | ------ | --------------------------------------------------------------------------- | ----------------------------------------------- |
| avatar     | String | The URL of the avatar to display.                                           | https://purrbot.site/assets/img/api/unknown.png |
| dateFormat | String | Format for the date. Can be f.e. `MM/dd/yyyy` or `dd. MM. yyyy`.            | `dd. MMM yyyy hh:mm:ss zzz`                     |
| message    | String | The message of the user.                                                    | `Some message`                                  |
| nameColor  | String | Color of name. Requires either `hex:rrggbb`, `rgb:r,g,b` or an inter value. | `#ffffff`                                       |
| timestamp  | Number | The time as echo time millis.                                               | `<Current time of request>`                     |
| username   | String | Name of the user.                                                           | `Someone`                                       |

### Example JSON request
```json
{
  "avatar": "https://cdn.discordapp.com/avatars/425382319449309197/1bc79c1286f5ef94b721a249bae5336d.png",
  "username": "*Purr*",
  "message": "Hello there!"
}
```

## POST /status
Returns a image of an avatar with an status-icon.

| Field  | Type    | Description                                                                                                                    | Default                                         |
| ------ | ------- | ------------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------- |
| avatar | String  | The URL of the avatar to display.                                                                                              | https://purrbot.site/assets/img/api/unknown.png |
| mobile | Boolean | If the user is on mobile. `true` will change the icon to the one used in Discord to indicate a mobile user (Small smartphone). | `false`                                         |
| status | String  | The status to set as icon. Can be `online`, `idle`, `do_not_disturb` (or `dnd`), `streaming` or `offline`                      | `offline`                                       |

### Example JSON request
```json
{
  "avatar": "https://cdn.discordapp.com/avatars/425382319449309197/1bc79c1286f5ef94b721a249bae5336d.png",
  "status": "online"
}
```

## Errors
The site might return a 403 (Unauthorized) or 500 (Internal Server error) error when the request either contains invalid JSON (or none at all) or an exception happened on creating the image.  
The error is returned as a normal JSON response which may look something like this:  
```json
{
  "code": 403,
  "message": "Invalid or empty JSON-body received!"
}
```

## Additional links
* [PurrBot](https://github.com/Andre601/PurrBot)
* [Wiki](https://docs.purrbot.site/bot)
