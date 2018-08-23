package br.com.async.asynctaskexample;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText edtLink;
    Button btnDownload;
    ImageView imvImagem;
    ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLink = findViewById(R.id.textoUrl);
        btnDownload = findViewById(R.id.botao);
        imvImagem = findViewById(R.id.imagem);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamarAsyncTask(edtLink.getText().toString());
            }
        });
    }

    private void chamarAsyncTask(String link) {
        // executar tarefa assincrona
        TarefaDownload tarefaDownload = new TarefaDownload();
        tarefaDownload.execute(link);
    }

    private class TarefaDownload extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load = ProgressDialog.show(MainActivity.this, "Por favor, Aguarde...", "Baixando imagem...");
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap imagem = null;

            //baixar imagem
            try {
                Auxiliar aux = new Auxiliar();
                imagem = aux.baixarImagem(strings[0]);
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return imagem;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imvImagem.setImageBitmap(bitmap);
            load.dismiss();
        }
    }
}
