package com.pioneer.frame.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.pioneer.app.exception.DAOException;

/**
 * @name :
 * @author pionner
 * @version : v 1.0
 * 
 * <desc> description :数据访问的公共接口. </desc>
 * @package : com.mdcl.knowledge.comm.dao
 */
public interface IDAO {

	/**
	 * @desc: 设置数据访问的连接.
	 * @param conn :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public void setConnection(Connection conn);

	/**
	 * @desc:插入一条记录.
	 * @param obj
	 * @return
	 * @throws DAOException :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Object doInsertObj(Object obj) throws DAOException;

	/**
	 * @desc:更新一条记录
	 * @param obj
	 * @return
	 * @throws DAOException :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Object doUpdateObjByID(Object obj) throws DAOException;

	/**
	 * @desc:删除一条记录
	 * @param obj_id
	 * @throws DAOException :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public void doDeleteObjByID(Serializable obj_id) throws DAOException;

	/**
	 * @desc:清空表中的记录.
	 * @throws DAOException :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public void doDeleteAllObj() throws DAOException;

	/**
	 * @desc:根据条件删除表中的记录.
	 * @param whereCause
	 * @throws DAOException :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public void doDeleteObjByCondition(String whereCause) throws DAOException;

	/**
	 * @desc:查询表中的记录,并存储在list中
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public List doFindAllObjectInList() throws DAOException, Exception;

	/**
	 * @desc:检索出所有的记录存储到ｍａｐ中，ｋｅｙ为ｉｄ
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Map doFindAllObjectInMap() throws DAOException, Exception;

	/**
	 * @desc:根据ｉｄ查找一条记录．
	 * @param obj_id
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Object doFindObjectByID(Serializable obj_id) throws DAOException,
			Exception;

	/**
	 * @desc:根据条件查询数据.
	 * @param whereCause
	 * @return ｗｈｅｒｅ后面的子句
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public List doFindObjectsByCondition(String whereCause)
			throws DAOException, Exception;

	/**
	 * @desc:根据条件把查询的结果存储到
	 * @param whereCause
	 *            ｗｈｅｒｅ后面的子句
	 * @return ResultSet
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public ResultSet doFindRSByCondition(String whereCause)
			throws DAOException, Exception;

	/**
	 * @desc:根据条件把查询的结果存储到document中,包括字段的描述文件.
	 * @param showCols
	 *            要查找出的列数组
	 * @param whereCause
	 *            ｗｈｅｒｅ后面的子句
	 * @param whereCause type true:生成的doc带字段的描述 false 不带字段的描述
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Document doFindObjectsCDSByCondition(Object[] showCols,
			String whereCause,boolean type) throws DAOException, Exception;
	
	

	/**
	 * @desc: 把所有的数据查询到doucment中.
	 * @param showCols
	 *            要查找出的列数组
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Document doFindAllObjectCDS(Object[] showCols,boolean flg) throws DAOException,
			Exception;

	/**
	 * @desc:根据id查询一条记录,同时存储到document中.
	 * @param showCols
	 * @param obj_id
	 * @return
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public Document doFindObjectCDSByID(Object[] showCols, Serializable obj_id)
			throws DAOException, Exception;

	/**
	 * @desc:根据指定的列更新数据. 最后一列为要更新数据的ｉｄ号．最后的值是ｉｄ的值
	 * 
	 * @param cols
	 *            更新的列数组
	 * @param pars列的值数组．
	 * @throws DAOException
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	public void doUpdateObjectCols(Object[] cols, Object[] pars)
			throws DAOException, Exception;

}
