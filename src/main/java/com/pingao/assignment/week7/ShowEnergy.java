package com.pingao.assignment.week7;

import com.pingao.utils.ResourceUtils;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by pingao on 2018/11/13.
 */
public class ShowEnergy {
    public static void main(String[] args) {
        Picture picture = new Picture(ResourceUtils.getTestResourcePath("HJoceanSmall.png"));
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();
        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);

    }
}
