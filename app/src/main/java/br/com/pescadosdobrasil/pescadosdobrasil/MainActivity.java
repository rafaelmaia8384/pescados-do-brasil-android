package br.com.pescadosdobrasil.pescadosdobrasil;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static PDBServer pdbServer;
    public static DialogHelper dialogHelper;

    public static JSONObject jsonAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdbServer = new PDBServer(this);
        dialogHelper = new DialogHelper(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.mainFragment, new FragmentMainActivityMain(), FragmentMainActivityMain.id);
        ft.commitAllowingStateLoss();
    }

    public void abrirAnuncio(View view) {

        jsonAnuncio = (JSONObject)view.getTag();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.mainFragment, new FragmentMainActivityAnuncio(), FragmentMainActivityMain.id);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public void buttonBack(View view) {

        onBackPressed();
    }
}
