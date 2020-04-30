package com.tomlepsky.beam;

public abstract class OutputData {

    private String path;

    public OutputData(String path) {
        this.path = path;
    }

    public abstract void export(double[] data);

    public abstract void export(double[][] data, String fileName);

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
