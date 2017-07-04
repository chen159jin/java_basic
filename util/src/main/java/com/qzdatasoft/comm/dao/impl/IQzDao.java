package com.qzdatasoft.comm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;

import com.qzdatasoft.comm.web.Pagination;

/**
 * DAO �ӿڣ����е���Ҫ�����ݿ�򽻵���Javabean�࣬�����ɹ�����TableToClass���ɣ����߷���ָ���ĸ�ʽ
 * 
 * @author yangzhuang
 * 
 */
public interface IQzDao extends java.io.Serializable {
	/**
	 * ���ɹ涨���ȵ�����������ֵΪ�ַ�������
	 * 
	 * @param length
	 * @return
	 */
	String getSequence(int length);

	/**
	 * ��������������ֵΪint����
	 * 
	 * @return
	 */
	int getSequence1();

	/**
	 * ��������������ֵΪString����
	 * 
	 * @return
	 */
	String getSequence();

	/**
	 * ��������������ֵΪint����Ҫ�Լ�����seq
	 * 
	 * @param s
	 *            ���
	 * @return
	 */
	int getSequence(String seq);

	/**
	 * ��ȡ������DAO��ֱ�ӻ�ȡ���� <br>
	 * ����˵������2����������ȫ���ɹ������������ݽ� <br>
	 * TransactionStatus ts =
	 * qzDao.getTransactionStatus(); try { XX;(ĳ������) xx2;���ڶ�������)
	 * qzDao.commitTransaction(ts); } catch (Exception e) {
	 * qzDao.rollbackTransaction(ts); throw new
	 * ServiceException(e.getMessage()); } <br>
	 * ���Ҫ�ֹ����������벻Ҫʹ��spring
	 * �Զ����������spring��ȡ���÷���������Ȼ���ֹ���������
	 * 
	 * @return
	 */
	TransactionStatus getTransactionStatus();

	/**
	 * �ع��������׳��쳣֮ǰ�ع�����
	 * 
	 * @param status
	 */
	void rollbackTransaction(TransactionStatus status);

	/**
	 * ִ�������ڴ�����ҵ���߼�֮���ύ����
	 * 
	 * @param status
	 */
	void commitTransaction(TransactionStatus status);

	/**
	 * ����Ԥ������ָ����ʽ��Javabean��Ȼ�󱣴浽���ݿ⣨Javabean����ʹ�ù�����TableToClass���ɣ�
	 * 
	 * @param object
	 */
	void save(Object object);

	// ���Ӷ�clob��֧��
	void saveByClob(Object object, String clobValue);

	/**
	 * ɾ�����󣬶����ʽ��������ѭ�涨��ʽ����Javabean����ʹ�ù�����TableToClass���ɣ�
	 * 
	 * @param object
	 */
	void delete(Object object);

	/**
	 * ��������ɾ����֧����������ɾ����<br>
	 * primaryKeyValue��˳������Javabean�е�����������˳��һ��
	 * 
	 * @param clazz
	 * @param primaryKeyValue
	 *            ����ֵ��������ʽ��˳����涨����ϣ�
	 */
	void deleteByPrimaryKey(Class clazz, Object[] primaryKeyValue);

	/**
	 * ��������ɾ��������ֻ����һ����
	 * 
	 * @param clazz
	 * @param primaryKeyValue
	 *            ����
	 */
	void deleteByPrimaryKey(Class clazz, Object primaryKeyValue);

	/**
	 * ����һ������ <br>
	 * ��׼�ĸ��²��裺 �ȴ����ݿ��л�ȡ��һ����¼��Ȼ�����Ҫ�޸ĵ�����set��ȥ��Ȼ����ִ�д˷��� �����Ϳ��Ա���ĳЩ������ʧ
	 * 
	 * @param object
	 * @throws Exception
	 */
	void update(Object object);

	/**
	 * ����һ������(ֻ���²�Ϊ�յ�����) <br>
	 * ��׼�ĸ��²��裺 �ȴ����ݿ��л�ȡ��һ����¼��Ȼ�����Ҫ�޸ĵ�����set��ȥ��Ȼ����ִ�д˷���
	 * �����Ϳ��Ա���ĳЩ������ʧ
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void updateNotNull(Object object);

	/**
	 * clob ֧��
	 * @param object
	 * @param clobValue
	 */
	void updateByClob(Object object, String clobValue);

	/**
	 * ��ȡ��Class���еĶ���
	 * 
	 * @param clazz
	 * @return
	 */
	List findAll(Class clazz);

	/**
	 * ������Ҫʹ�ø÷���,����˳����ڲ��ȶ���<br>
	 * ����������ѯ���֧࣬������������ѯ,���������������ֵ��˳������JavaBean �е�˳��һ��
	 * 
	 * @param clazz
	 * @param object
	 *            ����ֵ��������ʽ������˳����Javabean�е�˳��һ�£�
	 * @return
	 */
	Object findByPrimaryKey(Class clazz, Object[] object);

	/**
	 * ����������ѯ���ֻ֧࣬�ֵ�������ֵ
	 * 
	 * @param clazz
	 *            ��
	 * @param object
	 *            ����ֵ
	 * @return
	 */
	Object findByPrimaryKey(Class clazz, Object object);

	/**
	 * ͨ��SQL����ѯ����ҳ,�õ������������������ʽ��֯, ���sql����order by ,�벻Ҫ�ڴ���oderStrs
	 * 
	 * @param sql
	 *            SQL���
	 * @param pageIndex
	 *            ��ʼҳ��
	 * @param pageSize
	 *            ҳ���С
	 * @param args
	 *            ��ѯ������ֵ
	 * @param orderStrs
	 *            �ɱ���������������ֶ�ʱʹ��
	 * @return Pagination ��ҳ����
	 * @return
	 */
	public Pagination getSqlPagination(String sql, final Integer pageIndex, final Integer pageSize,
			final List args, List orderStrs);

	/**
	 * ͨ��SQL����ѯ����ҳ,�õ�����������JavaBean����ʽ��֯ �����ڼ򵥵�Javabean�� ��Ҫ�ֹ�����sql
	 * 
	 * @param sql
	 *            SQL���
	 * @param pageIndex
	 *            ��ʼҳ��
	 * @param pageSize
	 *            ҳ���С
	 * @param args
	 *            ��ѯ������ֵ
	 * @param orderStrs
	 *            �ɱ���������������ֶ�ʱʹ��
	 * @return Pagination ��ҳ����
	 * @return
	 */
	public Pagination getSqlPaginationToBean(Class clazz, String sql, final Integer pageIndex,
			final Integer pageSize, final List args, List orderStrs);

	/**
	 * ��ȡ��ҳ��Ϣ ͨ��SQL����ѯ����ҳ,�õ�����������JavaBean����ʽ��֯ �����ڼ򵥵�Javabean ����sql
	 * 
	 * @param sql
	 *            SQL���
	 * @param pageIndex
	 *            ��ʼҳ��
	 * @param pageSize
	 *            ҳ���С
	 * @param args
	 *            ��ѯ������ֵ
	 * @param orderStrs
	 *            �ɱ���������������ֶ�ʱʹ��
	 * @return Pagination ��ҳ����
	 * @return
	 */
	public Pagination getSqlPaginationToBean(Class clazz, final Integer pageIndex, final Integer pageSize,
			List orderStrs);

	/**
	 * ��ѯ���ݣ�����List �����Ϊ��װ�������List ֧��ͨ���������˳������sql��һ�� ͨ���������飬���Եõ����ݡ�
	 * ����ǵò�ѯ��˳�򣬲�����ȷ�������� �˷������ܱȽϺã���execSqlQueryToMap��
	 * 
	 * @param sqlStr
	 * @param list
	 *            ͨ����Ĳ�����˳������SQLһ�£����Ҹ���һ��
	 * @return
	 */
	public List execSqlQueryToArrays(String sqlStr, List list) throws SQLException;

	public List execSqlQueryToArrays(String sqlStr, List list, RowMapper rowMapper) throws SQLException;

	/**
	 * ��ѯ���� �������Ϊ��װ�������List ͨ���������飬���Եõ����ݡ� ����ǵò�ѯ��˳�򣬲�����ȷ��������
	 * �˷������ܱȽϺã���execSqlQueryToMap��
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List execSqlQueryToArrays(String sqlStr);

	/**
	 * ��ѯ���ݣ������Ϊ��װ��Map��List ֧��ͨ���������˳������sql��һ��
	 * ��Map�У�����������ֵ�Ǹ�������ֵ��ͨ������Map�����Եõ����ݡ� �˷������ܱ�execSqlQueryToArrays��
	 * 
	 * @param sqlStr
	 * @param list
	 * @return
	 */
	public List execSqlQueryToMap(String sqlStr, List list);

	/**
	 * ��ѯ���� �������Ϊ��װ��Map��List ��Map�У�����������ֵ�Ǹ�������ֵ��ͨ������Map�����Եõ����ݡ�
	 * �˷������ܱ�execSqlQueryToArrays��
	 * 
	 * @param sqlStr
	 * @return
	 */
	public List execSqlQueryToMap(String sqlStr);

	/**
	 * ԭ��̬��Sql��ѯ��������Ƿ�װ���Class �ļ���List
	 * 
	 * @param clazz
	 * @param sqlStr
	 *            ��ѯsql
	 * @return
	 */
	public List execSqlQuery(Class clazz, String sqlStr);

	/**
	 * ԭ��̬��Sql��ѯ��֧��ͨ�������ѯ�������ݻ��װ��class����List
	 * 
	 * @param clazz
	 * @param sqlStr
	 * @param list
	 * @return
	 */
	public List execSqlQuery(Class clazz, String sqlStr, List list);

	/**
	 * ԭ��̬SQL�޸���� ��֧��������ɾ�����޸ģ�sql��֧��ͨ���������list���ǲ�����������sql�е�˳��һ��
	 * 
	 * @param sql
	 * @param list
	 *            �����Ĳ�����˳��͸�����ͨ������Ӧ
	 * @return
	 */
	public Integer execSqlUpdate(String sql, List list);

	/**
	 * ԭ��̬SQL�޸���䣬֧��������ɾ�����޸�
	 * 
	 * @param sql
	 * @return
	 */
	public Integer execSqlUpdate(String sql);

	/**
	 * ִ�д洢����
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int execProcedure(String sql, List args);

	/**
	 * ��ʵ����󲻴���ʱ�������Ե�Ψһ��
	 * 
	 * @param clazz
	 *            POJO��������
	 * @param property
	 *            ����
	 * @param value
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value);

	/**
	 * ��ʵ����󲻴���ʱ�������Ե�Ψһ��
	 * 
	 * @param clazz
	 *            Javabean��������
	 * @param property
	 *            ����
	 * @param value
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 */
	public boolean validateOnly(String clazz, String property, Integer value);

	/**
	 * ��ʵ����󲻴���ʱ�������Ե�Ψһ��
	 * 
	 * @param clazz
	 *            POJO��������
	 * @param property
	 *            ����
	 * @param value
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, Long value);

	/**
	 * ��ʵ��������ʱʹ�ø÷�������֤Ψһ��
	 * 
	 * @param clazz
	 *            POJO��������
	 * @param property
	 *            ����
	 * @param value
	 *            ���ֵ
	 * @param pk
	 *            ��������
	 * @param pkValue
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, String pkValue);

	/**
	 * ��ʵ��������ʱʹ�ø÷�������֤Ψһ��
	 * 
	 * @param clazz
	 *            POJO��������
	 * @param property
	 *            ����
	 * @param value
	 *            ���ֵ
	 * @param pk
	 *            ��������
	 * @param pkValue
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, Integer pkValue);

	/**
	 * ��ʵ��������ʱʹ�ø÷�������֤Ψһ��
	 * 
	 * @param clazz
	 *            POJO��������
	 * @param property
	 *            ����
	 * @param value
	 *            ���ֵ
	 * @param pk
	 *            ��������
	 * @param pkValue
	 *            ����ֵ
	 * @return boolean trueΨһ��false��Ψһ
	 * @throws DataAccessException
	 */
	public boolean validateOnly(String clazz, String property, String value, String pk, Long pkValue);

	/**
	 * sql������
	 * 
	 * @param sql
	 * @param size
	 *            ��¼�Ĵ�С
	 * @param args
	 *            ������飬ÿ�������Ӧһ��?��
	 */
	public int[] execSqlUpdateBatch(String sql, final int size, final Object[]... args);

	/**
	 * ������
	 * 
	 * @param sql
	 * @param size
	 *            ��¼�Ĵ�С
	 * @param args
	 *            ��¼����, 2ά�����¼��ŷ�ʽ���£�
	 *            {{arg0,arg1,arg2},{arg0,arg1,arg2},{arg0,arg1,arg2}...},
	 *            ����ÿ��һά�����Ӧһ������
	 */
	public int[] execSqlUpdateBatchFixed(String sql, final int size, final Object[]... args);

	/**
	 * ��ȡJDBC����
	 * 
	 * @return Connection
	 */
	public Connection getConnection();
	
	/**
	 * ���ݼ�ֵ���в�ѯ<br>
	 * param �� �ֶ� �� ֵ ����ʽ
	 * @param clazz
	 * @param object
	 * @return
	 */
	public List findByKeyValue(Class clazz, Map param);
}
