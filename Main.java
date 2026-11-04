import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import detecting.BeadFinder;
import detecting.BlobDetector;
import imaging.GrayscaleConvertor;
import imaging.ImageConvertor;
import imaging.LuminanceClassifier;
import imaging.Picture;
import imaging.PixelClassifier;
import model.IBlob;
import tracking.BeadTracker;

public class Main {
    public static void main(String[] args) {
        if (args[0].charAt(0) == '-' && args[0].charAt(1) == 'd') {
            if (args.length < 3) {
                System.err.println("Usage: java Main <min> <tau> <image_path>");
                System.exit(1);
            }

            int min = Integer.parseInt(args[1]);
            if (min < 0) {
                System.err.println("invalid value for min");
                System.exit(1);
            }

            double tau = Double.parseDouble(args[2]);
            if (tau < 0 || tau > 255) {
                System.err.println("invalid value for tau");
                System.exit(1);
            }

            String imagePath = args[3];

            Picture image = new Picture(imagePath);
            PixelClassifier pc = new LuminanceClassifier(image, tau);
            ImageConvertor ic = new GrayscaleConvertor(image);
            Picture grayscaled = ic.convert(pc.getForegroundPixels());

            BlobDetector bd = new BlobDetector(grayscaled, pc.getForegroundPixels());
            List<IBlob> blobs = bd.detectBlobs();
            BeadFinder bf = new BeadFinder(blobs);

            for (IBlob blob : bf.findBeads(min)) {
                System.out.println(blob);
            }

            grayscaled.save("grayscaled.jpg");
        } else if (args[0].charAt(0) == '-' && args[0].charAt(1) == 't') {
            int min = Integer.parseInt(args[1]);
            double tau = Double.parseDouble(args[2]);
            int maxDistance = Integer.parseInt(args[3]);

            List<Picture> images = new ArrayList<>();
            for (String path : Arrays.copyOfRange(args, 4, args.length)) {
                images.add(new Picture(path));
            }

            List<List<IBlob>> blobsList = new ArrayList<>();
            for (Picture image : images) {
                PixelClassifier pc = new LuminanceClassifier(image, tau);
                ImageConvertor ic = new GrayscaleConvertor(image);
                Picture grayscaled = ic.convert(pc.getForegroundPixels());

                BlobDetector bd = new BlobDetector(grayscaled, pc.getForegroundPixels());
                BeadFinder bf = new BeadFinder(bd.detectBlobs());
                blobsList.add(bf.findBeads(min));
            }

            BeadTracker bt = new BeadTracker(blobsList, maxDistance);
            List<List<Double>> displacements = bt.trackDistance();
            for(List<Double> displacement:displacements){
                for(Double distance : displacement)
                    System.out.format("%6.4f\n",distance);
            }
        }
        else if (args[0].charAt(0)=='-' && args[0].charAt(1)=='a'){
            int min = Integer.parseInt(args[1]);
            double tau = Double.parseDouble(args[2]);
            int maxDistance = Integer.parseInt(args[3]);

            List<Picture> images = new ArrayList<>();
            for (String path : Arrays.copyOfRange(args, 4, args.length)) {
                images.add(new Picture(path));
            }

            List<List<IBlob>> blobsList = new ArrayList<>();
            for (Picture image : images) {
                PixelClassifier pc = new LuminanceClassifier(image, tau);
                ImageConvertor ic = new GrayscaleConvertor(image);
                Picture grayscaled = ic.convert(pc.getForegroundPixels());

                BlobDetector bd = new BlobDetector(grayscaled, pc.getForegroundPixels());
                BeadFinder bf = new BeadFinder(bd.detectBlobs());
                blobsList.add(bf.findBeads(min));
            }

            BeadTracker bt = new BeadTracker(blobsList, maxDistance);
            List<List<Double>> displacements = bt.trackDistance();

            List<Double> damn = new ArrayList<>();
            for (List<Double> distances : displacements) {
                for (double dist : distances) {
                    damn.add(dist);
                }
            }
            Avogadro avogadro = new Avogadro(damn);

            System.out.println("Boltzmann: " + avogadro.calculateBoltzmann());
            System.out.println("Avogadro: " + avogadro.calculateAvogadro());
        }
    }
}
