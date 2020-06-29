package connector.user;

public interface ConsoleUserConnector {
    String readString();

    void showMessage(String message);

    default String readString(String message) {
        showMessage(message);
        return readString();
    }

    default int readInt() {
        return Integer.parseInt(readString());
    }

    default int readInt(String message) {
        return Integer.parseInt(readString(message));
    }
}
