package connector.user;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConsoleUserConnectorImpl implements Closeable, ConsoleUserConnector {
    private final Scanner inputScanner;
    private final PrintWriter writer;

    public ConsoleUserConnectorImpl(InputStream inputStream, OutputStream outputStream) {
        this.inputScanner = new Scanner(inputStream);
        this.writer = new PrintWriter(outputStream);
    }

    @Override
    public void close() {
        inputScanner.close();
        writer.close();
    }

    @Override
    public String readString() {
        return inputScanner.nextLine();
    }

    @Override
    public void showMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}
