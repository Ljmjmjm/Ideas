package com.example.ideas;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class RegisterActivity extends Activity implements OnClickListener,
		Callback {
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPKEY
	private static String APPKEY = "11b9d49fdf261";

	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPSECRET
	private static String APPSECRET = "c0e38f320b401703598a7b5f1d0d9084";

	// �ǳ�
	private EditText edt_register_name;

	// �ֻ��������
	private EditText edt_register_phone;

	// ����
	private EditText edt_register_pw;

	// ��֤�������
	private EditText edt_register_code;

	// ��ȡ��֤�밴ť
	private ButtonListener btn_register_code;

	// ע�ᰴť
	private ButtonListener btn_register;

	// �����˺�
	private TextView txt_register_login;

	private boolean ready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		edt_register_name = (EditText) findViewById(R.id.edt_register_name);
		edt_register_phone = (EditText) findViewById(R.id.edt_register_phone);
		edt_register_pw = (EditText) findViewById(R.id.edt_register_pw);
		edt_register_code = (EditText) findViewById(R.id.edt_register_code);
		btn_register_code = (ButtonListener) findViewById(R.id.btn_register_code);
		btn_register = (ButtonListener) findViewById(R.id.btn_register);
		txt_register_login = (TextView) findViewById(R.id.txt_register_login);

		final String phone = edt_register_phone.getText().toString();
		final String code = edt_register_code.getText().toString();

		initSDK();

		//�����ȡ��֤�밴ť
		btn_register_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.getVerificationCode("86", phone);
				btn_register_code.setClickable(false);
			}
		});

		//���ע�ᰴť
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.submitVerificationCode("86", phone, code);
				btn_register_code.setClickable(true);
			}
		});

		//��������˺Ű�ť
		txt_register_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,
						ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		final String name = edt_register_name.getText().toString();
		final String phone = edt_register_phone.getText().toString();
		final String pw = edt_register_pw.getText().toString();

		int event = msg.arg1;
		int result = msg.arg2;
		if (result == SMSSDK.RESULT_COMPLETE) {
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
				// ����ע��ɹ�
				Toast.makeText(this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
				btn_register_code.sendMessage(url, name, phone, pw);
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	private void initSDK() {
		// ��ʼ������SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);

		final Handler handler = new Handler(this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				handler.sendMessage(msg);
			}
		};
		// ע��ص������ӿ�
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;
	}

	protected void onDestroy() {
		if (ready) {
			// ���ٻص������ӿ�
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
