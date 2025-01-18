package org.luxie.rijkstest;

import org.junit.jupiter.api.Test;
import org.luxie.rijkstest.tools.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RijksTestApplicationTests {

    @Value("${base.url}")
    private String baseURL;

    @Value("${base.culture.en}")
    private String cultureEN;

    @Value("${base.culture.nl}")
    private String cultureNL;

    @Value("${base.key}")
    private String key;

    @Value("${base.object.number}")
    private String objectNumber;

    @Value("${base.page}")
    private int page;

    @Value("${base.userset.id}")
    private String usersetId;

    HttpUtil httpUtil = new HttpUtil();

    @Test
    public void testCollectionsCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionsCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&involvedMaker=Rembrandt+van+Rijn";
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
        String url = baseURL + "/api/" + cultureEN + "/collection?key="+key+"&objectNumber="+objectNumber;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionObjectCultureNLWithInValidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection?key="+key+"&objectNumber="+objectNumber;
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
        String url = baseURL + "/api/" + cultureEN + "/collection/" + objectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testCollectionImageCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/collection/" + objectNumber + "/tiles?key="+key;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testUsersetsCultureENWithInvalidKey() {
        String url = baseURL + "/api/" + cultureEN + "/usersets/?key="+key+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testUsersetsCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/?key="+key+"&format=json&page="+page;
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
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
        String url = baseURL + "/api/" + cultureEN + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }

    @Test
    public void testUsersetDetailCultureNLWithInvalidKey() {
        String url = baseURL + "/api/" + cultureNL + "/usersets/" + usersetId + "?key="+key+"&format=json";
        String result = httpUtil.doGet(url);
        assert result.contains("Invalid key");
    }
}
