package com.ymt.bwk.spi.sms;

import java.util.HashMap;
import java.util.Map;

public class AccountDemo {

	/**
	 * ΢���˺Ų�ѯ
	 */
	public static void account_api() {

		Map<String, String> para = new HashMap<String, String>();

		/**
		 * ΢���˺ŵĽӿ�UID
		 */
		para.put("uid", "<enter your UID>");

		/**
		 * ΢���˺ŵĽӿ�����
		 */
		para.put("pas", "<enter your UID Pass>");

		/**
		 * �ӿڷ������ͣ�json��xml��txt��Ĭ��ֵΪtxt
		 */
		para.put("type", "json");

		try {
			System.out
					.println(HttpClientHelper.convertStreamToString(
							HttpClientHelper
									.get("http://api.weimi.cc/2/account/balance.html",
											para), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] a) {

		// �����˺Ų�ѯAPI
		account_api();
		
		//ע�⣺���ϲ�����ʱ��������<>�����

	}
}
