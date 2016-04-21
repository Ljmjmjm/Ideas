package com.example.ideas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// �ֻ���
	private EditText edt_login_phone;

	// ����
	private EditText edt_login_pw;

	// ��¼
	private ButtonListener btn_login;

	// ��������
	private TextView txt_login_forgetpw;

	// ע��
	private TextView txt_login_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edt_login_phone = (EditText) findViewById(R.id.edt_login_phone);
		edt_login_pw = (EditText) findViewById(R.id.edt_login_pw);
		btn_login = (ButtonListener) findViewById(R.id.btn_login);
		txt_login_forgetpw = (TextView) findViewById(R.id.txt_login_forgetpw);
		txt_login_register = (TextView) findViewById(R.id.txt_login_register);
		
		final String phone = edt_login_phone.getText().toString();
		final String pw = edt_login_pw.getText().toString();
		
		//�����¼��ť
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "�ֻ��Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(pw.equals("")){
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(btn_login.login(u, phone, pw)){
					Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,);
					startActivity(intent);
				}else
					Toast.makeText(getApplicationContext(), "�������", Toast.LENGTH_SHORT).show();
			}
		});
		
		//����������밴ť
		txt_login_forgetpw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
		
		//���ע�ᰴť
		txt_login_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
}
