package org.luxie.rijkstest;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson2.JSON;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.luxie.rijkstest.tools.DOMUtil;
import org.luxie.rijkstest.tools.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Random;

@Slf4j
@SpringBootTest
class RijksTestApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(RijksTestApplicationTests.class.getName());

    @Value("${base.url}")
    private String baseURL;

    @Value("${base.culture.en}")
    private String cultureEN;

    @Value("${base.culture.nl}")
    private String cultureNL;

    @Value("${base.key}")
    private String key;

    @Value("${base.incorrect.key}")
    private String incorrectKey;

    @Value("${base.object.number}")
    private String objectNumber;

    @Value("${base.object.incorrect.number}")
    private String incorrectObjectNumber;

    @Value("${base.page}")
    private int page;

    @Value("${base.page.size}")
    private int pageSize;

    @Value("${base.second.page.size}")
    private int pageSecondSize;

    @Value("${base.page.suer.size}")
    private int pageSuperSize;

    @Value("${base.userset.id}")
    private String usersetId;

    @Value("${base.OAIPMH.url}")
    private String OAIPMHurl;

    @Value("${base.OAIPMH.verbs}")
    private String OAIPMHVerbs;

    HttpUtil httpUtil = new HttpUtil();
    DOMUtil domUtil = new DOMUtil();

    @Test
    public void testCollectionsCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+incorrectKey+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionsCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+incorrectKey+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionsCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        //assert result.contains("count");
        JSONObject json = JSON.parse(result);
        assert json.get("count").greaterThan(0);
    }

    @Test
    public void testCollectionsCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        //assert result.contains("count");
        JSONObject json = JSON.parse(result);
        assert json.get("count").greaterThan(0);
    }

    @Test
    public void testCollectionObjectCultureENWithInValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+incorrectKey+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionObjectCultureNLWithInValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+incorrectKey+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionObjectCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        JsonObject json = JSON.parse(result);
        assert json.get("artObject").get("objectNumber").equals(objectNumber);
    }

    @Test
    public void testCollectionObjectCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parse(result);
        assert json.get("artObject").get("objectNumber").equals(objectNumber);
    }

    @Test
    public void testInvalidCollectionObjectCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+incorrectObjectNumber;
        String result = httpUtil.doGet(url);
        JSONObject jsonObject = JSON.parse(result);
        //Search with an invalid data and it should return not found, and count 0.
        assert jsonObject.get("count").equals(0);
    }

    @Test
    public void testInValidCollectionObjectCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&objectNumber="+incorrectObjectNumber;
        String result = httpUtil.doGet(url);
        JSONObject jsonObject = JSON.parse(result);
        //Search with an invalid data and it should return not found, and count 0.
        assert jsonObject.get("count").equals(0);
    }

    @Test
    public void testCollectionImageCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection/" + objectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        assert result.contains("levels");
    }

    @Test
    public void testCollectionImageCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection/" + objectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        assert result.contains("levels");
    }

    @Test
    public void testInvalidCollectionImageCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection/" + incorrectObjectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parse(result);
        //Nothing found with the invalid object number, so it should return not levels with 0 elements
        assert json.getJSONArray("levels").length() == 0;
    }

    @Test
    public void testInvalidCollectionImageCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection/" + incorrectObjectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parse(result);
        //Nothing found with the invalid object number, so it should return not levels with 0 elements
        assert json.getJSONArray("levels").length() == 0;
    }

    @Test
    public void testCollectionImageCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection/" + objectNumber + "/tiles?key="+incorrectKey;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionImageCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection/" + objectNumber + "/tiles?key="+incorrectKey;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testUsersetsCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+incorrectKey+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("invalid key");
    }

    @Test
    public void testUsersetsCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+incorrectKey+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("invalid key");
    }

    @Test
    public void testUsersetsCultureENWithValidKeyWithPageAndPageSizeNotGreaterThan10000() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSize;
        String result = httpUtil.doGet(url);
        int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSets");
        assert domResult == pageSize;
    }

    @Test
    public void testUsersetsCultureNLWithValidKeyWithPageAndPageSizeNotGreaterThan10000() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSecondSize;;
        String result = httpUtil.doGet(url);
        int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSets");
        assert domResult == pageSecondSize;
    }

    @Test
    public void testUsersetsCultureENWithValidKeyWithPageAndPageSizeGreaterThan10000() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSuperSize;
        String result = httpUtil.doGet(url);
        String domResult = domUtil.parseXMLStringAndReturnElementValue(result, "count");
        assert domResult.equals("0");
    }

    @Test
    public void testUsersetsCultureNLWithValidKeyWithPageAndPageSizeGreaterThan10000() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSuperSize;;
        String result = httpUtil.doGet(url);
        String domResult = domUtil.parseXMLStringAndReturnElementValue(result, "count");
        assert domResult.equals("0");
    }

    @Test
    public void testUsersetDetailCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("userSet");
    }

    @Test
    public void testUsersetDetailCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("userSet");
    }

    @Test
    public void testUsersetDetailCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/" + usersetId + "?key="+incorrectKey+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("invalid key");
    }

    @Test
    public void testUsersetDetailCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/" + usersetId + "?key="+incorrectKey+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("invalid key");
    }

    @Test
    public void testOAI_PMHVerbs() {
        String[] verb = OAIPMHVerbs.split(",");
        for (String v : verb) {
            String url = OAIPMHurl + "?verb=" + v;
            String result = httpUtil.doGet(url);
            assert result.contains("OAI-PMH");
        }
    }

    @Test
    public void testOAI_PMHPerformance() {
        String[] verb = OAIPMHVerbs.split(",");
        Random random = new Random();
        for(int i = 0;i < 100;i++){
            int temp = random.nextInt(verb.length);
            String url = OAIPMHurl + "?verb=" + verb[temp];
            long startTime = System.currentTimeMillis();
            String result = httpUtil.doGet(url);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            assert duration < 1000;
            LOGGER.debug("Duration: " + duration + "ms");

        }
    }
}
