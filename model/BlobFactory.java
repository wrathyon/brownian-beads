package model;

/**
 * Factory for creating {@link IBlob} instances, decoupling blob creation from detection logic.
 */
public class BlobFactory {
    /** Creates a new {@link IBlob} instance. */
    public IBlob createBlob() {
        return new Blob();
    }

    /** Static convenience method to create an {@link IBlob} instance. */
    public static IBlob create() {
        return new Blob();
    }
}
