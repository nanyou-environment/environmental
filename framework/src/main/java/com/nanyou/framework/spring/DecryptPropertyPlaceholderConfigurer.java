package com.nanyou.framework.spring;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.nanyou.framework.util.AESUtils;

/**
 * 1，处理配置文件中的加密字符串 2，根据环境读取不同的环境变量
 * 
 * @author Administrator
 * 
 */
public class DecryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer implements InitializingBean {

	private Log log = LogFactory
			.getLog(DecryptPropertyPlaceholderConfigurer.class);

	private static final String PRODUCTION_MODE = "production.mode";
	// 缓存�?��的属性配�?
	private Properties properties;

	/**
	 * 重写父类方法，解密指定属性名对应的属性�?
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		String result;
		if (isEncryptPropertyVal(propertyValue)) {
			String tmp = propertyValue.substring(8, propertyValue.length() - 1);
			result = AESUtils.Decrypt(tmp);// 调用解密方法
		} else {
			result = propertyValue;
		}
		log.debug(propertyName + ":" + propertyValue);
		return result;
	}

	/**
	 * 判断属�?值是否需要解密，encrypt(xxxxxx)
	 * 
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptPropertyVal(String propertyValue) {
		if (propertyValue.startsWith("encrypt(") && propertyValue.endsWith(")")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return properties.getProperty(PRODUCTION_MODE);
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		Properties mergeProperties = super.mergeProperties();
		// 根据路由原则，提取最终生效的properties
		this.properties = new Properties();
		// 获取路由规则,系统属�?设置mode优先
		String mode = System.getProperty(PRODUCTION_MODE);
		if (StringUtils.isEmpty(mode)) {
			String str = mergeProperties.getProperty(PRODUCTION_MODE);
			mode = str != null ? str : "online";
		}
		properties.put(PRODUCTION_MODE, mode);
		String[] modes = mode.split(",");
		Set<Entry<Object, Object>> es = mergeProperties.entrySet();
		for (Entry<Object, Object> entry : es) {
			String key = (String) entry.getKey();
			int idx = key.lastIndexOf('_');
			String realKey = idx == -1 ? key : key.substring(0, idx);
			if (!properties.containsKey(realKey)) {
				Object value = null;
				for (String md : modes) {
					value = mergeProperties.get(realKey + "_" + md);
					if (value != null) {
						properties.put(realKey, value);
						break;
					}
				}
				if (value == null) {
					value = mergeProperties.get(realKey);
					if (value != null) {
						properties.put(realKey, value);
					} else {
						throw new RuntimeException(
								"impossible empty property for " + realKey);
					}
				}
			}
		}
		return properties;
	}

	/**
	 * �?��此方法给�?��的业�?
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return resolvePlaceholder(key, properties);
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}
}
