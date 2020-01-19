package com.merapar.xmlanalyzer.xmlanalyzer.controller;


import com.merapar.xmlanalyzer.xmlanalyzer.model.AnalyzedUrl;
import com.merapar.xmlanalyzer.xmlanalyzer.model.ResultAnalysis;
import com.merapar.xmlanalyzer.xmlanalyzer.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
public class AnalysisController {

    private AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }


    @PostMapping(path = "/analyze", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResultAnalysis xmlAnalyze(@RequestBody AnalyzedUrl url) {

        try {
            return analysisService.doAnalysis(url.getUrl());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
