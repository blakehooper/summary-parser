## Technical Test

### Outstanding
- Completed very little in the way of structured error handling, a competent error strategy is TODO
- Allowed for the introduction of a validation function to the file parser but the file is currently assumed to be valid
- Added a source set for Functional test, api level testing against spring boot running or a built container but didn't add tests
- Logging omitted due to time constraints
- Versioning strategy

### Clarifications
#### Webflux (Netty)
I've built on-top of webflux but used a very procedural style. i.e. everything runs in the one action.
Reasons I've chosen this style:
- Quick to develop spring container starts in about 1 sec
- Good balance between reactive advantages and style familiarity 
- It felt like a very reactive problem, a phase 2 could be breaking into composable steps and allowing them to run in separate actions, the architecture will support this. Considered under the "no premature optimisation" paradigm
- When talking about orchestrated environments it's good to have some components with diametric resource requirements to help node packing. i.e. Hi RAM / Low CPU vs Hi CPU / Low RAM 

Negatives
- Risky choice for a technical test. Don't want to give the impression I choose technology over best fit for solution (mitigated by cool readme ;-) )
- Not a common choice among developers / resourcing

### Running

Clean build
```bash
./gradlew clean
```

Build application (Run unit tests as dependency of build)
```bash
./gradlew build
```

Run integration tests
```bash
./gradlew integrationTest
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

Stop container (this will remove the stopped container) and clean images so tech tests aren't polluting your machine
```bash
./gradlew dockerStop
docker image rm summary-parser
```

Crazy shell commands to verify first result. There's a fair chance there are conditions which will trick these commands. But I used as a bit of a smoke test to verify my results
```bash
cat application-resources/Input.txt | sed -e '/1234/!d' -e '/0002/!d' -e '/0001/!d' -e '/SGX/!d' -e '/FU/!d' -e '/NK/!d' | cut -c53-63 | paste -sd+ | bc
228
cat application-resources/Input.txt | sed -e '/1234/!d' -e '/0002/!d' -e '/0001/!d' -e '/SGX/!d' -e '/FU/!d' -e '/NK/!d' | cut -c64-73 | paste -sd+ | bc
280
bc -l <<< "228-280"
-52
```