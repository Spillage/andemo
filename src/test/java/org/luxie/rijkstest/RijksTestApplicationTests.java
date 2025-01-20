package org.luxie.rijkstest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

    @Value("${base.page}")
    private int page;

    @Value("${base.userset.id}")
    private String usersetId;

    @Value("${base.OAIPMH.url}")
    private String OAIPMHurl;

    @Value("${base.OAIPMH.verbs}")
    private String OAIPMHVerbs;

    HttpUtil httpUtil = new HttpUtil();

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
        assert result.contains("count");
    }

    @Test
    public void testCollectionsCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        assert result.contains("count");
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
        assert result.contains("count");
    }

    @Test
    public void testCollectionObjectCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        assert result.contains("count");
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
    public void testUsersetsCultureENWithValidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+key+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("userSets");
    }

    @Test
    public void testUsersetsCultureNLWithValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("userSets");
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
