package com.baldrichcorp.ml;

import com.baldrichcorp.ml.domain.Predictor;
import com.baldrichcorp.ml.repository.PredictorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@EnableCaching
@SpringBootApplication
public class H2oIrisPredictorApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2oIrisPredictorApplication.class, args);
    }

    @Service
    static class DatabaseLoader {
        @Autowired
        PredictorRepository repository;

        @PostConstruct
        void initDatabase() {
            InputStream rfis = this.getClass().getResourceAsStream("/RFIrisPredictor.jar");
            InputStream gbmis = this.getClass().getResourceAsStream("/GBMIrisPredictor.jar");
            try {
                System.out.println("Saving Random Forest predictor");
                byte[] data = FileCopyUtils.copyToByteArray(rfis);
                Predictor predictor = new Predictor("com.baldrichcorp.ml.generated.RFIrisPredictor", "RFIrisPredictor", data, Arrays.asList("sepal_width","sepal_length"));
                repository.save(predictor);
                System.out.println("Saving Gradient Boosting predictor");
                data = FileCopyUtils.copyToByteArray(gbmis);
                predictor = new Predictor("com.baldrichcorp.ml.generated.GBMIrisPredictor", "GBMIrisPredictor", data, Arrays.asList("sepal_length"));
                repository.save(predictor);
            } catch (IOException ex) {
                System.err.println("Sorry :(");
            }
        }
    }


}
