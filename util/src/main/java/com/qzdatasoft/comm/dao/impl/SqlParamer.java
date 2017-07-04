package com.qzdatasoft.comm.dao.impl;

/**
 * ƴ��SQL ����
 * 
 * @author Administrator
 * 
 */
public class SqlParamer {

	private String sqlpattern;

	public SqlParamer(String sqlpattern) {
		this.sqlpattern = sqlpattern;
	}

	public void setString(String value) {
		int pos = this.sqlpattern.indexOf("?");
		this.sqlpattern = this.sqlpattern.substring(0, pos) + "'" + value + "'"
				+ this.sqlpattern.substring(pos + 1);

	}

	public void setName(String value) {
		int pos = this.sqlpattern.indexOf("?");
		this.sqlpattern = this.sqlpattern.substring(0, pos) + value
				+ this.sqlpattern.substring(pos + 1);

	}

	public void setInt(int value) {
		int pos = this.sqlpattern.indexOf("?");
		this.sqlpattern = this.sqlpattern.substring(0, pos) + value
				+ this.sqlpattern.substring(pos + 1);

	}
	/**
	 * ������������� ����Ϊdouble��float�����
	 * @param value
	 */
	public void setObject(Object value) {
		int pos = this.sqlpattern.indexOf("?");
		this.sqlpattern = this.sqlpattern.substring(0, pos) + value
				+ this.sqlpattern.substring(pos + 1);

	}

	public void setCurrent() {
		int pos = this.sqlpattern.indexOf("?");
		this.sqlpattern = this.sqlpattern.substring(0, pos) + "getdate()"
				+ this.sqlpattern.substring(pos + 1);

	}

	public String toString() {
		return sqlpattern;
	}

}
