package com.fanwei.sdk.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.fanwei.sdk.activity.HandlerCallback;
import com.fanwei.sdk.api.PaySdk;
import com.fanwei.sdk.bean.PayParam;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		PaySdk.init(this, PaySdk.PORTRAIT);
		List<Button> buttons = new ArrayList<Button>();
		Button b0 = (Button) findViewById(R.id.pay0_02);
		Button b1 = (Button) findViewById(R.id.pay1);
		Button b2 = (Button) findViewById(R.id.pay2);
		Button b4 = (Button) findViewById(R.id.pay4);
		Button b6 = (Button) findViewById(R.id.pay6);
		Button b8 = (Button) findViewById(R.id.pay8);
		Button b9 = (Button) findViewById(R.id.pay9);
		Button b10 = (Button) findViewById(R.id.pay10);
		buttons.add(b0);
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b4);
		buttons.add(b6);
		buttons.add(b8);
		buttons.add(b9);
		buttons.add(b10);
		for (Button button : buttons) {
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Button b = (Button) v;
					PayParam payParam = new PayParam();
					//金额(必填)
					payParam.setAmount(String.valueOf(b.getText()));
					//物品名称(必填)
					payParam.setGoodsname("购买游戏币");
					//商户那边生成的标识用户身份的Id(必填)
					payParam.setPlayerid("1234567890");
					//商户生成的订单号(必填)
					payParam.setPayid(Tools.setRand());
					//商户添加备注(可选)
					payParam.setRemark("我是备注!");
					//渠道(可选)
					payParam.setChannelid("360");
					
					// 调到多条支付通道界面
					PaySdk.startPay(MainActivity.this, payParam, new HandlerCallback() {
						@Override
						public void processResult(int resultCode, String result) {
							Log.i("result", result);
							Toast.makeText(MainActivity.this, "[resultCode=" + resultCode + "][result=" + result + "]",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			});
		}
	}
}
