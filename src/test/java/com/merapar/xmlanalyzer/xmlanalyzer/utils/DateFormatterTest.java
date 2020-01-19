package com.merapar.xmlanalyzer.xmlanalyzer.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@RunWith(SpringRunner.class)
@RunWith(value = BlockJUnit4ClassRunner.class)
public class DateFormatterTest {

    @Test
    public void whenDateIsValid_thenReturnDateWithTimeZone() throws ParseException {
        String actualDateString = "2016-01-12T18:45:19.963";
        String expectedDateString = "2016-01-12T06:45:19.963";
        Date date = DateFormatter.getDateWithTimeZone(actualDateString);
        Assert.assertEquals(expectedDateString, DateFormatter.getStringFromDate(date));
    }

    @Test
    public void whenDateIsNotValid_thenExceptionSatisfied() {
        String dateString = "test";
        Exception exception = assertThrows(ParseException.class, () -> {
            DateFormatter.getDateWithTimeZone(dateString);
        });
        String expectedMessage = "Unparseable date";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }
}
