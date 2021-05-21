package server.commands;

import common.User;
import common.data.Flat;
import exceptions.DatabaseManagerException;
import exceptions.IllegalDatabaseEditException;
import exceptions.IncorrectValueException;
import exceptions.PermissionDeniedException;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseCreator;

/**
 * команда "clear". Удаляет все элементы коллекции
 */
public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;
    public ClearCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager){
        super("clear","очистить коллекцию");
        this.collectionManager=collectionManager;
        this.databaseCollectionManager=databaseCollectionManager;
    }

    /**
     * Выполнение команды
     * @param argument аргумент
     * @return Статус выполнения программы
     */
    @Override
    public boolean execute(String argument, Flat flat, User user){
        try {
            if (!argument.isEmpty() || flat!= null)throw new IncorrectValueException();
            for (Flat flatt : collectionManager.getCollection().values()) {
                if (!flatt.getOwner().equals(user)) throw new PermissionDeniedException();
                if (!databaseCollectionManager.checkFlatUserId(flat.getID(), user)) throw new IllegalDatabaseEditException();;
            }
            databaseCollectionManager.clearCollection();
            collectionManager.clear();
            ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Коллекция удалена"+"\u001B[33m"+"\u001B[37m");
            return true;
        }catch (IncorrectValueException e) {
            ResponseCreator.error("У этой команды нет параметров! Необходимо ввести: clear");
        }catch (PermissionDeniedException exception) {
                ResponseCreator.error("Принадлежащие другим пользователям объекты доступны только для чтения!\n");
        } catch (DatabaseManagerException e) {
            ResponseCreator.error("Произошла ошибка при обращении к базе данных!");
        } catch (IllegalDatabaseEditException e) {
            ResponseCreator.error("Произошло нелегальное изменение объекта в базе данных!");
        }
        return false;
    }


}
