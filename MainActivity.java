package com.example.fakemailer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static Scanner scanner;
	EditText sender, receiver, subject, body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sender = (EditText) findViewById(R.id.editText1);
		receiver = (EditText) findViewById(R.id.editText2);
 		body = (EditText) findViewById(R.id.editText4);
	}

	public void SendMail(View a) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					URL url = new URL(
							"http://www.wscubetech.org/mobileteam/email.php?email_to="
									+ receiver.getText().toString()
									+ "&message=" + body.getText().toString()
									+ "&email_form="
									+ sender.getText().toString());
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);

					// starts query
					conn.connect();

					
					  InputStream stream=conn.getInputStream(); 
					  String data=convertStreamToString(stream);
					 // readAndParseJson(data);
					 
					stream.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		thread.start();
	}
	static String convertStreamToString(InputStream is) {
		scanner = new Scanner(is);
		Scanner s = scanner.useDelimiter("\\A");

		return s.hasNext() ? s.next() : "";

	}
}
