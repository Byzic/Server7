package server.commands;

import common.User;
import common.data.Flat;
import exceptions.EmptyArgumentException;
import server.utility.CollectionManager;
import server.utility.ResponseCreator;


/**
 * Класс команды "update". Заменяет элемент по ключу
 */
public class UpdateCommand extends AbstractCommand {
    CollectionManager collectionManager;
    public UpdateCommand(CollectionManager collectionManager){
        super("update id {element}","обновить значение элемента коллекции, id которого равен заданному");

        this.collectionManager=collectionManager;
    }
    /**
     * Выполнение команды
     * @param argument аргумент
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Flat flat, User user){
        try{
            if (argument.isEmpty()) throw new EmptyArgumentException();
            Integer id =Integer.parseInt(argument);
            collectionManager.checkId(id);
            int key = collectionManager.getKeyById(id);
            collectionManager.update(key,flat);
            ResponseCreator.appendln("Элемент коллекции с id равным "+argument+" был успешно заменен");
            return true;
        }catch (EmptyArgumentException e) {
            ResponseCreator.error("У этой команды должен быть аргумент(ключ для удаления элементов)" );
        }catch (NumberFormatException e){
            ResponseCreator.error("Формат введенного аргумента неверен . Он должен быть целым.");
        } catch (NullPointerException e){
            ResponseCreator.error("Элемента с таким id не существует. Невозможно обновить значение несуществующего элемента коллекции");
        }
        return false;}
}
