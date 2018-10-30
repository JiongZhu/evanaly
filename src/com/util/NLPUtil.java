package com.util;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;

public class NLPUtil {
	
	public static List<String> getKeywordList(String document, int size){
		return MyTextRankKeyword.getKeywordList(document, size);
	}
	
	/**
	 * 返回字符串中的地址
	 */
	public static String getAddress(String s){
		
		List<Term> segment = HanLP.segment(s);
		for (Term term : segment) {
			if(term.nature==Nature.ns)
				return term.word;
		}
		return null;
		
	}
}
