## Iris Predictor

A quick and dirty implementation of a Boot-based web service that uses H2O to make predictions using the [Iris data set](https://en.wikipedia.org/wiki/Iris_flower_data_set).
The H2O Random Forest is trained using a simple R script (`predictor.R`); so yeah, you need to have `R` installed and on
your PATH to get this project running.

## Build
Run `gradle build` to run the R script that creates the Random Forest classifier and sets it up inside the `src` folder.
All this is accomplished using custom gradle tasks. Look at the `build.gradle` file to see how this is accomplished.


## Usage
Run `gradle bootRun` to start the embedded Tomcat server. `POST` JSON requests to get predictions that can be queried using
spring data rest.

#### Example prediction request
```
    curl -X POST -H "Content-Type: application/json" -d '{
        "sepalLength" : 6.9,
        "sepalWidth" : 3.1,
        "petalLength" : 14.9,
        "petalWidth" : 1.5
    }' "http://localhost:8080/predict"
```
#### Example database query request
```
    curl -X GET -H "Content-Type: application/json" "http://localhost:8080/predictions"
```

### References

* [H2O consumer loan sample](https://github.com/h2oai/app-consumer-loan)
* [H2O consumer loan sample presentation](https://www.youtube.com/watch?v=jSN2y6j0Mxk)
* [Spring boot reference](http://docs.spring.io/spring-boot/docs/current/reference/html/)