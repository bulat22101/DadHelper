package service;

import connector.grabber.TableGrabber;
import entity.TableType;

public interface TableGrabberResolver {
    TableGrabber getTableGrabber(TableType tableType);
}
