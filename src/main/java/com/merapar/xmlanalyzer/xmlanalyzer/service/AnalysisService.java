package com.merapar.xmlanalyzer.xmlanalyzer.service;


import com.merapar.xmlanalyzer.xmlanalyzer.model.Details;
import com.merapar.xmlanalyzer.xmlanalyzer.model.ResultAnalysis;
import com.merapar.xmlanalyzer.xmlanalyzer.utils.DateFormatter;
import com.merapar.xmlanalyzer.xmlanalyzer.utils.FileFromUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Service
public class AnalysisService {

    private final ResultAnalysis resultAnalysis;
    private final Details details;
    private final FileFromUrl fileFromUrl;

    private int score;

    @Autowired
    public AnalysisService(ResultAnalysis resultAnalysis, Details details, FileFromUrl fileFromUrl) {
        this.resultAnalysis = resultAnalysis;
        this.details = details;
        this.fileFromUrl = fileFromUrl;
    }

    public ResultAnalysis doAnalysis(String urlString) throws IOException, SAXException, ParserConfigurationException{

        File xmlFile = fileFromUrl.getFileFromUrl(urlString);
        NodeList nodeList = getNodeList(xmlFile);
        if(nodeList.getLength()!=0) {
            Node node = nodeList.item(0);
            setFirstAndLastPostDate(node);

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                node = nodeList.item(temp);
                setAttributesFromElements(node);
            }
            setValuesToObjects(nodeList);

            return resultAnalysis;
        }
        return null;
    }

    private void setValuesToObjects(NodeList nodeList) {
        details.setTotalPosts(nodeList.getLength());
        details.setAvgScore(score / details.getTotalPosts());
        try {
            resultAnalysis.setAnalyseDate(DateFormatter.getDateWithTimeZone(DateFormatter.getStringFromDate(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resultAnalysis.setDetails(details);
    }

    private void setAttributesFromElements(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;

            try {
                if (DateFormatter.getDateWithTimeZone(eElement.getAttribute("CreationDate")).before(details.getFirstPost())) {
                    details.setFirstPost(DateFormatter.getDateWithTimeZone(eElement.getAttribute("CreationDate")));
                }
                if (DateFormatter.getDateWithTimeZone(eElement.getAttribute("CreationDate")).after(details.getLastPost())) {
                    details.setLastPost(DateFormatter.getDateWithTimeZone(eElement.getAttribute("CreationDate")));
                }

                if (!eElement.getAttribute("AcceptedAnswerId").isEmpty()) {
                    details.setTotalAcceptedPosts(details.getTotalAcceptedPosts() + 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            score = score + Integer.valueOf(eElement.getAttribute("Score"));
        }
    }

    private void setFirstAndLastPostDate(Node node) {
        final String creationDateValueFromFirstRow = ((Element) node).getAttribute("CreationDate");
        try {
            details.setFirstPost(DateFormatter.getDateWithTimeZone(creationDateValueFromFirstRow));
            details.setLastPost(DateFormatter.getDateWithTimeZone(creationDateValueFromFirstRow));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private NodeList getNodeList(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        try {
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            return doc.getElementsByTagName("row");
        }
        catch(SAXParseException saxParseException){
            saxParseException.printStackTrace();
        }
        return null;
    }

}
