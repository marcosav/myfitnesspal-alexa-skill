# myfitnesspal-alexa-skill

Pretty simple Alexa Skill to get your daily food from MyFitnessPal

## Develop

- Use JDK 11 (recommended) or higher
- Tomcat 8.5.81 is recommended
- Checkout `Dockerfile` and [myfitnesspal-alexa-skill-docker](https://github.com/marcosav/myfitnesspal-alexa-skill-docker)

## Configuration

### Environment variables

| Property           | Description                                    | Info/Default value                       |
|--------------------|------------------------------------------------|------------------------------------------|
| `SKILL_ID`         | Alexa Skill ID                                 | *Mandatory*                              |
| `MFP_USERNAME`     | MyFitnessPal Account Username                  | *Mandatory*                              |
| `MFP_PASSWORD`     | MyFitnessPal Account Password                  | *Mandatory*                              |
| `GRAM_ALIASES`     | Aliases of gram (for better understandability) | *Optional* Separated by `,`, i.e: `g,gr` |
| `TIMEZONE`         | Timezone                                       | i.e: `Europe/Madrid`                     |
| `CACHE_LIFESPAN`   | Day meal cache lifespan                        | `0` seconds                              |
| `{meal}_THRESHOLD` | Threshold for getting next day's meal          | *Optional* i.e: `BREAKFAST_THRESHOLD=10` |