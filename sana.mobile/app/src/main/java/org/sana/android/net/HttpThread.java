package org.sana.android.net;

import android.os.Handler;
import android.os.Message;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used for doing a HTTP request, whether by a
 * POST method or a GET Method. The URL to request must
 * be passed in the parameter urlRequest; the parameters
 * of the request (if any), must be passed in the HashMap
 * requestParams, that can be set on the constructor or through
 * the corresponding setter. The Request code is included
 * in the Enum RequestCodeEnum. The Scheme possible values
 * are in the SchemeEnum. Other
 * parameters that can be set are the ReadTimeout and the
 * ConnectTimeout, although the default values are 10000
 * and 15000 milliseconds. These values must be set in milliseconds.
 * The class that wants to use this class must implement the interface
 * HttpThreadI. The method getHandler must return a handler that will
 * receive as a String the Response of the HttpRequest if it was
 * successful. The what of this message is the constant DATA_RECEIVED,
 * and the String is in the object of the message.
 *
 * @see RequestCodeEnum
 * @see SchemeEnum
 *
 */
public class HttpThread extends Thread
{
    public static final int DATA_RECEIVED=0;
    String urlRequest;
    HashMap<String,String> requestParams;
    RequestCodeEnum requestType;
    int readTimeout=10000,connectTimeout=15000,functionCode=0;
    HttpThreadI interfaz;
    public  interface HttpThreadI
    {
        Handler getHandler();
    }
    //region Constructoras
    public HttpThread(SchemeEnum en, String host, String path, RequestCodeEnum rc, HttpThreadI i)
    {
        this(en,host,path,rc,i,null);
    }
    public HttpThread(SchemeEnum en, String host, String path, RequestCodeEnum rc, HttpThreadI i, HashMap<String, String> params)
    {
        String url="localhost/mds/core/session";
        switch (en)
        {
            case HTTP:
                url=url+"http://";
                break;
            case HTTPS:
                url=url+"https://";
                break;
        }
        url+=host+"/"+path;
        if(!path.endsWith("/")&&!path.equals(""))
            url+="/";
        urlRequest=url;
        requestType=rc;
        requestParams=params;
        interfaz=i;
    }
    public HttpThread(String url, RequestCodeEnum rc, HttpThreadI i)
    {
        this(url,rc,i,null);
    }
    public HttpThread(String url, RequestCodeEnum rc, HttpThreadI i, HashMap<String, String> params)
    {
        urlRequest=url;
        requestType=rc;
        requestParams=params;
        interfaz=i;
    }
    //endregion

    //region Getters & setters
    public HashMap<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(HashMap<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    //endregion

    //region Utils
    public void parseParamsGet()
    {
        urlRequest+="?";
        for(Map.Entry<String,String> entry:requestParams.entrySet())
        {
            urlRequest+=entry.getKey()+"="+entry.getValue()+"&";
        }
        urlRequest=urlRequest.substring(0,urlRequest.length()-1);
        //Log.i("AppServicio","URL petición: "+urlRequest);
    }
    public void parseParamsPost(HttpPost post)
    {
        List<NameValuePair> nameValuePairs=new ArrayList<>();
        for(HashMap.Entry<String,String> entry:requestParams.entrySet())
        {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try
        {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
    //endregion

    @Override
    public void run()
    {
        HttpClient client=new DefaultHttpClient();
        HttpResponse httpResponse=null;
        try
        {
            switch (requestType) {
                case GET:
                    if (requestParams != null) {
                        parseParamsGet();
                    }
                    HttpGet httpGet = new HttpGet(urlRequest);
                    httpResponse = client.execute(httpGet);
                    break;
                case POST:
                    HttpPost httpPost = new HttpPost(urlRequest);
                    if (requestParams != null) {
                        parseParamsPost(httpPost);
                    }
                    httpResponse = client.execute(httpPost);
                    break;
            }
            if (httpResponse.getStatusLine().getStatusCode() < 300)
            {
                HttpEntity entity = httpResponse.getEntity();
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String cadena;
                String cadenaTotal="";
                while ((cadena = br.readLine()) != null)
                {
                    cadenaTotal+=cadena;
                }
                Message msg=interfaz.getHandler().obtainMessage();
                if(functionCode==0)
                {
                    msg.what=DATA_RECEIVED;
                }
                else
                {
                    msg.what=functionCode;
                }
                msg.obj=cadenaTotal;
                interfaz.getHandler().dispatchMessage(msg);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
