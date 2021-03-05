package model;

public interface AlertPopup {
    //Used lambda expressions using this interface to reduce the clutter of repeating alert popup code
    void alert(String title, String content);
}
