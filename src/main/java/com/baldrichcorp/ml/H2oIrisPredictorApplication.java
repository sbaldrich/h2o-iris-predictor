package com.baldrichcorp.ml;

import com.baldrichcorp.ml.generated.IrisPredictor;
import hex.genmodel.easy.EasyPredictModelWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class H2oIrisPredictorApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2oIrisPredictorApplication.class, args);
	}

	@Bean
	public EasyPredictModelWrapper easyPredictModelWrapper(){
		return new EasyPredictModelWrapper(new IrisPredictor());
	}
}
