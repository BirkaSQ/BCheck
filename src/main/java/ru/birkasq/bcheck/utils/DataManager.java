package ru.birkasq.bcheck.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataManager {
    public static String date = new SimpleDateFormat("dd.MM.yyyy_HH:mm").format(Calendar.getInstance().getTime());
}
