package service.ui.gui;

import service.search.SearchProcessor;
import service.ui.UserInterface;

public class GraphicalUserInterface implements UserInterface {
    private final SearchProcessor searchProcessor;

    public GraphicalUserInterface(SearchProcessor searchProcessor) {
        this.searchProcessor = searchProcessor;
    }

    @Override
    public void launch() {
        new MainWindow(searchProcessor);
    }
}
