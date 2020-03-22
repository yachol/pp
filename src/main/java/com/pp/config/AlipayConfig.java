package com.pp.config;


public class AlipayConfig {
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101700709420";
 
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLxoaRILHMcJcS5hqNqGTYYqIHSylH+bOTe7l+7Pev220q+qPprA4iEgQVJmrkZ6xRxACEPWFuddFXpkPVH8l8xTFtNIkjZuB2Nq+ydPpjkUH6oU5UYiSeAklwv0LfSeBt1uYSKZDAu9oe23sswsT0wel7m/GNtDogD3yEnP8LMggin5mlWv53AFd3ddSQ0PTQI9KrbjbD/ceOZsMowefNR8mUphXPzM3w5feIu+PLMClDSwQX8rWVa+w+UN7olmXY1YBwSBbsCnbmTdXgayZY5cizLrcFVcLHxC/sFTLPM4pCIeIvsPEdUiOuqi175vnx9sr/hQhuyC3bMoI3b05lAgMBAAECggEATwrsbEOl6Cu/cyOG3pbcuHVGlYNOi/JMN9nes0VEaEFZAdYNPYZb066GeoNoLVPnYbHKbP3dDD86LCvM7kf0hEdrRQtRhiCWTKNuRifatNWfDyGLDzMU6/n1F/pA0c6QSO0e3CR/RE628X5CsLRP9u11oL42ImFKAXlgfAngPK6ro2jae8AfUehQu7TNPf6wIx1F2tuTJJ/tW8EsLkJbkpX9Sw2K+3qY+BDprJ9nEypTOXukesSz3JmCWQzJFxW5sMQkzR8eN7EX5vU1pNDjJE3S9YBak0Ka/JXd3kdQNWipsSl7vt3bRR794lZaNv3rtu1VggTmV5zLkBzFUr6aHQKBgQDkZWlzMNyKMgZ5zGbu3DMHZp/mWUdx1NaHhaB6PWf83f7BSonqiTD3R7rXje1fCiUxW2hTwfOoe16YsH9Znv3DAY0BxNmAZZu68lUBY3/ES2oyYaNfe41sSOMXHbt6FoO7iBVqR6suIJrsD/+Tzt+Nb8QaJvidS0FoODU+kvgFFwKBgQCcqy+2kA0C5VuMOm8iJBPKKFG4KwuBV7ZOIn6ZU7FTpzxg7Tf8RRlRUDGAmv+stdC6caSv5BjFPZpBEo15PdySvjXB9gNd8KB1GF+4b7HdQs8jjDNt5MYzpVPeij90rWFIMmeA3OzDKrta1snYnWyICwWkz38wJt5mVYhp1mht4wKBgDPDPBvsNf9oCQmvpqB57WK/AnxNPaDx0NEUGuO5aQjpMySRhSJPh3ZznkfeFa8VeIpwIfvhmBxWIYFPdv1TyEVa459EAttTq/C5nURcTYeqY94ZPNKWCN5CwpzpkyS/V4m39MqKhaRFpXbcxVq1ZvsCGpqmvG/Br4g0PPT6kBVvAoGAXwfzrDCaw9vt+RRk3ve1TVXBpA3wL6fNvH1t/4FludFibYTXBd3AKrOTI2hhmBi9IKrYc5zPba0dSUWArT9Z8WURZLLefff8zKpZPZ367LSewvhDJfC0TRWG1yFStmqI7OsuH1ti/kuIU+SfkRaEg8zzwnHeu42IL9jIIzLcK3MCgYAZdBharx8qHwQQRAW97h3d/APD+iDziWvD5yLDtpujmCcO+0q0GqwbSyFR4oFYJ3ohtRKknCZFgMEnPQrsyiMJ+PHVMZfdp03o2fKD/P0fOCRlAq0qT8m8FznvPtbmuDaDT4RdDAdG1W55Ce/K+d05PXQgBXT/UFnGwQOA3MpK6w==";
 
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvjgvT2ZRgaaak0EtE2seAyf7VoYYpMOD5n8Pz3IQHg8gccay0DRsK56VKPaISmMdECs24+uGckOsO5S7FhtLbNTiLjwSmwd5jIm2fbL3KoV166DGz95/+OseeCTP2UuNbfNiVn5+75RmVJZ2BOogtiYU/8QUXDqyM+XVF32SFUR5VOhmlbk2Lt6pnUp+YaGos4eYZkjFh3DtQVwh3iqOZXubi33InTVLfnRcS5O0tyxpDFJgMaDBUk3eSDiNX4LOx/63aO5thlmeHRB3K5DTCUfKEXarT6HsjwrDzKYaexOLe8cKUNwf+wmXdmwLHv/S0wrS/qAqZCIIXjfqfDEqRQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost/alipay.trade.page.pay-JAVA-UTF-8/notify_url";
 
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	/*public static String return_url = "http://localhost/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";*/
    public static String return_url = "http://i28k668291.zicp.vip:54841/pp/User/alipay_callback";
	// 签名方式，注意这里，如果步骤设置的是RSA则用RSA
	public static String sign_type = "RSA2";
 
	// 字符编码格式
	public static String charset = "utf-8";
 
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
 
	// 支付宝网关
	public static String log_path = "C:\\";
 
}
