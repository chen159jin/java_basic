/**
 * ˵������ҳ���󣬹�����
 * ��д�ߣ�лƽ
 * ���ڣ�Jul 27, 2007
 * ����ǿ�ǿƼ���Ȩ���С�
 */
package com.qzdatasoft.comm.dao.impl;

import java.util.Collection;

/**
 * <p>
 * Title:��ҳ����
 * <p>
 * Description:���ݷ�ҳDTO��������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: ����ǿ�ǿƼ���չ���޹�˾
 * </p>
 * 
 */
public class Pagination
{
    // ��¼��
    private Collection rowSet;

    // ��¼����С
    private long rowSize = 0;

    // �ڼ�ҳ
    private int pageIndex = 1;

    // ҳ��С
    private int pageSize = 30;

    /**
     * �Ƿ�����ҳ
     */
    private boolean hasFirst = false;

    /**
     * �Ƿ���ǰһҳ
     */
    private boolean hasPrevious = false;

    /**
     * �Ƿ�����һҳ
     */
    private boolean hasNext = false;

    /**
     * �Ƿ���βҳ
     */
    private boolean hasLast = false;

    /**
     * ��ҳ��
     */
    private int totalPages = 1;

    /**
     * ��һҳ����
     */
    private int nextPage = 1;

    /**
     * ��һҳ����
     */
    private int previousPage = 1;

    /**
     * Ĭ�Ϲ��췽������ʹ�ø��ǵĹ��췽��
     */
    public Pagination()
    {

    }

    /**
     * �����ҳ���󷽷�
     * 
     * @param rowSet
     * @param rowSize
     * @param pageIndex
     * @param pageSize
     */
    public Pagination(Collection rowSet, long rowSize, int pageIndex,
	    int pageSize)
    {
	this.rowSet = rowSet;
	this.rowSize = rowSize;
	this.pageIndex = pageIndex;
	this.pageSize = pageSize;
    }

    /**
     * ������ҳ��
     * 
     * @return long
     */
    public long getTotalPages()
    {
	return totalPages;
    }

    /**
     * ������ҳ��
     * 
     * @param totalPages
     */
    public void setTotalPages(int totalPages)
    {
	this.totalPages = totalPages;
    }

    public int getPageIndex()
    {
	return pageIndex;
    }

    /**
     * ���õڼ�ҳ
     * 
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex)
    {
	this.pageIndex = pageIndex;
    }

    /**
     * ��ø�ҳ��¼����С
     *  
     * @return int
     */
    public int getPageSize()
    {
	return pageSize;
    }

    /**
     * ���ø�ҳ��¼����С
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize)
    {
	this.pageSize = pageSize;
    }

    /**
     * ��ñ�ҳ��¼��
     * 
     * @return Collection
     */
    public Collection getRowSet()
    {
	return rowSet;
    }

    /**
     * ���ñ�ҳ��¼��
     * 
     * @param rowSet
     */
    public void setRowSet(Collection rowSet)
    {
	this.rowSet = rowSet;
    }

    /**
     * ����ܼ�¼��
     * 
     * @return long
     */
    public long getRowSize()
    {
	return rowSize;
    }

    /**
     * �����ܼ�¼��
     * 
     * @param rowSize
     */
    public void setRowSize(long rowSize)
    {
	this.rowSize = rowSize;
    }

    /**
     * �Ƿ��е�һҳ
     * 
     * @return boolean
     */
    public boolean isHasFirst()
    {
	return hasFirst;
    }

    /**
     * �����е�һҳ
     * 
     * @param hasFirst
     */
    public void setHasFirst(boolean hasFirst)
    {
	this.hasFirst = hasFirst;
    }

    /**
     * �Ƿ������һҳ
     * 
     * @return boolean
     */
    public boolean isHasLast()
    {
	return hasLast;
    }

    /**
     * ���������һҳ
     * 
     * @param hasLast
     */
    public void setHasLast(boolean hasLast)
    {
	this.hasLast = hasLast;
    }

    /**
     * �Ƿ�����һҳ
     * 
     * @return boolean
     */
    public boolean isHasNext()
    {
	return hasNext;
    }

    /**
     * ��������һҳ��
     * 
     * @param hasNext
     */
    public void setHasNext(boolean hasNext)
    {
	this.hasNext = hasNext;
    }

    /**
     * �Ƿ�����һҳ
     * 
     * @return boolean
     */
    public boolean isHasPrevious()
    {
	return hasPrevious;
    }

    /**
     * ��������һҳ
     * 
     * @param hasPrevious
     */
    public void setHasPrevious(boolean hasPrevious)
    {
	this.hasPrevious = hasPrevious;
    }

    /**
     * �����һҳҳ��
     * 
     * @return int
     */
    public int getNextPage()
    {
	return nextPage;
    }

    /**
     * ������һҳҳ��
     * 
     * @param nextPage
     */
    public void setNextPage(int nextPage)
    {
	this.nextPage = nextPage;
    }

    /**
     * �����һҳҳ��
     * 
     * @return int
     */
    public int getPreviousPage()
    {
	return previousPage;
    }

    /**
     * ������һҳҳ��
     * 
     * @param previousPage
     */
    public void setPreviousPage(int previousPage)
    {
	this.previousPage = previousPage;
    }

    /**
     * ������ҳ��Ϣ
     */
    public void buildPagination()
    {
	// ������ҳ��
	if (rowSize % pageSize > 0)
	{
	    setTotalPages((int) (rowSize / pageSize + 1));
	} else
	{
	    if (rowSize > 0)
		this.setTotalPages((int) (rowSize / pageSize));
	}
	// �Ƿ�����һҳ����һҳ���룬�Ƿ�����ҳ
	if (pageIndex > 1)
	{
	    hasFirst = true;
	    hasPrevious = true;
	    previousPage = pageIndex - 1;
	} else
	{
	    previousPage = 1;
	}
	if (pageIndex < totalPages)
	{
	    hasLast = true;
	    hasNext = true;
	    nextPage = pageIndex + 1;
	} else
	{
	    nextPage = totalPages;
	}
    }
}
