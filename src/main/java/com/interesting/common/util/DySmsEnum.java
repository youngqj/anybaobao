package com.interesting.common.util;

public enum DySmsEnum {

	LOGIN_TEMPLATE_CODE("SMS_175435174","JEECG","code"),
	FORGET_PASSWORD_TEMPLATE_CODE("SMS_175435174","JEECG","code"),
	REGISTER_TEMPLATE_CODE("SMS_175430166","JEECG","code"),
	/**会议通知*/
	MEET_NOTICE_TEMPLATE_CODE("SMS_201480469","H5活动之家","username,title,minute,time"),
	/**我的计划通知*/
	PLAN_NOTICE_TEMPLATE_CODE("SMS_201470515","H5活动之家","username,title,time");

	/**
	 * 短信模板编码
	 */
	private String templateCode;
	/**
	 * 签名
	 */
	private String signName;
	/**
	 * 短信模板必需的数据名称，多个key以逗号分隔，此处配置作为校验
	 */
	private String keys;

	private DySmsEnum(String templateCode,String signName,String keys) {
		this.templateCode = templateCode;
		this.signName = signName;
		this.keys = keys;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public static DySmsEnum toEnum(String templateCode) {
		if(StringUtils.isNotEmpty(templateCode)){
			return null;
		}
		for(DySmsEnum item : DySmsEnum.values()) {
			if(item.getTemplateCode().equals(templateCode)) {
				return item;
			}
		}
		return null;
	}
}

