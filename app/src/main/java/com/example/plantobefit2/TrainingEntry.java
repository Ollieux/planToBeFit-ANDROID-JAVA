package com.example.plantobefit2;


public class TrainingEntry {

//    private int id;
//    private boolean isExpanded;
//    private int note;
//    private int duration;
//    private int total_weight;
//    Date a;

    private int id;
    private float exhaust;
    private float focus;
    private float motivation;
    private float satisfaction;
    private long date_start;
    private float note;
    private long duration;
    private float weight_total;
    private String time_formated;
    private String weight_total_formated;

    public TrainingEntry(int id, float exhaust, float focus, float motivation, float satisfaction, long date_start, float note, long duration, float weight_total, String time_formated, String weight_total_formated) {
        this.id = id;
        this.exhaust = exhaust;
        this.focus = focus;
        this.motivation = motivation;
        this.satisfaction = satisfaction;
        this.date_start = date_start;
        this.note = note;
        this.duration = duration;
        this.weight_total = weight_total;
        this.time_formated = time_formated;
        this.weight_total_formated = weight_total_formated;
    }

    public float getExhaust() {
        return exhaust;
    }

    public float getFocus() {
        return focus;
    }

    public float getMotivation() {
        return motivation;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public long getDate_start() {
        return date_start;
    }

    public float getNote() {
        return note;
    }

    public long getDuration() {
        return duration;
    }

    public String getTime_formated() {
        return time_formated;
    }

    public String getWeight_total_formated() {
        return weight_total_formated;
    }

    private String formatWeightTotal() {
        float total = getWeight_total();
        if(total < 1000) {
            return total + "kg";
        } else {
            total /= 1000;
            return total + "t";
        }

    }

    private String formatDuration() {
        long duration = getDuration();
        if (duration >= 3600) {
            if (duration == 3600) {
                return "01:00:00";
            } else {

                long hour_quotient = duration / 60 / 60;
                long min_reminder = duration / 60 % 60;
                long sec_reminder = duration % 60;
                String ret = "";

                if(hour_quotient < 10) {
                    ret += "0";
                }
                ret += hour_quotient;
                ret += ":";

                if(min_reminder < 10) {
                    ret += "0";
                }
                ret += min_reminder;
                ret += ":";

                if(sec_reminder < 10) {
                    ret += "0";
                }

                ret += sec_reminder;

                return ret;

            }

        }

        else if (duration >= 60 && duration < 3600) {
            if (duration == 60) {
                return "00:01:00";
            } else {

                long sec_reminder = duration % 60;
                long min_quotient = duration / 60;
                String ret = "00:";

                if(min_quotient < 10) {
                    ret += "0";
                }
                ret += min_quotient;
                ret += ":";

                if(sec_reminder < 10) {
                    ret += "0";
                }

                ret += sec_reminder;

                return ret;
            }
        }
        String ret = "00:00:";
        if (duration < 10) {
            ret += "0";
        }
        ret += duration;
        return ret;
    }

    public float getWeight_total() {
        return weight_total;
    }

    public void setTime_formated() {
        this.time_formated = formatDuration();
    }

    public void setWeight_total_formated() {
        this.weight_total_formated = formatWeightTotal();
    }

    public void setExhaust(float exhaust) {
        this.exhaust = exhaust;
    }

    public void setFocus(float focus) {
        this.focus = focus;
    }

    public void setMotivation(float motivation) {
        this.motivation = motivation;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }

    public void setDate_start(long date_start) {
        this.date_start = date_start;
    }

    public void setNote() {
        this.note = (this.exhaust + this.motivation + this.satisfaction + this.focus) / 4;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    public void setWeight_total(float weight_total) {
        this.weight_total = weight_total;
    }

}

    /*public boolean isExpanded(boolean flag) { ###
        isExpanded  = flag; ###
    } */ // ###

    /*public int totalWeight(int pos) { ###
        int total = 0; ###


        for(HistoryEntry he: temp) { ###
            total += he.getReps() * he.getSets() * he.getWeight(); ###
        } ###
    } ##
} ###
     */ // ###
