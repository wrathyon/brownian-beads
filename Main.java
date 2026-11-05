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
import physics.Estimator;
import tracking.BeadTracker;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            usage();
            return;
        }

        try {
            switch (args[0]) {
                case "beads" -> {
                    if (args.length != 4) {
                        usage();
                        return;
                    }
                    runBeads(args);
                }
                case "track" -> {
                    if (args.length < 6) {
                        usage();
                        return;
                    }
                    runTrack(args);
                }
                case "estimate" -> {
                    if (args.length < 6) {
                        usage();
                        return;
                    }
                    runEstimate(args);
                }
                default -> usage();
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            usage();
        }
    }

    private static void runBeads(String[] args) {
        int min = Integer.parseInt(args[1]);
        double tau = Double.parseDouble(args[2]);
        String path = args[3];

        PixelClassifier classifier = new LuminanceClassifier(tau);
        ImageConvertor convertor = new GrayscaleConvertor(classifier);
        Picture grayscaled = convertor.convert(new Picture(path));

        List<IBlob> blobs = new BlobDetector().detect(classifier.getForegroundPixels(grayscaled));
        for (IBlob bead : BeadFinder.filter(blobs, min)) {
            System.out.println(bead);
        }

        grayscaled.show();
    }

    private static void runTrack(String[] args) {
        for (double displacement : trackAll(args)) {
            System.out.println(displacement);
        }
    }

    private static void runEstimate(String[] args) {
        Estimator estimator = new Estimator(trackAll(args));
        System.out.println("Boltzmann = " + estimator.calculateBoltzmann());
        System.out.println("Avogadro  = " + estimator.calculateAvogadro());
    }

    private static List<Double> trackAll(String[] args) {
        int min = Integer.parseInt(args[1]);
        double tau = Double.parseDouble(args[2]);
        double maxDistance = Double.parseDouble(args[3]);

        PixelClassifier classifier = new LuminanceClassifier(tau);
        ImageConvertor convertor = new GrayscaleConvertor(classifier);
        BlobDetector detector = new BlobDetector();

        List<List<IBlob>> blobsList = new ArrayList<>();
        for (String path : Arrays.copyOfRange(args, 4, args.length)) {
            Picture grayscaled = convertor.convert(new Picture(path));
            List<IBlob> blobs = detector.detect(classifier.getForegroundPixels(grayscaled));
            blobsList.add(BeadFinder.filter(blobs, min));
        }

        BeadTracker beadTracker = new BeadTracker(blobsList);
        List<List<Double>> displacements = beadTracker.trackDistance(maxDistance);

        List<Double> all = new ArrayList<>();
        for (List<Double> displacement : displacements) {
            for (double distance : displacement) {
                all.add(distance);
            }
        }
        return all;
    }

    private static void usage() {
        System.err.println("""
            Usage:
              beads    <min> <tau> <image>
              track    <min> <tau> <maxDistance> <image>...
              estimate <min> <tau> <maxDistance> <image>...""");
        System.exit(1);
    }
}
