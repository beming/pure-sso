package com.aming.sso.model;

import java.io.Serializable;
import java.util.Arrays;

public class DwzRowItems implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1168623272996065557L;
	DwzRowItem[] items;
	public DwzRowItem[] getItems() {
		return items;
	}
	public void setItems(DwzRowItem[] items) {
		this.items = items;
	}
	public boolean isEmpty() {
		if(items == null || items.length <=0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "DwzRowItems [items=" + Arrays.toString(items) + "]";
	}
}
