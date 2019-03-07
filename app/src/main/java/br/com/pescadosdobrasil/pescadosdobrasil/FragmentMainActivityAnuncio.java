package br.com.pescadosdobrasil.pescadosdobrasil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


public class FragmentMainActivityAnuncio extends Fragment {

    public static final String id = "MAIN_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mainactivity_anuncio, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        try {

            final JSONObject jObj = MainActivity.jsonAnuncio;

            if (jObj.getString("anuncio_pago").equals("1")) {

                getActivity().findViewById(R.id.anuncioTipoGratis).setVisibility(View.VISIBLE);
            }
            else if (jObj.getString("anuncio_pago").equals("2")) {

                getActivity().findViewById(R.id.anuncioTipoPremium).setVisibility(View.VISIBLE);
            }
            else if (jObj.getString("anuncio_pago").equals("3")) {

                getActivity().findViewById(R.id.anuncioTipoDiamante).setVisibility(View.VISIBLE);
            }
            else if (jObj.getString("anuncio_pago").equals("4")) {

                getActivity().findViewById(R.id.anuncioTipoProdutor).setVisibility(View.VISIBLE);
            }

            ((TextView)getActivity().findViewById(R.id.anuncioId)).setText("Anúncio #" + jObj.getString("id_anuncio"));
            ((TextView)getActivity().findViewById(R.id.anuncioValor)).setText(AppUtils.getPrice(jObj.getInt("produto_preco"), jObj.getInt("produto_unidade")));
            ((TextView)getActivity().findViewById(R.id.anuncioDataPublicacao)).setText("Cadastrado em " + AppUtils.formatarData(jObj.getString("data_registro")));
            ((TextView)getActivity().findViewById(R.id.anuncioTitulo)).setText(jObj.getString("titulo"));
            ((TextView)getActivity().findViewById(R.id.anuncioDescricao)).setText(jObj.getString("descricao"));

            String uf = getActivity().getResources().getStringArray(R.array.spinner_regiao)[jObj.getInt("produto_uf")];
            String municipio = jObj.getString("produto_municipio");
            ((TextView)getActivity().findViewById(R.id.anuncioLocalizacao)).setText(uf + " - " + municipio);

            ((TextView)getActivity().findViewById(R.id.anuncioCategoria)).setText(AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), false));
            ((TextView)getActivity().findViewById(R.id.anuncioAgua)).setText(AppUtils.getWaterType(jObj.getInt("tipo_agua")));
            ((TextView)getActivity().findViewById(R.id.anuncioArmazenamento)).setText(AppUtils.getHoldType(jObj.getInt("armazenamento")));
            ((TextView)getActivity().findViewById(R.id.anuncioEstoque)).setText(AppUtils.getStockType(jObj.getInt("total_disponivel")));
            ((TextView)getActivity().findViewById(R.id.anuncioFazEntrega)).setText(AppUtils.getDeliveryType(jObj.getInt("faz_entrega")));
            ((TextView)getActivity().findViewById(R.id.anuncioSeloInspecao)).setText(AppUtils.getInspectionPaper(jObj.getInt("possui_selo_inspecao")));

            ImageView image = getActivity().findViewById(R.id.produtoImagem);

            Picasso.get().load(PDBServer.getImageAddress(jObj.getString("img_busca"), AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), true), true)).into(image);

            MainActivity.pdbServer.obterAnuncioImagens(jObj.getString("id_anuncio"), new PDBResponse(getActivity()) {

                @Override
                void onPDBResponse(String error, String msg, JSONObject extra) {

                    if (error.equals("0")) {

                        try {

                            JSONArray jsonArray = extra.getJSONArray("Resultado");

                            ViewGroup vg = getActivity().findViewById(R.id.layoutImagens);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                final JSONObject json = jsonArray.getJSONObject(i);

                                String img_busca = json.getString("img_busca");

                                final View child = LayoutInflater.from(getActivity()).inflate(R.layout.layout_new_image, null);

                                ImageView image = child.findViewById(R.id.newImage);
                                Picasso.get().load(PDBServer.getImageAddress(img_busca, AppUtils.getFisheryType(jObj.getInt("tipo_pescado"), true), true)).into(image);

                                vg.addView(child);

                                child.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        try {

                                            Intent i = new Intent(getActivity(), ImageViewActivity.class);
                                            i.putExtra("img_principal", json.getString("img_principal"));
                                            i.putExtra("tipo_pescado", jObj.getInt("tipo_pescado"));
                                            startActivity(i);
                                        }
                                        catch (Exception e) {

                                            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        catch (Exception e) {

                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                void onResponse(String error) {

                }

                @Override
                void onNoResponse(String error) {

                }

                @Override
                void onPostResponse() {

                }
            });
        }
        catch (Exception e) {

            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
