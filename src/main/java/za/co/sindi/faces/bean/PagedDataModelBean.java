/**
 * 
 */
package za.co.sindi.faces.bean;

import java.util.Collection;
import java.util.List;

import javax.faces.model.ArrayDataModel;
import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ScalarDataModel;

/**
 * @author Bienfait Sindi
 * @since 07 December 2014
 *
 */
public abstract class PagedDataModelBean<E> extends BaseMessageBean {
	
	private int startPage;
	private int pageSize;
	private long totalPages;
	private long totalRows;
	private DataModel<E> data;
	
	/**
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}
	
	/**
	 * @param startPage the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the totalPages
	 */
	public long getTotalPages() {
		return totalPages;
	}
	
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	
	/**
	 * @return the totalRows
	 */
	public long getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the data
	 */
	public DataModel<E> getData() {
		if (data == null) {
			refresh();
		}
		
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(DataModel<E> data) {
		this.data = data;
	}
	
	protected void setData(E[] data) {
		setData(new ArrayDataModel<E>(data));
	}
	
	protected void setData(Collection<E> data) {
		if (data instanceof List) {
			setData((List<E>)data);
		}
		
		setData(new CollectionDataModel<E>(data));
	}
	
	protected void setData(List<E> data) {
		setData(new ListDataModel<E>(data));
	}
	
	protected void setData(E data) {
		setData(new ScalarDataModel<E>(data));
	}

	public void next() {
		startPage += 1;
		if (startPage > totalPages) {
			startPage = (int) totalPages;
		}
		
		refresh();
	}
	
	public void previous() {
		startPage -= 1;
		if (startPage < 0) {
			startPage = 0;
		}
		
		refresh();
	}
	
	public abstract void refresh();
}
