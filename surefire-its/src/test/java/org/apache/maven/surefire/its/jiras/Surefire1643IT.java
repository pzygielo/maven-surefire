/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.surefire.its.jiras;

import org.apache.maven.surefire.its.fixture.OutputValidator;
import org.apache.maven.surefire.its.fixture.SurefireJUnit4IntegrationTestCase;
import org.apache.maven.surefire.its.fixture.TestFile;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings("checkstyle:magicnumber")
public class Surefire1643IT extends SurefireJUnit4IntegrationTestCase {
    @Test
    public void shouldNotMixResults() {
        OutputValidator outputValidator = unpack("surefire-1643-parallel-junit5")
                .maven()
                .withFailure()
                .executeTest()
                .verifyTextInLog("BUILD FAILURE")
                .assertTestSuiteResults(15, 0, 4, 0);

        TestFile xmlReportFile = outputValidator.getSurefireReportsXmlFile("TEST-io.olamy.App1Test.xml");
        xmlReportFile
                .assertNotContainsText("App2Test")
                .assertNotContainsText("App3Test")
                .assertNotContainsText("App4Test")
                .assertNotContainsText("App5Test");

        xmlReportFile = outputValidator.getSurefireReportsXmlFile("TEST-io.olamy.App2Test.xml");
        xmlReportFile
                .assertNotContainsText("App1Test")
                .assertNotContainsText("App3Test")
                .assertNotContainsText("App4Test")
                .assertNotContainsText("App5Test");

        xmlReportFile = outputValidator.getSurefireReportsXmlFile("TEST-io.olamy.App3Test.xml");
        xmlReportFile
                .assertNotContainsText("App1Test")
                .assertNotContainsText("App2Test")
                .assertNotContainsText("App4Test")
                .assertNotContainsText("App5Test");

        xmlReportFile = outputValidator.getSurefireReportsXmlFile("TEST-io.olamy.App4Test.xml");
        xmlReportFile
                .assertNotContainsText("App1Test")
                .assertNotContainsText("App2Test")
                .assertNotContainsText("App3Test")
                .assertNotContainsText("App5Test");

        xmlReportFile = outputValidator.getSurefireReportsXmlFile("TEST-io.olamy.App5Test.xml");
        xmlReportFile
                .assertNotContainsText("App1Test")
                .assertNotContainsText("App2Test")
                .assertNotContainsText("App3Test")
                .assertNotContainsText("App4Test");
    }
}
