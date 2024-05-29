package com.myaem65training.core.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myaem65training.core.config.MyNYTimesApiConfiguration;
import com.myaem65training.core.constants.AppConstants;
import com.myaem65training.core.service.GsonProvider;
import com.myaem65training.core.service.MyNYTimesApiConfigurationFactory;
import com.myaem65training.core.service.SearchHelperService;
import com.myaem65training.core.servlets.BookDetailServlet;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.google.gson.JsonParser.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Component(service = SearchHelperService.class)
public class SearchHelperServiceImpl implements SearchHelperService {
    @Reference(service =  MyNYTimesApiConfigurationFactory.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, fieldOption = FieldOption.UPDATE, bind = "bindConfigFactory", unbind = "unbindConfigFactory")
    private List<MyNYTimesApiConfigurationFactory> configFactories;
    private static final Logger log = LoggerFactory.getLogger(SearchHelperServiceImpl.class);
    @Reference
    private GsonProvider gsonProvider;

    private CloseableHttpClient httpClient;

    protected void bindConfigFactory(final MyNYTimesApiConfigurationFactory configFactory){
        if(configFactories == null){
            configFactories = new ArrayList<>();
        }
        configFactories.add(configFactory);
    }

    protected void unbindConfigFactory(final MyNYTimesApiConfigurationFactory configFactory){
        configFactories.remove(configFactory);
    }
    @Override
    public MyNYTimesApiConfigurationFactory getConfigFactory(String path) {
        //return configFactories.stream().filter(factory);
        return configFactories.stream().filter(factory -> path.startsWith(factory.getPath()))
                .max(Comparator.comparingInt(factory -> factory.getPath().length())).orElse(null);
    }

    @Override
    public String makeApiCall(String requestUrl, MyNYTimesApiConfigurationFactory config, JsonObject payloadJsonObj) {
        String apikey = config.getNytimesAPIKey();
        String listParam = AppConstants.URL_CATEGORY_PARAM;
        if(!requestUrl.contains(apikey)) requestUrl = requestUrl.concat(AppConstants.TNYTIMES_QUERYPARAMETER + AppConstants.TNYTIMES_API_KEY_PARAMETER_KEY + apikey);
        if(!requestUrl.contains(listParam)) requestUrl = requestUrl.concat(AppConstants.DEFAULT_BOOK_CATEGORY);
        Map<String, String> headerMap = new HashMap<>();
        if(null != payloadJsonObj && StringUtils.isNotEmpty(requestUrl)){
            headerMap.put(HttpHeaders.CONTENT_TYPE, AppConstants.CONTENT_JSON);
            try{
                String bookDetailResult = requestExecutor(requestUrl, payloadJsonObj, headerMap);
                if(StringUtils.isNotEmpty(bookDetailResult)) return bookDetailResult;
            } catch(IllegalArgumentException | UnsupportedOperationException ex){
                log.error("IllegalArgumentException | UnsupportedOperationException :: ", ex);
                return null;
            }
        }
        return null;
    }

    @Override
    public String makeRecipeCategoryListApiCall() throws IOException {
        StringBuffer jsonResponseData = new StringBuffer();
        BufferedReader readLine = null;
        BufferedReader bufferedReader = null;
        CloseableHttpClient httpClient =null;
        try{
            int timeout = 5;
            RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(10000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet httpget = new HttpGet(AppConstants.RECIPIE_API_ENDPOINT.concat(AppConstants.RECIPIE_API_LIST_ALL_MEAL_CATEGORIES));
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
            bufferedReader = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line ;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            com.google.gson.JsonParser jsonParser = new JsonParser();
            com.google.gson.JsonObject jsonObject = jsonParser.parse(result.toString()).getAsJsonObject();
            return jsonObject.toString();
        } catch (NullPointerException e){
            log.error(e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        } finally{
            httpClient.close();
        }
        return null;
    }

    private String requestExecutor(String requestUrl, JsonObject payloadJson, Map<String, String> headerMap){
        log.info("Invoking the Request Executor Method to make API call :: ");
        JsonParser jsonParser = new JsonParser();
        try{
            if(!StringUtils.isNotEmpty(requestUrl)){return null;}
            else{
                HttpPost request = new HttpPost(requestUrl);
                for(Map.Entry<String, String>entry: headerMap.entrySet()){
                    request.addHeader(entry.getKey(), entry.getValue());
                }
                request.setEntity(new StringEntity(payloadJson.toString()));
                try(CloseableHttpResponse authResponse = this.getHttpClient().execute(request)){
                    log.info("Connection has made successfully with the response code :: {}", authResponse.getStatusLine().getStatusCode());
                    JsonObject responseJson = null;
                    String resp = EntityUtils.toString(authResponse.getEntity(), UTF_8);
                    if(authResponse.getStatusLine().getStatusCode() == SC_OK){
                        if(jsonParser.parse(resp).isJsonArray()){
                            responseJson = this.gsonProvider.getGson().fromJson(resp, JsonArray.class).get(0).getAsJsonObject();
                        } else{
                            responseJson = this.gsonProvider.getGson().fromJson(resp, JsonObject.class).getAsJsonObject();
                        }
                    } else{
                        responseJson = this.gsonProvider.getGson().fromJson(resp, JsonObject.class).getAsJsonObject();
                    }
                    return responseJson.toString();
                }
            }
        } catch(IOException ioe){
            log.error("IO Exception :: ", ioe);
        }
        return null;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }
}
