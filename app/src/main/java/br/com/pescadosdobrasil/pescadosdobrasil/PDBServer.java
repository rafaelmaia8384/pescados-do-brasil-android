package br.com.pescadosdobrasil.pescadosdobrasil;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PDBServer {

    public static final String VERSAO = "1.0";
    public static final String HOST_WEBSERVICE = "app-v" + VERSAO + "/";
    public static final String HOST_BASE = "http://sincabs.com.br/pescados-do-brasil/";
    public static final String HOST_BASE_DATA =  HOST_BASE + "data/";

    public static final String HOST_EXECUTAR = HOST_BASE + HOST_WEBSERVICE + "executar.php";

    private static final int OPT_OBTER_ANUNCIOS_INICIAIS = 101;
    private static final int OPT_OBTER_ANUNCIO_IMAGENS = 102;

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    private static Context context;

    public PDBServer(Context context) {

        this.context = context;

        client.setUserAgent(getUserAgent());
    }

    public void obterAnunciosIniciais(int index, PDBResponse responseHandler) {

        RequestParams params = new RequestParams();

        params.put("index", index);

        globalRequest(OPT_OBTER_ANUNCIOS_INICIAIS, params, responseHandler);
    }

    public void obterAnuncioImagens(String id_anuncio, PDBResponse responseHandler) {

        RequestParams params = new RequestParams();

        params.put("id_anuncio", id_anuncio);

        globalRequest(OPT_OBTER_ANUNCIO_IMAGENS, params, responseHandler);
    }

    private void globalRequest(int demanda, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        params.put("plataforma", "Android");
        params.put("versao", VERSAO);
        params.put("codigo", demanda);

        client.post(HOST_EXECUTAR, params, responseHandler);
    }

    private String getUserAgent() {

        return "SASP - Sistema de Apoio ao Serviço Policial - PMPB.";
    }

    public static String getImageAddress(String img, String pescado, boolean isBusca) {

        //return HOST_BASE_DATA + pescado + (isBusca ? "/busca/" : "/principal/") + img;

        return HOST_BASE_DATA + (isBusca ? "busca/" : "principal/") + img;
    }
}
