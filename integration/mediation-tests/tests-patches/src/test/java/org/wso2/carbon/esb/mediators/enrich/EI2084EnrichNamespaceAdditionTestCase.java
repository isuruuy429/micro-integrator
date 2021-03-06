/**
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.esb.mediators.enrich;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EI2084EnrichNamespaceAdditionTestCase extends ESBIntegrationTest {
    private static final String API_NAME = "EI2084";

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
    }

    @Test(groups = "wso2.esb", description = "Enrich / Payload Factory mediators,soap namespace is added when soap is not in use")
    public void testEnrichNamespaceAdditionTestCase() throws Exception {

        String endpoint = getApiInvocationURL(API_NAME) + "/test_with_pf";
        String requestXml = "<MetaData><DateTimeSent/></MetaData>";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml;charset=UTF-8");
        HttpEntity stringEntity = new StringEntity(requestXml, CharEncoding.UTF_8);
        httpPost.setEntity(stringEntity);
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();

        BufferedReader rd = new BufferedReader(new InputStreamReader(resEntity.getContent()));
        String result = "";
        String line;
        while ((line = rd.readLine()) != null) {
            result += line;
        }
        Assert.assertTrue(!result.contains("http://schemas.xmlsoap.org/soap/envelope/"),
                "unnessary namespaces present in message");
    }

}

