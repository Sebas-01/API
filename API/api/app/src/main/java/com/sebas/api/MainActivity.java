package com.sebas.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sebas.api.modelos.Pokemon;
import com.sebas.api.modelos.pokemonResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private Retrofit retrofit;

private static final String BASEURL = "https://pokeapi.co/api/v2/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .build();

        obtenerDatosPokemon();

    }

    private void obtenerDatosPokemon(){
       APIService servicio = retrofit.create(APIService.class);
       Call<pokemonResponse> respuesta = servicio.obtenerListaPokemon();

       respuesta.enqueue(new Callback<pokemonResponse>() {
           @Override
           public void onResponse(Call<pokemonResponse> call, Response<pokemonResponse> response) {
              if(response.isSuccessful()){
                  pokemonResponse respuesdtaApi = response.body();
                  ArrayList<Pokemon> listaPokemon = respuesdtaApi.getResults();
                  for (int i = 0; i< listaPokemon.size();i++){
                      Pokemon pokemon = listaPokemon.get(i);
                  }
              }
              else {
                  Toast.makeText(MainActivity.this, "error"+response, Toast.LENGTH_SHORT).show();
              }
           }

           @Override
           public void onFailure(Call<pokemonResponse> call, Throwable t) {
               Toast.makeText(MainActivity.this, "error de consulta", Toast.LENGTH_SHORT).show();
           }
       });
    }
}