package connector.grabber;

import entity.MaterialRecord;
import entity.TableConfig;

import java.util.List;

public interface TableGrabber {
    List<MaterialRecord> getMaterialRecords(TableConfig tableConfig);

    default String fixString(String string, String patternToRemove) {
        return string.toUpperCase().replaceAll(patternToRemove, "");
    }
}
