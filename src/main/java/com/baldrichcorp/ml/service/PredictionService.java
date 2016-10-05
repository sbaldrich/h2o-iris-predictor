package com.baldrichcorp.ml.service;

import com.baldrichcorp.ml.repository.PredictionRepository;
import com.baldrichcorp.ml.domain.Flower;
import com.baldrichcorp.ml.domain.Prediction;
import com.baldrichcorp.ml.web.PredictionRequest;
import com.baldrichcorp.ml.web.PredictionResponse;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Santiago Baldrich.
 */
@Service
public class PredictionService {

    private ModelSelectionService modelSelectionService;
    private FeatureService featureService;
    private PredictionRepository predictionRepository;


    @Autowired
    public PredictionService(ModelSelectionService modelSelectionService, PredictionRepository predictionRepository, FeatureService featureService) {
        this.modelSelectionService = modelSelectionService;
        this.predictionRepository = predictionRepository;
        this.featureService = featureService;
    }

    public PredictionResponse predict(PredictionRequest request) {
        MultinomialModelPrediction multinomialPrediction;
        Prediction prediction;
        Flower flower = request.getFlower();
        EasyPredictModelWrapper predictor = modelSelectionService.obtainModel(request.getModelId());
        try {
            multinomialPrediction = predictor.predictMultinomial(featureService.extractFeatures(flower));
            prediction = Prediction.builder()
                    .flower(flower)
                    .successful(true)
                    .predictedLabel(multinomialPrediction.label)
                    .probability(multinomialPrediction.classProbabilities[multinomialPrediction.labelIndex])
                    .build();
        } catch (PredictException ex) {
            prediction = Prediction.builder()
                    .flower(flower)
                    .successful(false)
                    .probability(-1.0)
                    .build();
        }
        prediction = predictionRepository.save(prediction);
        return new PredictionResponse(prediction);
    }

}
