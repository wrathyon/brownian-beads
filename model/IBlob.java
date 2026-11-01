package model;

public interface IBlob {
    double getCenterX();

    double getCenterY();

    int mass();

    void add(double x, double y);

    double distanceTo(IBlob that);
}
