# PurrBotAPI
The PurrBotAPI is an API to create dynamically generated images based on provided values.

## Notes
- Requests need to be set as `application/json` and have to contain a JSON-body (even if empty).
- Leave a field away to use default values.

> **Base URL**:  
> https://purrbot.site/api

## POST /quote
Returns a image that looks similar to a discord message of a user.

| Field      | Description                                                                | Default                                         |
| ---------- | -------------------------------------------------------------------------- | ----------------------------------------------- |
| avatar     | The avatar of the user                                                     | https://purrbot.site/assets/img/api/unknown.png |
| nameColor  | Color of name. Requires either `hex:rrggbb`, `rgb:r,g,b` or an inter value | `#ffffff`                                       |
| dateFormat | Format for the date. Can be f.e. `MM/dd/yyyy` or `dd. MM. yyyy`            | `dd. MMM yyyy hh:mm:ss zzz`                     |
| username   | Name of the user                                                           | `Someone`                                       |
| message    | The message of the user                                                    | `Some message`                                  |
| timestamp  | The time as echo time millis                                               | `<Current time of request>`                     |

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

| Field  | Type    | Description                                                                                             | Default                                         |
| ------ | ------- | ------------------------------------------------------------------------------------------------------- | ----------------------------------------------- |
| avatar | URL     | The avatar of the user.                                                                                 | https://purrbot.site/assets/img/api/unknown.png |
| status | String  | The status for the icon. Can be `online`, `idle`, `do_not_disturb` (or `dnd`) or `offline`.             | `offline`                                       |
| mobile | Boolean | If the user is on mobile. This will change the icon to the one used on Discord to indicate mobile user. | `false`                                         |

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
