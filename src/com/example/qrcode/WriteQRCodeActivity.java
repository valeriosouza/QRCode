package com.example.qrcode;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class WriteQRCodeActivity extends Activity {

	private ImageView image;
	private EditText nome;
	private EditText sobrenome;
	private EditText nascimento;
	private Button gerar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_qrcode);
		nome = (EditText) findViewById(R.id.write_nome);
		sobrenome = (EditText) findViewById(R.id.write_sobrenome);
		nascimento = (EditText) findViewById(R.id.write_nascimento);
		gerar = (Button) findViewById(R.id.write_gerar);
		gerar.setOnClickListener(configurarOnGerarClickListener());
		image = (ImageView) findViewById(R.id.imageView1);
		preencherComponentes();
	}

	private String bitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	private Bitmap stringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	private void preencherComponentes() {
		String json = getIntent().getStringExtra("usuario");
		if (json != null) {
			UsuarioDTO usu = transformJsonToUsuarioDTO(json);
			nome.setText(usu.getNome());
			sobrenome.setText(usu.getSobrenome());
			nascimento.setText(usu.getNascimento());
		}
	}

	private OnClickListener configurarOnGerarClickListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				gerarQRCode(nome.getText().toString(), sobrenome.getText()
						.toString(), nascimento.getText().toString());
			}
		};
	}

	private void gerarQRCode(String nome, String sobrenome, String nascimento) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			UsuarioDTO usu = new UsuarioDTO(nome, sobrenome, nascimento);
			BitMatrix byteMatrix = qrCodeWriter.encode(
					transformUsuarioDTOToJson(usu), BarcodeFormat.QR_CODE, 400,
					400);
			image.setImageBitmap(toBitmap(byteMatrix));
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String transformUsuarioDTOToJson(UsuarioDTO usuario)
			throws Exception {
		return new Gson().toJson(usuario, UsuarioDTO.class);
	}

	private UsuarioDTO transformJsonToUsuarioDTO(String json) {
		return new Gson().fromJson(json, UsuarioDTO.class);
	}

	private Bitmap toBitmap(BitMatrix matrix) {
		int height = matrix.getHeight();
		int width = matrix.getWidth();
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
			}
		}
		return bmp;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
