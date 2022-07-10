## Technical Test

### Outstanding
- Completed very little in the way of structured error handling, a competent error strategy is TODO
- Allowed for the introduction of a validation function to the file parser but the file is currently assumed to be valid
- Added a source set for Functional test, api level testing against spring boot running or a built container but didn't add tests
- Logging omitted due to time constraints

### Clarifications
#### Webflux (Netty)
I've built on-top of webflux but used a very procedural style. i.e. everything runs in the one action.
Reasons I've chosen this style:
- Quick to develop spring container starts in about 1 sec
- Good balance between reactive advantages and style familiarity 
- It felt like a very reactive problem, a phase 2 could be breaking into composable steps and allowing them to run in separate actions, the architecture will support this. Considered under the "no premature optimisation" paradigm
- When talking about orchestrated environments it's good to have some components with diametric resoruce requirements to help node packing. I.e. Hi RAM / Low CPU vs Hi CPU / Low RAM 

Negatives
- Risky choice for a technical test. Don't want to give the impression I choose technology over best fit for solution (mitigated by cool readme ;-) )
- Not a common choice among developers / resourcing

### Running

Clean build
```bash
./gradlew clean
```

Build application
```bash
./gradlew build
```

Build container
```bash
./gradlew docker
```

Run container
```bash
./gradlew dockerRun
```

Check running container status
```bash
./gradlew dockerRunStatus
```

Execute test request
```bash
curl http://localhost:8080/summary | jq
```

Output.json generated with
```bash
curl http://localhost:8080/summary | jq > Output.json
```