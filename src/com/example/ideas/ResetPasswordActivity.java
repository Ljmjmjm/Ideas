package com.example.ideas;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity implements OnClickListener,
		Callback {
	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPKEY
	private static String APPKEY = "11b9d49fdf261";

	// ��д�Ӷ���SDKӦ�ú�̨ע��õ���APPSECRET
	private static String APPSECRET = "c0e38f320b401703598a7b5f1d0d9084";

	// �ֻ�
	private EditText edt_reset_phone;

	// ��֤��
	private EditText edt_reset_code;

	// ��ȡ��֤��
	private ButtonListener btn_reset_code;

	// ����
	private EditText edt_reset_pw;

	// ���
	private Button btn_reset;

	private boolean ready;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);

		edt_reset_phone = (EditText) findViewById(R.id.edt_reset_phone);
		edt_reset_code = (EditText) findViewById(R.id.edt_reset_code);
		btn_reset_code = (ButtonListener) findViewById(R.id.btn_reset_code);
		edt_reset_pw = (EditText) findViewById(R.id.edt_reset_pw);
		btn_reset = (Button) findViewById(R.id.btn_reset);

		final String phone = edt_reset_phone.getText().toString();
		final String code = edt_reset_code.getText().toString();
		final String pw = edt_reset_code.getText().toString();

		initSDK();
		
		//�����ȡ��֤�밴ť
		btn_reset_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SMSSDK.getVerificationCode("86", phone);
				btn_reset_code.setClickable(false);
			}
		});
		
		//�����ɰ�ť
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "�ֻ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(pw.equals("")){
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				SMSSDK.submitVerificationCode("China", phone, code);
				btn_reset_code.setClickable(true);
			}
		});
	}

	private void initSDK() {
		// ��ʼ������SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);

		final Handler handler = new Handler((Callback) this);
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

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		final String phone = edt_reset_phone.getText().toString();
		final String code = edt_reset_code.getText().toString();
		final String pw = edt_reset_pw.getText().toString();
		
		int event = msg.arg1;
		int result = msg.arg2;
		if (result == SMSSDK.RESULT_COMPLETE) {
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
				// �޸ĳɹ�
				Toast.makeText(this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
				btn_reset_code.sendMessage(url, phone , pw);
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}
}
