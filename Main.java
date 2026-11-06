import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import detecting.*;
import imaging.*;
import model.*;
import physics.*;
import tracking.*;

/**
 * Unified CLI entry point for three modes:
 *   beads    – detect and print blobs in a single image.
 *   track    – print displacement distances across consecutive frames.
 *   estimate – run the full pipeline and print Boltzmann's constant and Avogadro's number.
 */
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

    // Detects beads in a single frame and displays a binarised preview.
    private static void runBeads(String[] args) {
        int min = Integer.parseInt(args[1]);
        double tau = Double.parseDouble(args[2]);
        String path = args[3];

        Picture image = new Picture(path);
        PixelClassifier classifier = new LuminanceClassifier(tau);
        List<IBlob> blobs = new BlobDetector().detect(classifier.getForegroundPixels(image));
        for (IBlob bead : BeadFinder.filter(blobs, min)) {
            System.out.println(bead);
        }

        // Show binarised preview
        ImageConverter converter = new GrayscaleConverter(classifier);
        Picture grayscaled = converter.convert(image);
        grayscaled.show();
    }

    // Prints per-frame displacement distances to stdout.
    private static void runTrack(String[] args) {
        for (double displacement : trackAll(args)) {
            System.out.printf("%.4f%n", displacement);
        }
    }

    // Estimates and prints Boltzmann's constant and Avogadro's number.
    private static void runEstimate(String[] args) {
        Estimator estimator = new Estimator(trackAll(args));
        System.out.printf("Boltzmann = %.4E%n", estimator.calculateBoltzmann());
        System.out.printf("Avogadro  = %.4E%n", estimator.calculateAvogadro());
    }

    // Shared pipeline: loads frames, detects beads, tracks and flattens all displacements.
    private static List<Double> trackAll(String[] args) {
        int min = Integer.parseInt(args[1]);
        double tau = Double.parseDouble(args[2]);
        double maxDistance = Double.parseDouble(args[3]);

        PixelClassifier classifier = new LuminanceClassifier(tau);
        BlobDetector detector = new BlobDetector();

        List<List<IBlob>> blobsList = new ArrayList<>();
        for (String path : Arrays.copyOfRange(args, 4, args.length)) {
            Picture image = new Picture(path);
            List<IBlob> blobs = detector.detect(classifier.getForegroundPixels(image));
            blobsList.add(BeadFinder.filter(blobs, min));
        }

        BeadMatcher beadMatcher = new NearestNeighborMatcher();
        BeadTracker beadTracker = new BeadTracker(blobsList, beadMatcher);
        List<List<Double>> displacements = beadTracker.trackDistance(maxDistance);

        // Flatten per-frame displacement lists into a single sequence
        List<Double> all = new ArrayList<>();
        for (List<Double> displacement : displacements) {
            all.addAll(displacement);
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
