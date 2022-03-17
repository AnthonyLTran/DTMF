package edu.ggc.atran.dtmf;

public class DTMF {

    public static final Double[] DTMF_FREQUENCIES = { 697D, 770D, 852D, 941D, 1209D,
            1336D, 1477D, 1633D };

    public static final int SAMPLE_RATE = 8000;


    public static short[] generateTone (char c , int t) {

        double f0 = 697D;
        if (c == '4' || c == '5' || c == '6' || c == 'B')
            f0 = 770D;
        else if (c == '7' || c == '8' || c == '9' || c == 'C')
            f0 = 852D;
        else if (c == '*' || c == '0' || c == '#' || c == 'D')
            f0 = 941D;

        double f1 = 1209D;
        if (c == '2' || c == '5' || c == '8' || c == '0')
            f1 = 1336D;
        else if (c == '3' || c == '6' || c == '9' || c == '*')
            f1 = 1477D;
        else if (c == 'A' || c == 'B' || c == 'C' || c == 'D')
            f1 = 1633D;

        double [] resultD = generateTone(SAMPLE_RATE, f0, f1, t);

        short[] resultS = new short[resultD.length]; /* convert double [] to float [] */
        for (int i = 0; i < resultD.length; i++)
            resultS[i] = (short)(Math.round(resultD[i]*32767.0D));
        return resultS;
    }



    public static double[] generateTone(final float sampleRate, double f0, double f1, int msecs) {
        final double amplitudeF0 = 0.5;
        final double amplitudeF1 = 0.5;
        final double twoPiF0 = 2 * Math.PI * f0;
        final double twoPiF1 = 2 * Math.PI * f1;
        final double[] buffer = new double[(int) (msecs/1000D*sampleRate)];
        for (int sample = 0; sample < buffer.length; sample++) {
            final double time = sample / sampleRate;
            double f0Component = amplitudeF0 * Math.sin(twoPiF0 * time);
            double f1Component = amplitudeF1 * Math.sin(twoPiF1 * time);
            buffer[sample] = f0Component + f1Component;
        }
        return buffer;
    }
}

