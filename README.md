# myfitnesspal-alexa-skill

Pretty simple Alexa Skill to get your daily food from MyFitnessPal.
This project makes use of [this Java myfitnesspal API](https://github.com/marcosav/myfitnesspal-api).

## Getting started

Create an Alexa Skill [here](https://developer.amazon.com/alexa/console/ask) (you may need a developer account),
then create a new `Custom` skill and select `Provision your own` backend resources.

Once is created (it will take some minutes to completely set up), import the provided example model `mode.json`
on the `left side section` > `Intents` > `JSON Editor` > `Drag and drop a .json file` > Select the previous file > `Save Model`.

Then you just have to customize the existing slot types to your preference.

## Develop

- Use JDK 11 (recommended) or higher
- Tomcat 8.5.81 is recommended
- Checkout `Dockerfile` and [myfitnesspal-alexa-skill-docker](https://github.com/marcosav/myfitnesspal-alexa-skill-docker)

## Configuration

### Meals YAML

By default, a `meals.yml` config file will be created with an example meal set, each meal **must** have
a `name` and `action` keys, and optionally a `threshold` one.

| Property    | Description                                                               | Info/Default value                                                                                          |
|-------------|---------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| `name`      | MFP Meal                                                                  | *Mandatory* (**Must match exactly the Alexa Skill's Slot VALUE & MFP meal name**) i.e. `Lunch`, `Dinner`... |
| `action`    | Action used to communicate that meal action (check `messages.properties`) | *Mandatory* i.e. `eating`, `comer`...                                                                       |
| `threshold` | Hour since skill will check for the next day's meal                       | *Optional* **Must be an integer** i.e. `10`, `18`...                                                        |

### Environment variables

General configuration.

| Property           | Description                                    | Info/Default value                       |
|--------------------|------------------------------------------------|------------------------------------------|
| `SKILL_ID`         | Alexa Skill ID                                 | *Mandatory*                              |
| `MFP_USERNAME`     | MyFitnessPal Account Username                  | *Mandatory*                              |
| `MFP_PASSWORD`     | MyFitnessPal Account Password                  | *Mandatory*                              |
| `GRAM_ALIASES`     | Aliases of gram (for better understandability) | *Optional* Separated by `,`, i.e: `g,gr` |
| `TIMEZONE`         | Timezone                                       | i.e: `Europe/Madrid`                     |
| `CACHE_LIFESPAN`   | Day meal cache lifespan                        | `0` seconds                              |