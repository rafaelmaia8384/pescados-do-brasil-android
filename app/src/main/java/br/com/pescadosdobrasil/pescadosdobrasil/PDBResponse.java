package br.com.pescadosdobrasil.pescadosdobrasil;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

public abstract class PDBResponse extends AsyncHttpResponseHandler {

    private Context context;

    protected PDBResponse(Context context) {

        this.context = context;
    }

    @Override
    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

        try {

            Thread.sleep(500);
        }
        catch (Exception e) { }

        super.onPreProcessResponse(instance, response);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        String response = "";
        JSONObject json = null;

        try {

            response = new String(responseBody, "UTF-8");
            json = new JSONObject(response);

            onPDBResponse(json.getString("error"), json.getString("msg"), json.getString("extra").equals("null") ? null : new JSONObject(json.getString("extra")));
        }
        catch (Exception e) {

            onResponse(response + "\n\n" + e.getLocalizedMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        onNoResponse("Não foi possível conectar com o servidor." + "\n\n" + error);
    }

    @Override
    public void onFinish() {

        onPostResponse();

        super.onFinish();
    }

    abstract void onPDBResponse(String error, String msg, JSONObject extra);
    abstract void onResponse(String error);
    abstract void onNoResponse(String error);
    abstract void onPostResponse();
}