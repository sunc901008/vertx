package openscanner.cc.test;

import openscanner.cc.utils.ImageHelper;

import java.io.IOException;

/**
 * Created : sunc
 * Date : 16-8-8
 * Description :
 */
public class ImageHelperTest {

    public static void main(String[] args) {
        try {
            String path = "/opt/test/test/abc";
            ImageHelper.cutImage(path, path, 0, 0, 250, 250);
            ImageHelper.zoomImage(path, path, 100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
