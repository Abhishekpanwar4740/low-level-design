package com.example.demo.mementoDesignPattern;

public class ConfigurationOriginator {
    int height;
    int width;

    public ConfigurationOriginator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public ConfigurationMemento createMemento(){
        return new ConfigurationMemento(this.height,this.width);
    }
    public void restoreMemento(ConfigurationMemento memento){
        this.height= memento.getHeight();
        this.width=memento.getWidth();
    }
}
