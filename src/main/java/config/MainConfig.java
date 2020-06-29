package config;

import connector.grabber.CsvTableGrabber;
import connector.grabber.TableGrabber;
import connector.grabber.XlsxTableGrabber;
import connector.user.ConsoleUserConnector;
import connector.user.ConsoleUserConnectorImpl;
import connector.writer.ReportWriter;
import connector.writer.XlsxReportWriter;
import entity.TableType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.TableGrabberResolver;
import service.TableGrabberResolverImpl;
import service.search.SearchProcessor;
import service.search.SearchProcessorImpl;
import service.ui.ConsoleUserInterface;
import service.ui.UserInterface;
import service.ui.gui.GraphicalUserInterface;

import java.io.FileInputStream;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class MainConfig {
    @Bean
    UserInterface graphicalUserInterface(SearchProcessor searchProcessor){
        return new GraphicalUserInterface(searchProcessor);
    }

    @Bean
    public ReportWriter reportWriter() {
        return new XlsxReportWriter();
    }

    @Bean
    public TableGrabber xlsxTableGrabber() {
        return new XlsxTableGrabber();
    }

    @Bean
    public TableGrabber csvTableGrabber() {
        return new CsvTableGrabber();
    }

    @Bean
    public TableGrabberResolver tableGrabberResolver(
            TableGrabber xlsxTableGrabber,
            TableGrabber csvTableGrabber
    ) {
        Map<TableType, TableGrabber> tableGrabberEnumMap = new EnumMap<>(TableType.class);
        tableGrabberEnumMap.put(TableType.CSV, csvTableGrabber);
        tableGrabberEnumMap.put(TableType.XLSX, xlsxTableGrabber);
        return new TableGrabberResolverImpl(tableGrabberEnumMap);
    }

    @Bean
    public SearchProcessor searchProcessor(
            TableGrabberResolver tableGrabberResolver,
            ReportWriter reportWriter) {
        return new SearchProcessorImpl(tableGrabberResolver, reportWriter);
    }

    @Bean
    public ConsoleUserConnector consoleUserConnector() {
        return new ConsoleUserConnectorImpl(System.in, System.out);
    }

    @Bean
    public UserInterface consoleUserInterface(
            ConsoleUserConnector consoleUserConnector,
            SearchProcessor searchProcessor
    ) {
        return new ConsoleUserInterface(consoleUserConnector, searchProcessor);
    }
}
