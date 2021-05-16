package server.utility;

import common.Request;
import common.Response;
import common.ResponseCode;
import common.data.Flat;

//Класс для обработки запроса клиента и формирования ответа
public class RequestProcessing {
    private CommandManager commandManager;
    public RequestProcessing(CommandManager commandManager){
        this.commandManager=commandManager;
    }
    public Response processing(Request request){
        return new Response(executeCommand(request.getCommandName(),request.getArgument(), request.getObjectArgument()),ResponseCreator.getAndClear());

    }
    public ResponseCode executeCommand(String commandName, String argument, Flat flat){
        switch (commandName){
            case "help" :
                if (!commandManager.help(argument,flat)){
                    return ResponseCode.ERROR;
                }

                break;
            case"show":
                if (!commandManager.show(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "info":
                if (!commandManager.info(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "insert":
                if (!commandManager.insert(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "update":
                if (!commandManager.update(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "remove_key":
                if (!commandManager.removeKey(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "clear":
                if (!commandManager.clear(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "save":
                if (!commandManager.save(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "execute_script":
                if (!commandManager.executeScript(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "replace_if_greater":
                if (!commandManager.replaceIfGreater(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "replace_if_lower":
                if (!commandManager.replaceIfLower(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "remove_all_by_number_of_rooms":
                if (!commandManager.removeAllByNumber(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "count_greater_than_furnish":
                if (!commandManager.countFurnish(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "filter_starts_with_name":
                if (!commandManager.filterName(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            case "exit":
                if (!commandManager.exit(argument,flat)){
                    return ResponseCode.ERROR;
                }
                break;
            default:
                ResponseCreator.appendln("Команда '" + commandName+ "' не найдена. Наберите 'help' для справки.");
                return ResponseCode.ERROR;
            }
        return ResponseCode.OK;
    }
}
