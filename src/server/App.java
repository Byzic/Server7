package server;

import server.commands.*;
import server.utility.*;

public class App {
    public static final int PORT = 8088;
    public static final String ENV_VARIABLE = "FLAT_FILE";
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseManager);
        DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseManager, databaseUserManager);
        CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);
        CommandManager commandManager = new CommandManager(
                new ExitCommand(collectionManager),
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ReplaceIfGreaterCommand(collectionManager),
                new ReplaceIfLowerCommand(collectionManager),
                new RemoveLowerKeyCommand(collectionManager),
                new RemoveAllByNumberOfRoomsCommand(collectionManager),
                new CountGreaterThanFurnishCommand(collectionManager),
                new FilterNameCommand(collectionManager),new ServerExitCommand(),
                new LoginCommand(databaseUserManager),
                new RegisterCommand(databaseUserManager));
        RequestManager requestManager=new RequestManager(commandManager);
        Server server=new Server(PORT,requestManager);
        server.run();
        databaseManager.closeConnection();

    }

}
