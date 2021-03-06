package org.wso2.carbon.esb.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.clients.JSONClient;

import static org.testng.Assert.assertEquals;

public class HttpRelativeLocationHeaderTestCase extends ESBIntegrationTest {
    private JSONClient jsonclient;

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
        jsonclient = new JSONClient();
    }

    @Test(groups = { "wso2.esb", "localOnly" }, description = "Http Location header")
    public void testRelativeLocationHeader() throws Exception {
        String addUrl = getProxyServiceURLHttp("HTTPRelativeLocationService0Proxy");
        String query = "{\"employees\": [{\"id\": 0,\"name\": \"Carlene Pope\"},{\"id\": 1,\"name\": \"Jewell Richard\"}]}";
        String expectedResult = "{\"employees\":[{\"id\":0,\"name\":\"Carlene Pope\"},{\"id\":1,\"name\":\"Jewell Richard\"}]}";
        String actualResult = jsonclient.sendUserDefineRequest(addUrl, query).toString();
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode expectedJsonObject = mapper.readTree(expectedResult);
        final JsonNode actualJsonObject = mapper.readTree(actualResult);
        assertEquals(actualJsonObject, expectedJsonObject, "Could not process relative Location header.");
    }
}
