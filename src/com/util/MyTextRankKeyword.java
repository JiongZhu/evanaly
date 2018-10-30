package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.TextRankKeyword;
/**
 * 
 * 自定义一个关键字提取类  
 * 为了过滤一些不需要的词
 * 
 */
public class MyTextRankKeyword extends TextRankKeyword {
	
	int  nKeyword = 2;
	public static List<String> getKeywordList(String document, int size)
    {
		MyTextRankKeyword textRankKeyword = new MyTextRankKeyword();
		textRankKeyword.nKeyword = size;
        return textRankKeyword.getKeyword(document);
    }
	
	
	/**
     * 提取关键词
     * @param content
     * @return
     */
    public List<String> getKeyword(String content)
    {
        Set<Map.Entry<String, Float>> entrySet = getTermAndRank(content, nKeyword).entrySet();
        List<String> result = new ArrayList<String>(entrySet.size());
        for (Map.Entry<String, Float> entry : entrySet)
        {
            result.add(entry.getKey());
        }
        return result;
    }

	public boolean shouldInclude(Term term) {
		// 除掉停用词
        if (term.nature == null) return false;
        String nature = term.nature.toString();
        char firstChar = nature.charAt(0);
        switch (firstChar)
        {
            case 'm':
            case 'b':
            case 'c':
            case 'e':
            case 'o':
            case 'p':
            case 'q':
            case 'u':
            case 'y':
            case 'z':
            case 'r':
            case 'w':
            {
                return false;
            }
            default:
            {
            	if(term.nature==Nature.ns)
            		return false;
            	else if (term.word.trim().length() > 1 && !CoreStopWordDictionary.contains(term.word))
                {
                    return true;
                }
            }
            break;
        }

        return false;
	}
}
