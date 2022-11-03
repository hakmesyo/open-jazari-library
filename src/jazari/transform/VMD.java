/* 
Variational Mode Decomposition
--------------------------------------------------------
Authors: Konstantin Dragomiretskiy and Dominique Zosso
zosso@math.ucla.edu --- http://www.math.ucla.edu/~zosso
Initial release 2013-12-12 (c) 2013
---------------------------------------------------------
porting matlab to the java and ocl was implemented by 
Dr.Musa Ataş El-Cezeri Cybernetics and Robotics Lab 2017
 */
package jazari.transform;

import jazari.matrix.CMatrix;

/**
 *
 * @author BAP1
 */
public class VMD {

    public static void main(String[] args) {
        // Time Domain 0 to T
        float T = 1000;
        float fs = 1 / T;
        CMatrix t = CMatrix.getInstance().range(1, T).divideScalar(T);
        CMatrix freqs = t.minusScalar(0.5f + 1.0f / T).timesScalar(2 * (float)Math.PI).divideScalar(fs);
        //% center frequencies of components
        float f_1 = 2;
        float f_2 = 24;
        float f_3 = 288;

        //% modes
        CMatrix v_1 = t.timesScalar(2 * (float)Math.PI * f_1).cos();
        CMatrix v_2 = t.timesScalar(2 * (float)Math.PI * f_2).cos().timesScalar(1 / 4.0f);
        CMatrix v_3 = t.timesScalar(2 * (float)Math.PI * f_3).cos().timesScalar(1 / 16.0f);

        //% composite signal, including noise
        CMatrix f = v_1.add(v_2).add(v_3).add(CMatrix.getInstance().randn(v_1.maxsize()).timesScalar(0.1f)).plot();
        //f_hat = fftshift((fft(f)));

        //% some sample parameters for VMD
        float alpha = 2000;       // moderate bandwidth constraint
        float tau = 0;            // noise-tolerance (no strict fidelity enforcement)
        float K = 3;              // 3 modes
        float DC = 0;             // no DC part imposed
        float init = 1;           // initialize omegas uniformly
        float tol = 1e-7f;

//figure;plot(f);
//%--------------- Run actual VMD code
//VMD(f, alpha, tau, K, DC, init, tol);
    }

}
