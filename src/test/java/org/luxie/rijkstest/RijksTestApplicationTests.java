package org.luxie.rijkstest;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson2.JSON;
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

    @Value("${base.page.super.size}")
    private int pageSuperSize;

    @Value("${base.userset.id}")
    private String usersetId;

    @Value("${base.OAIPMH.url}")
    private String OAIPMHurl;

    @Value("${base.OAIPMH.verbs}")
    private String OAIPMHVerbs;

    @Value("${base.OAIPMH.incorrect.verb}")
    private String OAIPMHIncorrectVerb;

    HttpUtil httpUtil = new HttpUtil();
    //DOMUtil domUtil = new DOMUtil();

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
        JSONObject json = JSON.parseObject(result);
        assert json.getIntValue("count") > 0;
    }

    @Test
    public void testCollectionsCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        //assert result.contains("count");
        JSONObject json = JSON.parseObject(result);
        assert json.getIntValue("count") > 0;
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
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("artObjects")){
            //It returns all items which objectNumber starts with BK-NM
            assert json.getJSONArray("artObjects").getObject(0, JSONObject.class).getString("objectNumber").equals(objectNumber);
        } else {
            assert false;
        }
    }

    @Test
    public void testCollectionObjectCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("artObjects")){
            //It returns all items which objectNumber starts with BK-NM
            assert json.getJSONArray("artObjects").getObject(0, JSONObject.class).getString("objectNumber").equals(objectNumber);
        } else {
            assert false;
        }
    }

    @Test
    public void testInvalidCollectionObjectCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+incorrectObjectNumber;
        String result = httpUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        //Search with an invalid data and it should return not found, and count 0.
        assert jsonObject.getIntValue("count") == 0;
    }

    @Test
    public void testInValidCollectionObjectCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&objectNumber="+incorrectObjectNumber;
        String result = httpUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        //Search with an invalid data and it should return not found or count = 0, but now it returns all items.
        assert jsonObject.getIntValue("count") == 0;
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
        JSONObject json = JSON.parseObject(result);
        //Nothing found with the invalid object number, so it should return not levels with 0 elements but now it returns 403
        if(json == null) {
            assert true;
        } else {
            assert json.getJSONArray("levels").isEmpty();
        }
    }

    @Test
    public void testInvalidCollectionImageCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection/" + incorrectObjectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parseObject(result);
        //Nothing found with the invalid object number, so it should return not levels with 0 elements but now it returns 403
        if(json == null) {
            assert true;
        } else {
            assert json.getJSONArray("levels").isEmpty();
        }
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
        //int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSets");
        //At the beginning, they are using XML, now they are using JSON
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("userSets")){
            assert json.getJSONArray("userSets").size() == pageSize;
        } else {
            assert false;
        }
    }

    @Test
    public void testUsersetsCultureNLWithValidKeyWithPageAndPageSizeNotGreaterThan10000() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSecondSize;;
        String result = httpUtil.doGet(url);
        JSONObject json = JSON.parseObject(result);
        //int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSets");
        //Previous version is using XML, now they are using JSON
        if(json.containsKey("userSets")){
            assert json.getJSONArray("userSets").size() == pageSecondSize;
        } else {
            assert false;
        }
    }

    @Test
    public void testUsersetsCultureENWithValidKeyWithPageAndPageSizeGreaterThan10000() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSuperSize;
        String result = httpUtil.doGet(url);
        //String domResult = domUtil.parseXMLStringAndReturnElementValue(result, "count");
        //At the beginning, they are using XML, now they are using JSON
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("count")){
            assert json.getIntValue("count") == 0;
        } else {
            assert false;
        }
    }

    @Test
    public void testUsersetsCultureNLWithValidKeyWithPageAndPageSizeGreaterThan10000() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page+"&pageSize="+pageSuperSize;;
        String result = httpUtil.doGet(url);
        //String domResult = domUtil.parseXMLStringAndReturnElementValue(result, "count");
        //At the beginning, they are using XML, now they are using JSON
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("count")){
            assert json.getIntValue("count") == 0;
        } else {
            assert false;
        }
    }

    @Test
    public void testUsersetDetailCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        //int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSet");
        //At the beginning, they are using XML, now they are using JSON
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("userSet")){
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    public void testUsersetDetailCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        //int domResult = domUtil.parseXMLStringAndReturnElementCount(result, "userSet");
        //String domResult = domUtil.parseXMLStringAndReturnElementValue(result, "count");
        //At the beginning, they are using XML, now they are using JSON
        JSONObject json = JSON.parseObject(result);
        if(json.containsKey("userSet")){
            assert true;
        } else {
            assert false;
        }
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
            switch (v) {
                case "Identify":
                    LOGGER.info("Identify");
                    assert result.contains("Identify");
                    break;
                case "ListMetadataFormats":
                    LOGGER.info("ListMetadataFormats");
                    assert result.contains("ListMetadataFormats");
                    break;
                case "ListSets":
                    LOGGER.info("ListSets");
                    assert result.contains("ListSets");
                    break;
                case "ListIdentifiers":
                    LOGGER.info("ListIdentifiers");
                    assert result.contains("ListIdentifiers");
                    break;
                case "ListRecords":
                    LOGGER.info("ListRecords");
                    assert result.contains("ListRecords");
                    break;
                case "GetRecord":
                    LOGGER.info("GetRecord");
                    assert result.contains("GetRecord");
                    break;
                default:
                    assert false;
            }
        }
    }

    @Test
    public void testOAI_PMHInvalidVerb() {
        String url = OAIPMHurl + "?verb=" + OAIPMHIncorrectVerb;
        String result = httpUtil.doGet(url);
        assert result.contains("badVerb");
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
            LOGGER.info("Duration: " + duration + "ms");
        }
    }
}
