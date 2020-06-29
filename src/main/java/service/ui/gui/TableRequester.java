package service.ui.gui;

import entity.TableConfig;

public interface TableRequester {
    void tableGotten(String key, TableConfig tableConfig);
}
