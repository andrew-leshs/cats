import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        String body = new String(response.getEntity().getContent().readAllBytes(),
                StandardCharsets.UTF_8);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("cats.json"))) {
            bw.write(body);
        }

        JSONParser parser = new JSONParser();
        List<Cats> cats = new ArrayList<>();
        try {
            Object obj = parser.parse(new InputStreamReader(new FileInputStream("cats.json")));
            JSONArray array = (JSONArray) obj;
            for (Object jsonObject : array) {

                JSONObject catObj = (JSONObject) jsonObject;
                Long upvotes = (Long) catObj.get("upvotes");
                if (upvotes != null) {
                    String id = (String) catObj.get("id");
                    String text = (String) catObj.get("text");
                    String type = (String) catObj.get("type");
                    String user = (String) catObj.get("user");

                    Cats cat = new Cats(id, text, type, user, upvotes);
                    cats.add(cat);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (Cats cat : cats) {
            System.out.println(cat);
        }
    }
}
