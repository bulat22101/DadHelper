package connector.grabber;

import entity.MaterialRecord;
import entity.TableConfig;
import exception.DadHelperException;

import java.util.List;

public interface TableGrabber {
    List<MaterialRecord> getMaterialRecords(TableConfig tableConfig) throws DadHelperException;

    default String fixString(String string, String patternToRemove) {
        return string.toUpperCase().replaceAll(patternToRemove, "");
    }
}
