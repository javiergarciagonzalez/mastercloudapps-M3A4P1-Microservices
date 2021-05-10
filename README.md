# Assignment 1 - Microservices
## Master Cloud Apps

### Explanation for the teacher

This repository only contains: 
- Product service working within the given microservices existing architecture.
- Requested E2E tests for: 
    - Successful order
    - Failure order due to insufficient credit
    - Failure order due to insufficient stock

**Disclaimer**: API Gateway has not been improved to also take products in count.

### Install

1. Clone this project

2. Go to eventuateTramApp folder
``` sh
cd eventuateTramApp
```

3. Run
``` sh
./gradlew assemble
```

4. Configure Docker Host IP
``` sh
sudo ifconfig lo0 alias 10.200.10.1/24  # (where 10.200.10.1 is some unused IP address)
export DOCKER_HOST_IP=10.200.10.1
```

5. Run
``` sh
./gradlew mysqlComposeBuild
```

and then run:
``` sh
./gradlew mysqlComposeUp
```


### Tests
For this assignments, 3 new test cases have been added to:
`end-to-end-test/src/test/java/io.eventuate.examples.tram.sagas.ordersandcustomers.endtoendtests`

Feel free to execute them once the previous `gradlew` command have been succesfully completed, by running:
``` sh
./gradlew endToEndTests
```

The new tests added for this project can be found at:
`eventuateTramApp/end-to-end-tests/src/test/java/io/eventuate/examples/tram/sagas/ordersandcustomers/endtoendtests/AssignmentTests.java`
