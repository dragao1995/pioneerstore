package com.pioneer.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.shiftone.cache.Cache;
import org.shiftone.cache.policy.fifo.FifoCacheFactory;

/**
 * @name : 数据缓存类
 * @URL :
 * @URL_Parameter :
 * @author pionner
 * @version : v 1.0
 * @description :
 */

public class DatCache {
	private static DatCache _inst;

	public static String CACHE_GOOD_SALE = "tc_good_request";//求购区

	public static String CACHE_GOOD_PURCHASE = "tc_good_purchase";//购物区

	public static String CACHE_USER = "tc_user";//用户信息区
	
	public static String CACHE_COMM = "tc_comm";//其他数据缓存
	
	private static String[] CTNames = { CACHE_COMM, CACHE_GOOD_SALE,CACHE_GOOD_PURCHASE, CACHE_USER }; // Cache

	private HashMap hCaches = new HashMap();

	private HashMap hCacheCTDatName = new HashMap();

	private DatCache() {
	};

	/**
	 * @desc:取道缓存类的对象.
	 * @return :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public synchronized static DatCache inst() {
		if (_inst == null) {
			_inst = new DatCache();
			_inst.readyCaches();
		}
		return _inst;
	}

	/**
	 * @desc: :准备cache组
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	private void readyCaches() {
		for (int i = 0; i < CTNames.length; i++) {
			hCaches.put(CTNames[i], new FifoCacheFactory().newInstance(
					CTNames[i], 1000 * 60 * 60 * 12, 500));
		}
	}

	/**
	 * @desc:取道缓存在组中的数据.
	 * @param ctName
	 * @param datName
	 * @return :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public Object getDat(String ctName, String datName) {
		Cache aCache = (Cache) hCaches.get(ctName);
		if (aCache == null)
			return null;
		return aCache.getObject(datName);
	}

	/**
	 * @desc:缓存数据.
	 * @param datName
	 * @return :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public Object getDat(String datName) {
		return getDat(CACHE_COMM, datName);
	}

	/**
	 * @desc:缓存数据到指定组中..
	 * @param ctName
	 * @param datName
	 * @param o
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public void setDat(String ctName, String datName, Object o)
			throws Exception {
		Cache aCache = (Cache) hCaches.get(ctName);
		if (aCache == null) {
			aCache = new FifoCacheFactory().newInstance(ctName,
					1000 * 60 * 60 * 12, 50);
			hCaches.put(ctName, aCache);
		}
		String ctDatName = ctName + ":" + datName;
		if (o != null) {
			aCache.addObject(datName, o);
			hCacheCTDatName.put(ctDatName, null);
		} else {
			aCache.remove(datName);
			hCacheCTDatName.remove(ctDatName);
		}
	}

	/**
	 * @desc:缓存数据到默认组中.
	 * @param datName
	 * @param o
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public void setDat(String datName, Object o) throws Exception {
		setDat(CACHE_COMM, datName, o);
	}

	public void clearDat(String groupName, String key) {
		Cache aCache = (Cache) this.hCaches.get(groupName);
		if (null != aCache)
			aCache.remove(key);
	}

	/**
	 * @desc:清除cache的方法
	 * 
	 * @param groups
	 *            :Map key为组的名称,对应的值为list
	 * @auther : pionner
	 * @date : 2007-9-17
	 */
	public void clearDat(Map groups) {
		if (null != groups) {
			String groupKey = null, key = null;
			Iterator it = groups.keySet().iterator();
			Cache aCache = null;
			while (it.hasNext()) {
				groupKey = (String) it.next();
				aCache = (Cache) this.hCaches.get(groupKey);
				List keysList = (List) groups.get(groupKey);
				if (null != keysList && keysList.size() > 0) {

					for (int i = 0; i < keysList.size(); i++) {
						key = (String) keysList.get(i);
						aCache.remove(key);
					}
				}
			}
		}

	}

	/**
	 * @desc:清除组下的所有记录.
	 * @param groupName
	 *            :组名称
	 * @auther : pionner
	 * @date : 2007-9-17
	 */
	public void clearDat(String groupName) {
		Cache aCache = (Cache) this.hCaches.get(groupName);
		if (null != aCache) {
			aCache.clear();
		}
	}

	/**
	 * @desc:清除缓存.
	 * @param cacheKeyMatch :
	 * @auther : pionner
	 * @date : 2007-9-10
	 */
	public void clearCache(CacheKeyMatch cacheKeyMatch) {
		String ctName, ctDatName, datName;
		ArrayList toClearList = new ArrayList();
		for (Iterator iter = hCaches.keySet().iterator(); iter.hasNext();) {
			ctName = (String) iter.next();
			for (Iterator iterator = hCacheCTDatName.keySet().iterator(); iterator
					.hasNext();) {
				ctDatName = (String) iterator.next();
				// if (ctName.equals( comdzy.strUtil.getFPartStr(ctDatName,":")
				// )) {
				// datName = comdzy.strUtil.getBPartStr(ctDatName,":");
				// if (cacheKeyMatch.isKeyMatch(ctName, datName)) {
				// //�������,��ɾ��
				// String[] item = {ctName, datName};
				// toClearList.add(item);
				// }
				// }
			}
		}

		String[] item = null;
		// System.out.println(toClearList.toString());
		for (int i = 0; i < toClearList.size(); i++) {
			item = (String[]) toClearList.get(i);
			try {
				setDat(item[0], item[1], null);
			} catch (Exception e) {
			}
		}
	}

	// ��ɵ�ǰ��Cache��ݵ�XML�ĵ�

	public static void main(String[] args) {
		try {
			DatCache.inst().setDat("Key1", "KeyStr1");
			DatCache.inst().setDat("Key2", "KeyStr2");
			System.out.println(DatCache.inst().getDat("Key1"));
			DatCache.inst().clearCache(new CacheKeyMatch() {

				public boolean isKeyMatch(String ctName, String datName) {
					System.out.println("ctName=" + ctName + "  datName="
							+ datName);
					return datName.equals("Key2");
				}
			});
			System.out.println(DatCache.inst().getDat("Key1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
