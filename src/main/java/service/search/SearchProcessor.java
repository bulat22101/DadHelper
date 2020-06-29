package service.search;

import entity.SearchTask;
import exception.DadHelperException;

public interface SearchProcessor {
    String proceedSearching(SearchTask searchTask) throws DadHelperException;
}
