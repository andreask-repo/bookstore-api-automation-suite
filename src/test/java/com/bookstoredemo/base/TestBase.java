package com.bookstoredemo.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static ExtentReports extentReports;
    protected static final Logger logger = LogManager.getLogger(TestBase.class);

    @BeforeAll
    public static void setup() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("BooksApiReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        logger.info("ExtentReports setup completed.");
    }

    @AfterAll
    public static void tearDown() {
        // Flush the reports
        extentReports.flush();
        logger.info("ExtentReports flushed.");
    }
}
