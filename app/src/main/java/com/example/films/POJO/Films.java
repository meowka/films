package com.example.films.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Films {
    @SerializedName("films")
    @Expose
    public List<ApiResponse> films = null;

    public List<ApiResponse> getFilms() {
        return films;
    }

    public void setFilms(List<ApiResponse> films) {
        this.films = films;
    }

    public static class ApiResponse implements Comparable<ApiResponse> {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("localized_name")
        @Expose
        public String localizedName;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("year")
        @Expose
        public Integer year;
        @SerializedName("rating")
        @Expose
        public Object rating;
        @SerializedName("image_url")
        @Expose
        public Object imageUrl;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("genres")
        @Expose
        public List<String> genres = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLocalizedName() {
            return localizedName;
        }

        public void setLocalizedName(String localizedName) {
            this.localizedName = localizedName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public Object getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Object imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        @Override
        public int compareTo(ApiResponse apiResponse) {

            return  localizedName.compareTo(apiResponse.localizedName);
        }
    }
}

