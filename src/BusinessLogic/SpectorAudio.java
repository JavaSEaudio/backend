package BusinessLogic;


import com.beaglebuddy.mp3.MP3;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

/**
 * Created by Администратор on 04.08.2014.
 */
public class SpectorAudio  {


    public static void main(String [] args) throws IOException {

        InputStream stream = new FileInputStream("D:\\music\\motorolla_-_zhar-ptitsya.mp3");
        byte [] arr = inputStreamToByteArray(stream);
        double [] doubles = calculateFFT(arr);
        System.out.println(doubles.length);
        for(double t:doubles){
            System.out.println(t);
        }
    }
    public static byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public  static  double[] calculateFFT(byte[] signal)
    {
        final int mNumberOfFFTPoints =1024;
        double mMaxFFTSample;

        double temp;
        Complex[] y;
        Complex[] complexSignal = new Complex[mNumberOfFFTPoints];
        double[] absSignal = new double[mNumberOfFFTPoints/2];

        for(int i = 0; i < mNumberOfFFTPoints; i++){
            temp = (double)((signal[2*i] & 0xFF) | (signal[2*i+1] << 8)) / 32768.0F;
            complexSignal[i] = new Complex(temp,0.0);
        }

        y = FFT.fft(complexSignal); // --> Here I use FFT class

        mMaxFFTSample = 0.0;
        double mPeakPos = 0;
        for(int i = 0; i < (mNumberOfFFTPoints/2); i++)
        {
            absSignal[i] = Math.sqrt(Math.pow(y[i].re(), 2) + Math.pow(y[i].im(), 2));
            if(absSignal[i] > mMaxFFTSample)
            {
                mMaxFFTSample = absSignal[i];
                mPeakPos = i;
            }
        }
        return absSignal;
    }
    public static double [] descret(int timeOfDecreasePoint, double [] masOfPoint){
    double [] tempMas = new double[masOfPoint.length/timeOfDecreasePoint];
    for(int i=0;i<masOfPoint.length;i=i+timeOfDecreasePoint){
        tempMas[i] = masOfPoint[i];
    }
        return tempMas;
    }

    public static double [] getSpectrum(String path) throws IOException {
        InputStream stream = new FileInputStream(path);
        byte [] arr = inputStreamToByteArray(stream);
        double [] doublesData = calculateFFT(arr);
        return doublesData;
    }
}
