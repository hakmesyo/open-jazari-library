/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.utils.PidController;

/**
 *
 * @author cezerilab
 * further info see
 * http://brettbeauregard.com/blog/2011/04/improving-the-beginners-pid-direction/improving-the-beginners-pid-introduction
 */
public class TestPidController {

    public static void main(String[] args) {
        PidController pid = new PidController(0.25, 0.01, 0.4);
        pid.setOutputLimits(10);
        //pid.setMaxIOutput(2);
        //pid.setOutputRampRate(3);
        //pid.setOutputFilter(.3);
        pid.setSetpointRange(40);

        double target = 100;

        double actual = 0;
        double output = 0;

        pid.setSetpoint(0);
        pid.setSetpoint(target);
        

        System.err.printf("Target\tActual\tOutput\tError\n");
        //System.err.printf("Output\tP\tI\tD\n");

        // Position based test code
        for (int i = 0; i < 100; i++) {

            //if(i==50)miniPID.setI(.05);
            if (i == 60) {
                target = 50;
            }

            //if(i==75)target=(100);
            //if(i>50 && i%4==0)target=target+(Math.random()-.5)*50;
            output = pid.getOutput(actual, target);
            actual = actual + output;

            //System.out.println("=========================="); 
            //System.out.printf("Current: %3.2f , Actual: %3.2f, Error: %3.2f\n",actual, output, (target-actual));
            System.err.printf("%3.2f\t%3.2f\t%3.2f\t%3.2f\n", target, actual, output, (target - actual));

            //if(i>80 && i%5==0)actual+=(Math.random()-.5)*20;
        }

    }
}
