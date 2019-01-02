package com.quizest.quizestapp.ModelPackage;

public class LeaderBoard {
    String name;
    int points;
    int rank;
    int image;

    public LeaderBoard(String name, int points, int rank, int image) {
        this.name = name;
        this.points = points;
        this.rank = rank;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getRank() {
        return rank;
    }

    public int getImage() {
        return image;
    }
}
