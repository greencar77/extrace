package com.rabarbers.call.pattern.image;

import com.rabarbers.call.html.svg.Svg;
import com.rabarbers.call.pattern.Pattern;

public abstract class PatternImageProducer<P extends Pattern> {
    public abstract Svg produce(P pattern);
}
