package com.nanyou.weixin.utils;

import java.util.Properties;

public class AppConfig {

	//卖废品
	private static final String SELL_WASTE = "SELL.WASTE";
	//参考价格
	private static final String REFERENCE_PRICE = "REFERENCE.PRICE";
	//积分商城
	private static final String INTEGRAL_MALL = "INTEGRAL.MALL";

    private static Properties p = new Properties();

    static {
        try {
            p.load(AppConfig.class.getResourceAsStream("/appconfig.properties"));
        } catch (Exception e) {
        }
    }

    
    public static String getSellWaste() {
        return p.getProperty(SELL_WASTE);
    }
    
    public static String getReferencePrice() {
        return p.getProperty(REFERENCE_PRICE);
    }
    
    public static String getIntegralMall() {
        return p.getProperty(INTEGRAL_MALL);
    }
    
}
