package com.merapar.xmlanalyzer.xmlanalyzer.service;


import com.merapar.xmlanalyzer.xmlanalyzer.utils.FileFromUrl;
import com.merapar.xmlanalyzer.xmlanalyzer.model.Details;
import com.merapar.xmlanalyzer.xmlanalyzer.model.ResultAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Service
public class AnalysisService {

    private final ResultAnalysis resultAnalysis;
    private final Details details;
    private final FileFromUrl fileFromUrl;


    private int score = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    @Autowired
    public AnalysisService(ResultAnalysis resultAnalysis, Details details, FileFromUrl fileFromUrl) {
        this.resultAnalysis = resultAnalysis;
        this.details = details;
        this.fileFromUrl = fileFromUrl;
    }

    public ResultAnalysis doAnalysis(String urlString) throws IOException, SAXException, ParserConfigurationException, ParseException {

        File xmlFile = fileFromUrl.getFileFromUrl(urlString);
        NodeList nodeList = getNodeList(xmlFile);
        setFirstAndLastPostDate(nodeList);

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            setAttributesFromElements(node);
        }
        details.setTotalPosts(nodeList.getLength());
        details.setAvgScore(score / details.getTotalPosts());
        resultAnalysis.setAnalyseDate(dateFormat.parse(dateFormat.format(new Date())));
        resultAnalysis.setDetails(details);

        return resultAnalysis;
    }

    private void setAttributesFromElements(Node node) throws ParseException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;

            if (dateFormat.parse(eElement.getAttribute("CreationDate")).before(details.getFirstPost())) {
                details.setFirstPost(dateFormat.parse(eElement.getAttribute("CreationDate")));
            }
            if (dateFormat.parse(eElement.getAttribute("CreationDate")).after(details.getLastPost())) {
                details.setLastPost(dateFormat.parse(eElement.getAttribute("CreationDate")));
            }

            if (!eElement.getAttribute("AcceptedAnswerId").isEmpty()) {
                details.setTotalAcceptedPosts(details.getTotalAcceptedPosts() + 1);
            }
            score = score + Integer.valueOf(eElement.getAttribute("Score"));
        }
    }

    private void setFirstAndLastPostDate(NodeList nodeList) {
        dateFormat.setTimeZone(TimeZone.getDefault());
        try {
            details.setFirstPost(dateFormat.parse(((Element) nodeList.item(0)).getAttribute("CreationDate")));
            details.setLastPost(dateFormat.parse(((Element) nodeList.item(0)).getAttribute("CreationDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private NodeList getNodeList(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName("row");
    }

}
