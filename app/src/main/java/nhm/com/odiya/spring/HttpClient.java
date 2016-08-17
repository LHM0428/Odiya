package nhm.com.odiya.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HttpClient {

    private static final String WWW_FORM = "application/x-www-form-urlencoded";

    private int httpStatusCode;
    private String body;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getBody() {
        return body;
    }

    private Builder builder;

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void request() {
        HttpURLConnection conn = getConnection();
        setHeader(conn);
        setBody(conn);
        httpStatusCode = getStatusCode(conn);
        body = readStream(conn);
        conn.disconnect();
    }

    private HttpURLConnection getConnection() {
        try {
            URL url = new URL(builder.getUrl());
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHeader(HttpURLConnection connection) {
        setContentType(connection);
        setRequestMethod(connection);

        connection.setConnectTimeout(10000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    private void setContentType(HttpURLConnection connection) {
        connection.setRequestProperty("Content-Type", WWW_FORM);
    }

    private void setRequestMethod(HttpURLConnection connection) {
        try {
            connection.setRequestMethod(builder.getMethod());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    private void setBody(HttpURLConnection connection) {

        String parameter = builder.getParameters();
        if (parameter != null && parameter.length() > 0) {
            OutputStream outputStream = null;
            try {
                outputStream = connection.getOutputStream();
                outputStream.write(parameter.getBytes("UTF-8"));
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null)
                        outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private int getStatusCode(HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -10;
    }

    private String readStream(HttpURLConnection connection) {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }

        return result;
    }

    public static class Builder {

        private Map parameters;
        private String method;
        private String url;

        public String getMethod() {
            return method;
        }

        public String getUrl() {
            System.out.println("getUrl: "+url);
            return url;
        }

        public Builder(String method, String url) {
            if (method == null) {
                method = "GET";
            }
            this.method = method;
            this.url = url;
            this.parameters = new HashMap();
        }

        public void addOrReplace(String key, String value) {
            this.parameters.put(key, value);
        }
        public void addAllParams(Map map) {
            this.parameters = map;
        }

        public String getParameters() {
            return generateParameters();
        }

        public String getParameter(String key) {
            return (String) this.parameters.get(key);
        }

        private String generateParameters() {
            StringBuffer parameters = new StringBuffer();

            Iterator keys = getKeys();

            String key = "";
            while (keys.hasNext()) {
                key = (String) keys.next();
                parameters.append(String.format("%s=%s", key, this.parameters.get(key)));
                parameters.append("&");
            }

            String params = parameters.toString();
            if (params.length() > 0) {
                params = params.substring(0, params.length() - 1);
            }

            return params;
        }

        private Iterator getKeys() {
            return this.parameters.keySet().iterator();
        }

        public HttpClient create() {
            HttpClient client = new HttpClient();
            client.setBuilder(this);
            return client;
        }

    }
 /*
    스프링 구현 예제
    public class NetworkOdiya extends AsyncTask<NGeoPoint, Integer, String> {
        *//**
     * doInBackground 실행되기 이전에 동작한다.
     *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(NGeoPoint... params) {
            *//**
     * 본 작업을 쓰레드로 처리해준다.
     *//*
            try {
                String keyword =  URLEncoder.encode("이디야", "UTF-8");
                NGeoPoint searchPoint = params[0];
                String url = "https://apis.daum.net/local/v1/search/keyword.json?apikey=8d76f7a19e09506d7ea7b9e7338ebd2a&query="+
                        keyword+"&location="+searchPoint.getLatitude()+","+searchPoint.getLongitude()+"&radius=5000";
                Log.d("url : ",url);
                HttpClient.Builder http = new HttpClient.Builder("POST", url);
                HttpClient post = http.create();
                post.request();

                // 응답 상태코드 가져오기
                int statusCode = post.getHttpStatusCode();

                // 응답 본문 가져오기
                String body = post.getBody();

                return body;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

        *//**
     * doInBackground 종료되면 동작한다.
     * s : doInBackground가 리턴한 값이 들어온다.
     *//*
        @Override
        protected void onPostExecute(String s) {
            JSONObject dataJson = null;
            odiyaList = new ArrayList<>();
            try {
                dataJson = new JSONObject(s);
                JSONObject dataChannel = (JSONObject) dataJson.get("channel");
                JSONArray jsonArray = (JSONArray) dataChannel.get("item");
                Gson gson = new Gson();
                for(int i=0; i<jsonArray.length(); i++){
                    String odiyaJsonStr = jsonArray.getJSONObject(i).toString();
                    Log.d("odiyaString : ", odiyaJsonStr);
                    OdiyaData data = gson.fromJson(odiyaJsonStr, OdiyaData.class);
                    odiyaList.add(data);
                }
                Log.d("odiyaList : ", odiyaList.get(0).getTitle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}