package com.merapar.xmlanalyzer.xmlanalyzer.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;



    @RunWith(SpringRunner.class)
    public class FileFromUrlTest {

        FileFromUrl fileFromUrl;

        @Before
        public void setUp() throws Exception {
            fileFromUrl = new FileFromUrl();
        }

        @Test
        public void whenUrlIsValid_thenReturnCorrectFile(){
            String urlString = "http://s3-eu-west-1.amazonaws.com/merapar-assessment/arabic-posts.xml";
            String expectedCorrectFileNameInReturn = "xmlFile";
            String returnFileName = fileFromUrl.getFileFromUrl(urlString).getName();

            Assert.assertEquals(expectedCorrectFileNameInReturn, returnFileName);
        }

        @Test(expected= IOException.class)
        public void whenUrlIsInvalid_thenExceptionSatisfied(){
            String invalidUrlString = "http://s3-eu-west-1.amazonaws.com/merapar-assessment/araic-posts.xml";
            File file = fileFromUrl.getFileFromUrl(invalidUrlString);
        }
}
