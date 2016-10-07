package com.baldrichcorp.ml.service;

import com.baldrichcorp.ml.domain.Flower;
import com.baldrichcorp.ml.domain.Prediction;
import com.baldrichcorp.ml.domain.Predictor;
import com.baldrichcorp.ml.repository.PredictionRepository;
import com.baldrichcorp.ml.repository.PredictorRepository;
import com.baldrichcorp.ml.web.PredictionRequest;
import com.baldrichcorp.ml.web.PredictionResponse;
import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Santiago Baldrich.
 */
@Service
@Slf4j
public class PredictionService {

    private FeatureService featureService;
    private PredictionRepository predictionRepository;
    private PredictorRepository predictorRepository;


    @Autowired
    public PredictionService(PredictionRepository predictionRepository, FeatureService featureService, PredictorRepository predictorRepository) {
        this.predictionRepository = predictionRepository;
        this.featureService = featureService;
        this.predictorRepository = predictorRepository;
    }

    public PredictionResponse predict(PredictionRequest request) {
        MultinomialModelPrediction multinomialPrediction;
        Prediction prediction;
        Flower flower = request.getFlower();
        Predictor predictor = predictorRepository.findOne(request.getModelId());
        if (predictor == null) {
            prediction = Prediction.builder()
                    .flower(flower)
                    .successful(false)
                    .probability(-1.0)
                    .message("No model found with the given id.")
                    .build();
            log.info("No model predictor with the given id. Can't process request [{}]", request);
        } else {
            Optional<EasyPredictModelWrapper> model = predictor.obtainModel();
            if (model.isPresent()) {
               try {
                    multinomialPrediction = model.get().predictMultinomial(predictor.computeFeatures(featureService.adapt(flower)));
                    prediction = Prediction.builder()
                            .flower(flower)
                            .successful(true)
                            .predictedLabel(multinomialPrediction.label)
                            .probability(multinomialPrediction.classProbabilities[multinomialPrediction.labelIndex])
                            .build();
                   log.info("Performing prediction for request [{}]", request);
                } catch (PredictException ex) {
                    prediction = Prediction.builder()
                            .flower(flower)
                            .successful(false)
                            .probability(-1.0)
                            .message("Unknown Error.")
                            .build();
                   log.error("Unkown error while processing request [{}] {}", request, ex);
               }
            }
            else{
                prediction = Prediction.builder()
                            .flower(flower)
                            .successful(false)
                            .probability(-1.0)
                            .message("Prediction Error.")
                            .build();
                log.error("A predictor was found but no model was generated to process request [{}]", request);
            }
        }
        prediction = predictionRepository.save(prediction);
        return new PredictionResponse(prediction);
    }

}
