/**
 * 
 */
package za.co.sindi.faces.model;

import javax.faces.model.DataModel;

/**
 * @author Bienfait Sindi
 * @since 10 November 2015
 *
 */
public abstract class LazyDataModel<T> extends DataModel<T> {

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#isRowAvailable()
	 */
	@Override
	public boolean isRowAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowData()
	 */
	@Override
	public T getRowData() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#setRowIndex(int)
	 */
	@Override
	public void setRowIndex(int rowIndex) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#getWrappedData()
	 */
	@Override
	public Object getWrappedData() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.model.DataModel#setWrappedData(java.lang.Object)
	 */
	@Override
	public void setWrappedData(Object data) {
		// TODO Auto-generated method stub

	}
}
