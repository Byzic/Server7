package server.commands;

import common.data.Flat;
import exceptions.EmptyArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;


/**
 * Команда "insert". Добавляет новый элемент в коллекцию
 */
public class InsertCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InsertCommand(CollectionManager collectionManager){
        super("insert null {element}","добавить новый элемент с заданным ключом");
        this.collectionManager=collectionManager;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Flat flat){
        try{
            if (argument.isEmpty()) throw new EmptyArgumentException();
            int key =Integer.parseInt(argument);//доделать проверку на наличие уже такого ключа в коллекции
            collectionManager.Key(key);
            collectionManager.insertNew(key, flat);
            ResponseCreator.appendln("\u001B[37m"+"\u001B[33m"+"Элемент с заданным ключом успешно добавлен"+"\u001B[33m"+"\u001B[37m");
            return true;}
        catch (EmptyArgumentException e) {
            ResponseCreator.error("У этой команды должен быть аргумент(Ключ для добавления нового значения)" );
        }catch (NumberFormatException e){
            ResponseCreator.error("Формат введенного аргумента неверен. Он должен быть целым....");
        }catch(NullPointerException e){
            ResponseCreator.error("Элемент с таким ключом уже существует");
        }
        return false;
    }
}
