package pucp.edu.pe.sap_backend.SimulatedAnnealing.AnotherApproach.S.A;

import pucp.edu.pe.sap_backend.Genetico.Vehiculo;
import pucp.edu.pe.sap_backend.Ruta.Cell;



public class Time implements Comparable<Time>{
    private int day;
    private int hours;
    private int minutes;
    private int seconds;
    private static final double SPEED = 50.0;

    public Time(int time){
        day = 1;
        seconds = time % 60;
        minutes = (time / 60) % 60;
        hours = (time / 3600) % 60;
        day += time / 86400;
    }

    public Time(String string){
        setDayHourMinute(string);
    }

    public Time(Time time){
        setDay(time.getDay());
        setHours(time.getHours());
        setMinutes(time.getMinutes());
        setSeconds(time.getSeconds());
    }

    public Time(long time){
        setTime(time);
    }

    public Time(int day, int hours){
        this.day = day;
        this.hours = hours;
        this.minutes = 0;
        this.seconds = 0;
    }

    public int getDay() {
        return day;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setTime(Time time){
        setDay(time.getDay());
        setHours(time.getHours());
        setMinutes(time.getMinutes());
        setSeconds(time.getSeconds());
    }

    public long getTimeLong(){
        return (day * 86400) + (hours * 3600) + (minutes * 60) + seconds;
    }

    public long getOnlyTimeLong(){
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    public void setTime(long seconds){
        this.day = 1;
        this.seconds = (int)(seconds % 60);
        this.minutes = (int)((seconds / 60) % 60);
        this.hours = (int)((seconds / 3600) % 60);
        this.day += (int)(seconds / 86400);
    }

    public void setDayHourMinute(String string){
        var parts = string.split(":");
        day = Integer.parseInt(parts[0]);
        hours = Integer.parseInt(parts[1]);
        minutes = Integer.parseInt(parts[2]);
        seconds = 0;
    }

    public void addTime(long seconds){
        this.seconds += (int)(seconds % 60);
        this.minutes += (int)((seconds / 60) % 60);
        this.hours += (int)((seconds / 3600) % 24);
        this.day += (int)(seconds / 86400);

        if(this.seconds >= 60){
            this.seconds -= 60;
            this.minutes++;
        }
        if(this.minutes >= 60){
            this.minutes -= 60;
            this.hours++;
        }
        if(this.hours >= 24){
            this.hours -= 24;
            this.day++;
        }
    }

    public void addTime(Time time){
        addTime(time.getTimeLong());
    }

    public void removeTime(long seconds){
        this.seconds -= (int)(seconds % 60);
        this.minutes -= (int)((seconds / 60) % 60);
        this.hours -= (int)((seconds / 3600) % 60);
        this.day -= (int)(seconds / 86400);


        if(this.seconds < 0){
            this.seconds += 60;
            this.minutes--;
        }
        if(this.minutes < 0){
            this.minutes += 60;
            this.hours--;
        }
        if(this.hours < 0){
            this.hours += 24;
            this.day--;
        }
    }

    public void removeTime(Time time){
        removeTime(time.getTimeLong());
    }

    public boolean withinInterval(Time start, Time end){
        if(compareTo(start) >= 0 && compareTo(end) <= 0) return true;
        return false;
    }

    public long difference(Time time){
        long d = (time.getDay() - getDay()) * 86400;
        long h = (time.getHours() - getHours()) * 3600;
        long m = (time.getMinutes() - getMinutes()) * 60;
        long s = time.getSeconds() - getSeconds();
        return Math.abs(d + h + m + s);
    }

    public Time withoutDays(){
        var alt = new Time(this);
        alt.setDay(0);
        return alt;
    }

    public int compareOnlyTime(Time time){
        if(this.getOnlyTimeLong() > time.getOnlyTimeLong()) return 1;
        if(this.getOnlyTimeLong() < time.getOnlyTimeLong()) return -1;
        return 0;
    }

    @Override
    public int compareTo(Time time){
        if(this.getTimeLong() > time.getTimeLong()) return 1;
        if(this.getTimeLong() < time.getTimeLong()) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != Time.class) return false;

        var t = (Time)obj;
        var first = getDay() == t.getDay() && getHours() == t.getHours();
        var second = getMinutes() == t.getMinutes() && getSeconds() == t.getSeconds();
        return first && second;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(day) + Integer.hashCode(hours)
                + Integer.hashCode(minutes) + Integer.hashCode(seconds);
    }

    @Override
    public String toString(){
        return String.format("{Day %02d: (%02d:%02d:%02d)}", day, hours, minutes, seconds);
    }

    public static long toLong(Vehiculo vehicle, Cell m, Cell n){
        return (long)(3600.0 * m.manhattan(n) / SPEED);
    }
}
