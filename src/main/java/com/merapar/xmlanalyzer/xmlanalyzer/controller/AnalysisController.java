package com.merapar.xmlanalyzer.xmlanalyzer.controller;


import com.merapar.xmlanalyzer.xmlanalyzer.model.AnalyzedUrl;
import com.merapar.xmlanalyzer.xmlanalyzer.model.ResultAnalysis;
import com.merapar.xmlanalyzer.xmlanalyzer.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

@RestController
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;


    @PostMapping(path="/analyze", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResultAnalysis xmlAnalyze(@RequestBody AnalyzedUrl url) throws SAXException, ParserConfigurationException, IOException, ParseException {
        return analysisService.doAnalysis(url.getUrl());
    }

}
