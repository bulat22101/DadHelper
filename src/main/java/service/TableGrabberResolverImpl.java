package service;

import connector.grabber.TableGrabber;
import entity.TableType;

import java.util.Map;

public class TableGrabberResolverImpl implements TableGrabberResolver {
    private final Map<TableType, TableGrabber> tableGrabberMap;

    public TableGrabberResolverImpl(Map<TableType, TableGrabber> tableGrabberMap) {
        this.tableGrabberMap = tableGrabberMap;
    }

    @Override
    public TableGrabber getTableGrabber(TableType tableType) {
        return tableGrabberMap.get(tableType);
    }
}
