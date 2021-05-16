package server.commands;


import common.data.Flat;
import exceptions.IncorrectValueException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;

/**
 * Класс команды "save". Сохраняет коллекцию в файл
 */
public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        super("save","сохранить коллекцию в файл");
        this.collectionManager=collectionManager;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Flat flat){
        try {
            if (!argument.isEmpty())throw new IncorrectValueException();
        collectionManager.saveToFile();
        return true;
        }
        catch (IncorrectValueException e) {
            ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: save");
        }
        return false;
    }
}
