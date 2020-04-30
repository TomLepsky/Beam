package com.tomlepsky.beam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomlepsky.beam.configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

//Сила полож. направление вверх; момент полож. направление по часовой стрелке
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        double beamLength = 10;
        double HISCoord = 0;
        double HMSCoord = 8;

        List<Stress> stresses = new ArrayList<>();

        stresses.add(new UniformlyDistributedLoad(1, -2));
        stresses.add(new UniformlyDistributedLoad(4, 2));
        stresses.add(new Force(6,-6));
        stresses.add(new Moment(9, 3));


        //Beam beam = new HingedBearingBeam(stresses, beamLength, HISCoord, HMSCoord);
        Beam beam = new AnchorageBeam(stresses, beamLength);
        beam.calculateBeam();
        toJSON(beam.getShear());
/*
        for (Stress stress: beam.getStresses()) {
            System.out.println(stress.toString());
        }
*/
        //showStress(beam.getIntervals(), beam.getTorsion());

    }

    public static void showStress(double[][] intervals, double[][] stress) {
        for (int i = 0; i < intervals.length; i++) {
            System.out.println("\ninterval: " + (i + 1));
            for (int j = 0; j < intervals[i].length; j++) {
                System.out.println(intervals[i][j] + "   " + stress[i][j]);
            }
        }
    }

    public static void toJSON(double[][] data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new FileOutputStream("D:\\stress.txt"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
