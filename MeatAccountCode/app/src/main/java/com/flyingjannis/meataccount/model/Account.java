package com.flyingjannis.meataccount.model;


import java.util.Calendar;

public class Account {      //DateSaver speichert mittlerweile viel unnötigen Stuff, da die Payments mit Millisekunden geregelt werden!
    private int balance;
    private int weeklyAmount;
    private int payments;
    private WeekStamp[] weeks = new WeekStamp[1];


    private DateSaver creationDate;
    private long creationDateMillis;

    public Account(int weeklyAmount) {
        this.weeklyAmount = weeklyAmount;
        this.balance = weeklyAmount;
        Calendar calendar = Calendar.getInstance();
        long minutes = calendar.get(Calendar.MINUTE);        //Rundet die Millis auf die nächste volle Stunde ab!
        long seconds = calendar.get(Calendar.SECOND);
        long millisToFullHour = minutes * 60000 + seconds * 1000;

        payments = 0;
        weeks[payments] = new WeekStamp(payments);
        creationDateMillis = calendar.getTimeInMillis() - millisToFullHour;
        creationDate = new DateSaver(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR),
                calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.HOUR_OF_DAY));
    }

    public void changeCreationHour(int i) {
        creationDate.setHour(creationDate.getHour() + i);
        if(creationDate.getHour() > 23) {                                            //Falls die Veränderung der Stunde, gleichzeitig den Tag ändert!
            creationDate.setHour(creationDate.getHour() - 24);                       //ACHTUNG: An Jahr und Woche wurde nicht gedacht!!!
            if(creationDate.getDayOfWeek() == 7) {
                creationDate.setDayOfWeek(1);
            } else {
                creationDate.setDayOfWeek(creationDate.getDayOfWeek() + 1);
            }
        } else if(creationDate.getHour() < 0) {
            creationDate.setHour(creationDate.getHour() + 24);
            if (creationDate.getDayOfWeek() == 1) {
                creationDate.setDayOfWeek(7);
            } else {
                creationDate.setDayOfWeek(creationDate.getDayOfWeek() - 1);
            }
        }
    }

    public void addMeatDay(int amount) {
        if(payments + 1 > weeks.length) {           //Neue WeekStamps müssen hinzugefügt werden!
            WeekStamp[] tmp = weeks;
            weeks = new WeekStamp[payments + 1];
            for(int i = 0; i < tmp.length; i++) {
                weeks[i] = tmp[i];
            }
            for(int n = tmp.length; n < weeks.length; n++) {
                weeks[n] = new WeekStamp(n);
            }
        }
        //Der Creation Day ist immer das Feld 0 in jeder Woche!

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = ((((calendar.get(Calendar.DAY_OF_WEEK) - creationDate.getDayOfWeek()) % 7) + 7) % 7);
        weeks[payments].setDay(dayOfWeek, amount);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getWeeklyAmount() {
        return weeklyAmount;
    }

    public void setWeeklyAmount(int weeklyAmount) {
        this.weeklyAmount = weeklyAmount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public WeekStamp[] getWeeks() {
        return weeks;
    }

    public void setWeeks(WeekStamp[] weeks) {
        this.weeks = weeks;
    }

    public DateSaver getCreationDate() {
        return creationDate;
    }


    public long getCreationDateMillis() {
        return creationDateMillis;
    }

    public void setCreationDateMillis(long creationDateMillis) {
        this.creationDateMillis = creationDateMillis;
    }

}
