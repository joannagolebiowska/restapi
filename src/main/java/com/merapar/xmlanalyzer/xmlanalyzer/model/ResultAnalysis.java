package com.merapar.xmlanalyzer.xmlanalyzer.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ResultAnalysis {

    private Date analyseDate;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @Autowired
    Details details;

    public Date getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(Date analyseDate) {
        this.analyseDate = analyseDate;
    }


}
