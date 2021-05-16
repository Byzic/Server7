package server;

import server.commands.*;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.FileManager;
import server.utility.RequestProcessing;

public class App {
    public static final int PORT = 8088;
    public static final String ENV_VARIABLE = "FLAT_FILE";
    public static void main(String[] args) {
        FileManager fileManager= new FileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(fileManager);
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
                new FilterNameCommand(collectionManager));
        RequestProcessing requestProcessing=new RequestProcessing(commandManager);
        Server server=new Server(PORT,requestProcessing);
        server.run();



    }

}
