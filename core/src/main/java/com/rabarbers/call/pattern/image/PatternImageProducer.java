package com.rabarbers.call.pattern.image;

import com.rabarbers.htmlgen.html.Element;
import com.rabarbers.htmlgen.html.svg.Svg;
import com.rabarbers.call.pattern.Pattern;

public abstract class PatternImageProducer<P extends Pattern> {
    public abstract Element produce(Pattern pattern);
}
