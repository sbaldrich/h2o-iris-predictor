library(h2o)

h2o.init(nthreads = -1)
iris.hex = h2o.uploadFile(path = system.file("extdata", "iris_wheader.csv", package="h2o"), destination_frame = "iris.hex")
iris.rf = h2o.randomForest(y = 5, x = c(1,2,3,4), training_frame = iris.hex, ntrees = 50, max_depth = 100, model_id = "IrisPredictor")
print(iris.rf)

if (! file.exists("tmp")) {
        dir.create("tmp")
}
h2o.download_pojo(iris.rf, path = "tmp")