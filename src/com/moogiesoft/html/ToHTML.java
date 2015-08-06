package com.moogiesoft.html;
import java.util.HashMap;
import java.util.List;

public interface ToHTML {
	public String toHtml(HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts);
}
