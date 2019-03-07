package br.com.pescadosdobrasil.pescadosdobrasil;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


public class FragmentMainActivityMain extends Fragment {

    public static final String id = "MAIN_FRAGMENT";

    private View view = null;
    private boolean hasLoaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_mainactivity_main, container, false);
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (!hasLoaded) {

            hasLoaded = true;

            processInit();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void processInit() {

        getActivity().findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getActivity().findViewById(R.id.layoutSearch).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.buttonSearch).setVisibility(View.GONE);

                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(getActivity().findViewById(R.id.layoutSearch));
            }
        });

        getActivity().findViewById(R.id.searchStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                ft.replace(R.id.mainFragment, new FragmentMainActivitySearch(), FragmentMainActivityMain.id);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
        });

        MainActivity.dialogHelper.showProgress();

        MainActivity.pdbServer.obterAnunciosIniciais(1, new PDBResponse(getActivity()) {

            @Override
            void onPDBResponse(String error, String msg, JSONObject extra) {

                try {

                    JSONArray json = extra.getJSONArray("Resultado");

                    ViewGroup vgProdutor = getActivity().findViewById(R.id.layoutProdutor);
                    ViewGroup vgDestaque = getActivity().findViewById(R.id.layoutDestaque);
                    ViewGroup vgUltimos = getActivity().findViewById(R.id.layoutUltimos);

                    for (int a = 0; a < json.length(); a++) {

                        final JSONArray jArr = json.getJSONArray(a);

                        for (int b = 0; b < jArr.length(); b++) {

                            final JSONObject jObj = jArr.getJSONObject(b);

                            if (a == 0) {

                                View child = LayoutInflater.from(getActivity()).inflate(R.layout.layout_anuncio_simple, null);
                                ((TextView)child.findViewById(R.id.produtoCategoria)).setText(AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), false));
                                ((TextView)child.findViewById(R.id.produtoTitulo)).setText(jObj.getString("titulo"));
                                child.setTag(jObj);

                                if (jObj.getInt("anuncio_pago") == 2) {

                                    child.findViewById(R.id.anuncioPremium).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 3) {

                                    child.findViewById(R.id.anuncioDiamante).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 4) {

                                    child.findViewById(R.id.anuncioProdutor).setVisibility(View.VISIBLE);
                                }

                                vgProdutor.addView(child);

                                final ImageView image = child.findViewById(R.id.produtoImagem);

                                Picasso.get().load(PDBServer.getImageAddress(jObj.getString("img_busca"), AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), true), true)).into(image);
                            }
                            else if (a == 1) {

                                View child = LayoutInflater.from(getActivity()).inflate(R.layout.layout_anuncio_simple, null);
                                ((TextView)child.findViewById(R.id.produtoCategoria)).setText(AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), false));
                                ((TextView)child.findViewById(R.id.produtoTitulo)).setText(jObj.getString("titulo"));
                                child.setTag(jObj);

                                if (jObj.getInt("anuncio_pago") == 2) {

                                    child.findViewById(R.id.anuncioPremium).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 3) {

                                    child.findViewById(R.id.anuncioDiamante).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 4) {

                                    child.findViewById(R.id.anuncioProdutor).setVisibility(View.VISIBLE);
                                }

                                vgDestaque.addView(child);

                                final ImageView image = child.findViewById(R.id.produtoImagem);

                                Picasso.get().load(PDBServer.getImageAddress(jObj.getString("img_busca"), AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), true), true)).into(image);
                            }
                            else {

                                View child = LayoutInflater.from(getActivity()).inflate(R.layout.layout_anuncio_full, null);
                                ((TextView)child.findViewById(R.id.produtoCategoria)).setText(AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), false));
                                ((TextView)child.findViewById(R.id.produtoTitulo)).setText(jObj.getString("titulo"));
                                ((TextView)child.findViewById(R.id.produtoDescricao)).setText(jObj.getString("descricao"));
                                child.setTag(jObj);

                                if (jObj.getInt("anuncio_pago") == 2) {

                                    child.findViewById(R.id.anuncioPremium).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 3) {

                                    child.findViewById(R.id.anuncioDiamante).setVisibility(View.VISIBLE);
                                }
                                else if (jObj.getInt("anuncio_pago") == 4) {

                                    child.findViewById(R.id.anuncioProdutor).setVisibility(View.VISIBLE);
                                }

                                vgUltimos.addView(child);

                                final ImageView image = child.findViewById(R.id.produtoImagem);

                                Picasso.get().load(PDBServer.getImageAddress(jObj.getString("img_busca"), AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), true), true)).into(image);
                            }
                        }
                    }

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            getActivity().findViewById(R.id.layoutProgress).setVisibility(View.GONE);
                            getActivity().findViewById(R.id.layoutInit).setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                }
                catch (Exception e) {

                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            void onResponse(String error) {

                MainActivity.pdbServer.obterAnunciosIniciais(1, this);
            }

            @Override
            void onNoResponse(String error) {

                MainActivity.pdbServer.obterAnunciosIniciais(1, this);
            }

            @Override
            void onPostResponse() {

                MainActivity.dialogHelper.dismissProgress();
            }
        });
    }
}
