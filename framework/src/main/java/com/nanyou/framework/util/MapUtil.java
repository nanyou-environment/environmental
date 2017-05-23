package com.nanyou.framework.util;


public class MapUtil {
	/**
	 * 根据经纬度，获取两点间的距离
	 * 
	 * @author zhijun.wu
	 * @param lng1
	 *            经度
	 * @param lat1
	 *            纬度
	 * @param lng2
	 * @param lat2
	 * @return
	 * 
	 * @date 2011-8-10
	 */
	public static double distanceByLngLat(double lng1, double lat1,
			double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;// 取WGS84标准参�?椭球中的地球长半�?单位:m)
		s = Math.round(s * 10000) / 10000;

		return s;
	}

	/**
	 * 说明�?
	 * 
	 * @author zhijun.wu
	 * @param args
	 * @throws Exception
	 * 
	 * @date 2008-5-16
	 */
	public static void main(String[] args) throws Exception {
		//32.041695,118.784351
		//32.041404,118.794994
		System.out.println(distanceByLngLat(118.784351, 32.041695, 118.794994,
				32.041404));
	}
}
