package com.ronakmanglani.bookworm.model;

import com.ronakmanglani.bookworm.R;

import java.util.ArrayList;

public class Category {

    // Attributes
    private int iconId;
    private String displayName;
    private String listName;

    // Getters
    public int getIconId() {
        return iconId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getListName() {
        return listName;
    }

    // Constructor
    public Category(int iconId, String displayName, String listName) {
        this.iconId = iconId;
        this.displayName = displayName;
        this.listName = listName;
    }

    // Static list of categories
    private static ArrayList<Category> categoryList;
    public static ArrayList<Category> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<>();
            categoryList.add(new Category(R.drawable.icon_category_fiction, "Fiction", "combined-print-and-e-book-fiction"));
            categoryList.add(new Category(R.drawable.icon_category_nonfiction, "Nonfiction", "combined-print-and-e-book-nonfiction"));
            categoryList.add(new Category(R.drawable.icon_category_advice, "Advice and How-To", "advice-how-to-and-miscellaneous"));
            categoryList.add(new Category(R.drawable.icon_category_animals, "Animals", "animals"));
            categoryList.add(new Category(R.drawable.icon_category_business, "Business", "business-books"));
            categoryList.add(new Category(R.drawable.icon_category_celebrities, "Celebrities", "celebrities"));
            categoryList.add(new Category(R.drawable.icon_category_crime, "Crime and Punishment", "crime-and-punishment"));
            categoryList.add(new Category(R.drawable.icon_category_culture, "Culture", "culture"));
            categoryList.add(new Category(R.drawable.icon_category_education, "Education", "education"));
            categoryList.add(new Category(R.drawable.icon_category_food, "Food and Diet", "food-and-fitness"));
            categoryList.add(new Category(R.drawable.icon_category_games, "Games and Activities", "games-and-activities"));
            categoryList.add(new Category(R.drawable.icon_category_graphic, "Graphic Books", "paperback-graphic-books"));
            categoryList.add(new Category(R.drawable.icon_category_fitness, "Health and Fitness", "health"));
            categoryList.add(new Category(R.drawable.icon_category_humor, "Humor", "humor"));
            categoryList.add(new Category(R.drawable.icon_category_love, "Love and Relationships", "relationships"));
            categoryList.add(new Category(R.drawable.icon_category_family, "Parenthood and Family", "family"));
            categoryList.add(new Category(R.drawable.icon_category_history, "Politics and History", "hardcover-political-books"));
            categoryList.add(new Category(R.drawable.icon_category_religion, "Religion and Spirituality", "religion-spirituality-and-faith"));
            categoryList.add(new Category(R.drawable.icon_category_science, "Science and Technology", "science"));
            categoryList.add(new Category(R.drawable.icon_category_sports, "Sports", "sports"));
            categoryList.add(new Category(R.drawable.icon_category_travel, "Travel", "travel"));
            categoryList.add(new Category(R.drawable.icon_category_young, "Young Adult", "young-adult"));
        }
        return categoryList;
    }
}