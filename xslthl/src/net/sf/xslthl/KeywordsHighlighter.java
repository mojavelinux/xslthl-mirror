package net.sf.xslthl;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

class KeywordsHighlighter extends Highlighter {

    private Collection<String> keywords;
    private boolean ignoreCase = false;

    KeywordsHighlighter(Params params) {
	ignoreCase = params.isSet("ignoreCase");
	if (ignoreCase) {
	    keywords = new TreeSet<String>(new IgnoreCaseComparator());
	} else {
	    keywords = new TreeSet<String>();
	}
	params.load("keyword", keywords);
    }

    @Override
    boolean startsWith(CharIter in) {
	if (Character.isJavaIdentifierStart(in.current())
		&& (in.prev() == null || !Character.isJavaIdentifierPart(in
			.prev()))) {
	    return true;
	}
	return false;
    }

    @Override
    boolean highlight(CharIter in, List<Block> out) {
	while (!in.finished() && Character.isJavaIdentifierPart(in.current())) {
	    in.moveNext();
	}
	if (keywords.contains(in.getMarked())) {
	    out.add(in.markedToStyledBlock("keyword"));
	    return true;
	}
	return false;
    }

}
