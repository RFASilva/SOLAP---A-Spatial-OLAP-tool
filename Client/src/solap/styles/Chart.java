package solap.styles;

import solap.utils.ITriple;

abstract public class Chart extends Style {

    public Chart(String name) {
        super(name);
    }

    abstract public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend);
}
