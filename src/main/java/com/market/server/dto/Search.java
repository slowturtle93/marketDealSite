package com.market.server.dto;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Search")
public class Search extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;

	public Search() {
	}

	public Search(String key, Object val) {
		this.add(key, val);
	}
	
	public void add(String key, Object val) {
		super.put(key, val);
	}

	public void remove(String key) {
		super.remove(key);
	}

	public boolean getBoolean(String key) {
		return (boolean) (super.get(key));
	}

	public int getInt(String key) {
		return (int) (super.get(key));
	}

	public long getLong(String key) {
		return (long) (super.get(key));
	}

	public float getFloat(String key) {
		return (float) (super.get(key));
	}

	public double getDouble(String key) {
		return (double) (super.get(key));
	}

	public String getString(String key) {
		return (String) (super.get(key));
	}
	
	public void setRow(String type) {
		int pg   = this.getInt("pg");
		int pgSz = this.getInt("pgSz");
		
		if (pg > 0 && pgSz > 0) {
			this.add("strtRow", ((pg - 1) * pgSz));
			this.add("endRow", pgSz);
		}
	}
	
	public Search copy(Search search) {
		Search copy = new Search();
		Iterator<String> keys = search.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			copy.add(key, search.get(key));
		}
		return copy;
	}
}
