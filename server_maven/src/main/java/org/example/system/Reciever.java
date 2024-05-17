package org.example.system;

import org.example.commands.BaseCommand;
import org.example.exceptions.RootException;
import org.example.filelogic.WriterXML;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.recources.*;
import org.example.recources.comparators.MusicBandComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Reciever {
    public static String help(){
        StringBuilder sb = new StringBuilder();
        HashMap<String, BaseCommand> table = CommandManager.getCommandMap();
        for (String key: table.keySet()){
            sb.append(key + ": " + table.get(key).getDescription() + '\n');
        }
        return sb.toString();
    }

    public static String add(MusicBand musicBand){
        CollectionManager.add(musicBand.getName(), musicBand);
        return "Элемент был добавлен в коллекцию";
    }

    public static String info(){
        StringBuilder sb = new StringBuilder();
        sb.append(CollectionManager.getLinkedHashMap().getClass()  );
        sb.append('\n');
        sb.append(CollectionManager.getTimeOfCreate() );
        sb.append('\n');
        sb.append(CollectionManager.getLinkedHashMap().size() );
        return sb.toString();
    }
    public static String show(){
        StringBuilder sb = new StringBuilder();
        HashMap<String, MusicBand> table = CollectionManager.getLinkedHashMap();
        for (String key: table.keySet()){
            sb.append(CollectionManager.getLinkedHashMap().get(key)).append("\n");
        }
        if (sb.isEmpty()){
            return "Коллекция пустая";
        }
        return sb.toString();
    }

    public static String save() throws IOException, RootException {
        WriterXML.write(Server.getDataPath());
        return "";
    }

    public static String update_by_id(String line, MusicBand m1) {
        int currentID = Integer.parseInt(line.split(" ")[1]);
        HashMap<String, MusicBand> table = CollectionManager.getLinkedHashMap();
        for (String key : table.keySet()) {
            if (currentID == table.get(key).getId()) {
                CollectionManager.removeById(currentID);
                m1.setId(currentID);
                CollectionManager.add(m1.getName(), m1);
                return "Элемент был обновлен";
            }
        }
        return "Нет эллемента с таким id";
    }
    public static String remove_by_id(String line){
        int currentID = Integer.parseInt(line.split(" ")[1]);
        HashMap<String, MusicBand> table = CollectionManager.getLinkedHashMap();
        for (String key : table.keySet()) {
            if (currentID == table.get(key).getId()) {
                CollectionManager.remove(key);
                return "Элемент был удален из коллекции";
            }
        }
        return "Нет эллемента с таким id";
    }

    /**
     * Removes a music band from the collection by its label sales.
     *
     * @param salesInput The label sales of the music band to remove.
     * @return A message indicating whether the music band was successfully removed or not.
     */
    public static String removeAnyByLabel(String salesInput) {
        Integer sales = Integer.parseInt(salesInput);
        LinkedHashMap<String, MusicBand> sp = CollectionManager.getLinkedHashMap();
        for (String key: sp.keySet()){
            if (sp.get(key).getLabel().getSales() == sales) {
                CollectionManager.remove(key);
                return "Музыкальная группа удалена из коллекции";
            }
        }
        return "Не удалось найти музыкальную группу с таким значением выручки лэйбла";
    }

    /**
     * Prints the label sales of music bands in ascending order.
     *
     * @return A message indicating that the label sales have been successfully printed.
     */
    public static String printFieldAscendingLabel() {
        List<MusicBand> list = new ArrayList<>();

        for (Object musicBand : CollectionManager.getLinkedHashMap().values()) {
            list.add((MusicBand) musicBand);
        }
        MusicBandComparator comparator = new MusicBandComparator();
        list.sort(comparator);

        StringBuilder st = new StringBuilder();
        for (MusicBand m : list) {
            st.append(m.getLabel().getSales()).append("\n");
        }
        return st.toString();
    }

    /**
     * Filters and displays music bands by name containing the specified substring.
     *
     * @param str The substring to filter by.
     * @return A message indicating that music bands containing the specified substring have been displayed.
     */
    public static String filterContainsName(String str) {
        boolean haveAnyElem = false;
        StringBuilder st = new StringBuilder();
        for (Object m : CollectionManager.getLinkedHashMap().values()) {
            MusicBand musicBand = (MusicBand) m;
            if (musicBand.getName().contains(str)) {
                st.append(m.toString()).append("\n");
                haveAnyElem = true;
            }
        }
        if (!haveAnyElem) {
            return "Нет музыкальных групп с указанной подстрокой в названии";
        }
        return st.toString();
    }

    /**
     * Adds a new music band to the collection if its label sales are maximum.
     *
     * @return A message indicating whether the element was successfully added or not.
     */
    public static String addIfMax(MusicBand musibcand) {
        long salesOfBand = musibcand.getLabel().getSales();
        List<MusicBand> list = new ArrayList<>();

        for (Object musicBand : CollectionManager.getLinkedHashMap().values()) {
            list.add((MusicBand) musicBand);
        }
        MusicBandComparator comparator = new MusicBandComparator();
        list.sort(comparator);

        if (salesOfBand > list.get(list.size() - 1).getLabel().getSales()) {
            CollectionManager.add(musibcand.getName(), musibcand);
            return "элемент был успешно добавлен";
        } else {
            return "элемент не был добавлен";
        }
    }

    /**
     * Adds a new music band to the collection if its label sales are minimum.
     *
     * @return A message indicating whether the element was successfully added or not.
     */
    public static String addIfMin(MusicBand musibcand) {
        long salesOfBand = musibcand.getLabel().getSales();
        List<MusicBand> list = new ArrayList<>();

        for (Object musicBand : CollectionManager.getLinkedHashMap().values()) {
            list.add((MusicBand) musicBand);
        }
        MusicBandComparator comparator = new MusicBandComparator();
        list.sort(comparator);

        if (salesOfBand < list.get(0).getLabel().getSales()) {
            CollectionManager.add(musibcand.getName(), musibcand);
            return "элемент был успешно добавлен";
        } else {
            return "элемент не был добавлен";
        }
    }
}
