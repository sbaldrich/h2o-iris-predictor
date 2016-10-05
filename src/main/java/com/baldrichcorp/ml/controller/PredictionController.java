package com.baldrichcorp.ml.controller;

import com.baldrichcorp.ml.domain.Flower;
import com.baldrichcorp.ml.service.PredictionService;
import com.baldrichcorp.ml.web.PredictionRequest;
import com.baldrichcorp.ml.web.PredictionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Santiago Baldrich.
 */
@RestController
public class PredictionController {

    private PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService){
        this.predictionService = predictionService;
     }

    @RequestMapping(value = "predict", method = RequestMethod.POST)
    public @ResponseBody
    PredictionResponse predict(@RequestBody PredictionRequest request){
        return predictionService.predict(request);
    }
}
