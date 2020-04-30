package com.tomlepsky.beam;

import java.io.FileWriter;
import java.io.IOException;

public class OutputTXT extends OutputData {

    public OutputTXT(String path) {
        super(path);
    }

    @Override
    public void export(double[] data) {
        StringBuilder builder = new StringBuilder();

        for (double datum : data) {
            builder.append(String.format("%.2f\r\n", datum));
        }

        try(FileWriter writer = new FileWriter(getPath(),false)) {
            writer.write(builder.toString());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void export(double[][] data, String fileName) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                builder.append(String.format("%.2f\r\n", data[i][j]));
            }
        }

        try(FileWriter writer = new FileWriter(getPath() + fileName,false)) {
            writer.write(builder.toString());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
