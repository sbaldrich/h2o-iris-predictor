library(h2o)
library(dplyr)

h2o.init(nthreads = -1)
train.data <- iris %>% 
                mutate(Length.Sum = Petal.Length + Sepal.Length) %>% 
                rename(petal_wid = Petal.Width, 
                       petal_len = Petal.Length,
                       length_sum = Length.Sum,
                       sepal_len = Sepal.Length,
                       sepal_wid = Sepal.Width) %>% 
                as.h2o
iris.gbm = h2o.randomForest(y = 5, x = c(1,2,3,4,6), training_frame = train.data, ntrees = 50, max_depth = 100, model_id = "GBMIrisPredictor")

print(iris.gbm)

if (! file.exists("tmp")) {
        dir.create("tmp")
}
h2o.download_pojo(iris.gbm, path = "tmp")