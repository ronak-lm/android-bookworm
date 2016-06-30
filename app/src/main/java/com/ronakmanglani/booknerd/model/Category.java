package com.ronakmanglani.booknerd.model;

import java.util.ArrayList;

public class Category {

    // Attributes
    private String displayName;
    private String listName;

    // Getters
    public String getDisplayName() {
        return displayName;
    }
    public String getListName() {
        return listName;
    }

    // Constructor
    public Category(String displayName, String listName) {
        this.displayName = displayName;
        this.listName = listName;
    }

    // Static list of categories
    private static ArrayList<Category> categoryList;
    public static ArrayList<Category> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<>();
            categoryList.add(new Category("Fiction", "combined-print-and-e-book-fiction"));
            categoryList.add(new Category("Nonfiction", "combined-print-and-e-book-nonfiction"));
            categoryList.add(new Category("Advice and How-To", "advice-how-to-and-miscellaneous"));
            categoryList.add(new Category("Animals", "animals"));
            categoryList.add(new Category("Business", "business-books"));
            categoryList.add(new Category("Celebrities", "celebrities"));
            categoryList.add(new Category("Crime and Punishment", "crime-and-punishment"));
            categoryList.add(new Category("Culture", "culture"));
            categoryList.add(new Category("Education", "education"));
            categoryList.add(new Category("Espionage", "espionage"));
            categoryList.add(new Category("Expeditions and Adventure", "expeditions-disasters-and-adventures"));
            categoryList.add(new Category("Food and Diet", "food-and-fitness"));
            categoryList.add(new Category("Games and Activities", "games-and-activities"));
            categoryList.add(new Category("Graphic Books", "paperback-graphic-books"));
            categoryList.add(new Category("Health and Fitness", "health"));
            categoryList.add(new Category("Humor", "humor"));
            categoryList.add(new Category("Love and Relationships", "relationships"));
            categoryList.add(new Category("Manga", "manga"));
            categoryList.add(new Category("Parenthood and Family", "family"));
            categoryList.add(new Category("Politics and History", "hardcover-political-books"));
            categoryList.add(new Category("Race and Civil Rights", "race-and-civil-rights"));
            categoryList.add(new Category("Religion and Spirituality", "religion-spirituality-and-faith"));
            categoryList.add(new Category("Science and Technology", "science"));
            categoryList.add(new Category("Series Books", "series-books"));
            categoryList.add(new Category("Sports", "sports"));
            categoryList.add(new Category("Travel", "travel"));
            categoryList.add(new Category("Young Adult", "young-adult"));
        }
        return categoryList;
    }
}
