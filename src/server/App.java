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
                new InsertCommand(collectionManager,databaseCollectionManager),
                new UpdateCommand(collectionManager,databaseCollectionManager),
                new RemoveKeyCommand(collectionManager,databaseCollectionManager),
                new ClearCommand(collectionManager,databaseCollectionManager),
                new ExecuteScriptCommand(),
                new ReplaceIfGreaterCommand(collectionManager,databaseCollectionManager),
                new ReplaceIfLowerCommand(collectionManager,databaseCollectionManager),
                new RemoveLowerKeyCommand(collectionManager,databaseCollectionManager),
                new RemoveAllByNumberOfRoomsCommand(collectionManager,databaseCollectionManager),
                new CountGreaterThanFurnishCommand(collectionManager),
                new FilterNameCommand(collectionManager),
                new ServerExitCommand(),
                new LoginCommand(databaseUserManager),
                new RegisterCommand(databaseUserManager));
        RequestManager requestManager=new RequestManager(commandManager);
        Serverr server=new Serverr(PORT,requestManager);
        server.run();
        databaseManager.closeConnection();

    }

}
