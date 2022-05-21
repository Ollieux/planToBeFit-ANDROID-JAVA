package com.example.plantobefit2;

public class Exercise {

    private int id;
    private String name;
    private String description;
    private String category;
    private String imageURL;
    private String gifURL;

    public Exercise(int id, String name, String description, String category, String imageURL, String gifURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.imageURL = imageURL;
        this.gifURL = gifURL;
    }

    @Override
    public String toString() {
        return "ExerciseClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", gifURL='" + gifURL + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getGifURL() {
        return gifURL;
    }

    public String getCategory() {
        return category;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
}
